package taskmanager

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import java.time.LocalDate
import java.time.ZoneId
import grails.gorm.DetachedCriteria

@Transactional
class TaskService {

    @Transactional(readOnly = true)
    Task get(Long id) {
        Task.get(id)
    }

    @Transactional(readOnly = true)
    List<Task> list(Map params) {
        filtered(params).list(max: params.max, offset: params.offset, sort: 'deadline', order: 'asc')
    }

    @Transactional(readOnly = true)
    Long count(Map params = [:]) {
        filtered(params).count()
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
        Map<TaskStatus, Long> counts = TaskStatus.values().collectEntries { [(it): 0L] }
        Task.executeQuery('select t.status, count(t) from Task t group by t.status').each { row ->
        counts[row[0]] = row[1]}
        counts
    }

    @Transactional(readOnly = true)
    Long countOverdue() {
        Task.countByDeadlineLessThanAndStatusNotEqual(startOfToday(), TaskStatus.DONE)
    }

    private static Date startOfToday() {
        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    private DetachedCriteria<Task> filtered(Map params) {
    DetachedCriteria<Task> query = new DetachedCriteria(Task)

    if (params.search) {
        String pattern = "%${params.search}%".toString()
        query = query.where { title =~ pattern }
    }

    TaskStatus wanted = TaskStatus.values().find { it.name() == params.status }
    if (wanted) {
        query = query.where { status == wanted }
    }
    query
}
}