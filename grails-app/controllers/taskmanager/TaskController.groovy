package taskmanager

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured('isAuthenticated()')
class TaskController {

    TaskService taskService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def taskList = taskService.list(params)
        [taskList: taskList, taskCount: taskList.totalCount, statusCounts: taskService.countByStatus(), overdueCount: taskService.countOverdue()]
    }

    def show(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(); return }
        [task: task]
    }

    def create() {
        [task: new Task(params), users: taskService.listUsers()]
    }

    def save() {
        Task task = new Task(params)
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            render view: 'create', model: [task: task, users: taskService.listUsers()]
            return
        }
        flash.message = "Task '${task.title}' created."
        redirect action: 'show', id: task.id
    }

    def edit(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(); return }
        [task: task, users: taskService.listUsers()]
    }

    def update(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(); return }
        task.properties = params
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            render view: 'edit', model: [task: task, users: taskService.listUsers()]
            return
        }
        flash.message = "Task '${task.title}' updated."
        redirect action: 'show', id: task.id
    }
    
    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        try {
            taskService.delete(id)
            flash.message = "Task deleted."
        } catch (IllegalArgumentException e) {
            flash.error = e.message
        }
        redirect action: 'index'
    }

    private void notFound() {
        flash.error = "Task ${params.id} not found."
        redirect action: 'index'
    }

}