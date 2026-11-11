package taskmanager

import grails.gorm.transactions.Transactional

@Transactional
class TaskService {

    @Transactional(readOnly = true)
    Task get(Long id) {
        Task.get(id)
    }

    @Transactional(readOnly = true)
    List<Task> list(Map params) {
        Task.list(params)
    }

    @Transactional(readOnly = true)
    Long count() {
        Task.count()
    }

    @Transactional(readOnly = true)
    List<User> listUsers() {
        User.list(sort: 'username')
    }

    Task save(Task task) {
        task.save(failOnError: true)
    }

    void delete(Long id) {
        Task task = Task.get(id)
        if (!task) {
            throw new IllegalArgumentException("Task ${id} not found.")
        }
        task.delete()
    }
}