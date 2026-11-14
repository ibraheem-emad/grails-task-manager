package taskmanager

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import java.time.LocalDate
import java.time.ZoneId
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils

@Transactional
class TaskService {

    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    Task get(Long id) {
        Task task = Task.get(id)
        if (task && !isAdmin() && task.assignedTo.id != currentUser().id) {
            return null
        }
        task
    }

    @Transactional(readOnly = true)
    List<Task> list(Map params) {
    boolean admin = isAdmin()
    User me = currentUser()
    TaskStatus status = TaskStatus.values().find { it.name() == params.status }

    Task.createCriteria().list(max: params.max, offset: params.offset) {
        if (!admin) {
            eq('assignedTo', me)
        }
        if (params.search) {
            ilike('title', "%${params.search}%".toString())
        }
        if (status) {
            eq('status', status)
        }
        order('deadline')
    }
}

    @Transactional(readOnly = true)
    List<User> listUsers() {
        isAdmin() ? User.list(sort: 'username') : [currentUser()]
    }

    Task save(Task task) {

        if (!isAdmin()) {
        task.assignedTo = currentUser()
        }

        task.validate()

        if (task.id == null && task.deadline && task.deadline.before(startOfToday())) {
            task.errors.rejectValue('deadline', 'task.deadline.past', "The deadline can't be in the past.")
        }

        if (task.hasErrors()) {
            throw new ValidationException('Task is not valid', task.errors)
        }
        task.save(failOnError: true)
    }

    void delete(Long id) {
        Task task = Task.get(id)
        if (!task) {
            throw new IllegalArgumentException("Task ${id} not found.")
        }
        task.delete()
    }

    @Transactional(readOnly = true)
    Map<TaskStatus, Long> countByStatus() {
        boolean admin = isAdmin()
        User me = currentUser()
        Map<TaskStatus, Long> counts = [:]
        TaskStatus.values().each { s ->
            counts[s] = admin ? Task.countByStatus(s) : Task.countByStatusAndAssignedTo(s, me)
        }
        return counts
    }

    @Transactional(readOnly = true)
    Long countOverdue() {
        if (isAdmin()) {
            return Task.countByDeadlineLessThanAndStatusNotEqual(startOfToday(), TaskStatus.DONE)
        }
        Task.countByDeadlineLessThanAndStatusNotEqualAndAssignedTo(startOfToday(), TaskStatus.DONE, currentUser())
}

    private static Date startOfToday() {
        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    private boolean isAdmin() {
        SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')
    }

    private User currentUser() {
        springSecurityService.currentUser as User
    }
}