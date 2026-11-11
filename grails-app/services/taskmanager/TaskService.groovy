package taskmanager

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import java.time.LocalDate
import java.time.ZoneId

@Transactional
class TaskService {

    @Transactional(readOnly = true)
    Task get(Long id) {
        Task.get(id)
    }

    @Transactional(readOnly = true)
    List<Task> list(Map params) {
        TaskStatus status = TaskStatus.values().find { it.name() == params.status }

        Task.createCriteria().list(max: params.max, offset: params.offset) {
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
        User.list(sort: 'username')
    }

    Task save(Task task) {
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
        Map<TaskStatus, Long> counts = [:]
        TaskStatus.values().each { s ->
            counts[s] = Task.countByStatus(s)
        }
        return counts
    }

    @Transactional(readOnly = true)
    Long countOverdue() {
        Task.countByDeadlineLessThanAndStatusNotEqual(startOfToday(), TaskStatus.DONE)
    }

    private static Date startOfToday() {
        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    }
}