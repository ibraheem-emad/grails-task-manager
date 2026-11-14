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

    /**
    * takes one task id, checks first if the user has permission to see the task
    * if they have permission it returns the task, otherwise returns null
    *
    * @param id the task id
    * @return the task or null if it doesn't exist or the user can't see it
    */
    @Transactional(readOnly = true)
    Task get(Long id) {
        Task task = Task.get(id)
        if (task && !isAdmin() && task.assignedTo.id != currentUser().id) {
            return null
        }
        task
    }

    /**
    * lists tasks one page at a time sorted by deadline, only shows users
    * the tasks they are permitted to see.
    *
    * @param params may contain search (part of the title), status (TODO, IN_PROGRESS or DONE),
    * max (page size) and offset (how many tasks to skip)
    * @return one page of tasks
    * totalCount on the result gives the number of all matching tasks
    */
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

    /**
    * lists the users a task can be assigned to.
    *
    * @return all users for an admin, or just the current user for a regular user
    */
    @Transactional(readOnly = true)
    List<User> listUsers() {
        isAdmin() ? User.list(sort: 'username') : [currentUser()]
    }

    /**
    * validates and saves a new task, or task edits.
    * business rule: a new task's deadline can't be in the past.
    * A regular user's can only aassign new tasks to themselves.
    *
    * @param task the task to save
    * @return the saved task
    * @throws ValidationException if a constraint or the deadline rule fails
    */
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

    /**
    * deletes a task, Only admins can reach this.
    *
    * @param id the task id
    * @throws IllegalArgumentException if no task has this id
    */
    void delete(Long id) {
        Task task = Task.get(id)
        if (!task) {
            throw new IllegalArgumentException("Task ${id} not found.")
        }
        task.delete()
    }

    /**
    * counts tasks according to status for the dashboard cards.
    * admins get counts for all tasks regular users for their own.
    *
    * @return a map from each status to its number of tasks
    */
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

    /**
    * counts overdue tasks: deadline before today and status not Done.
    * admins get the count for all tasks, regular users for their own.
    *
    * @return the number of overdue tasks
    */
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