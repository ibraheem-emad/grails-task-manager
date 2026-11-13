package taskmanager

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured('isAuthenticated()')
class TaskRestController {

    TaskService taskService

    static responseFormats = ['json']

    def index(Integer max) {
        int pageSize = Math.min(max ?: 10, 100)
        int offset = params.int('offset') ?: 0
        params.max = pageSize
        params.offset = offset

        def tasks = taskService.list(params)
        def result = [total: tasks.totalCount, page: offset.intdiv(pageSize) + 1, max: pageSize, data: tasks.collect { toMap(it) }]
        render result as JSON
    }

    def show(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(id); return }
        render toMap(task) as JSON   
    }

    def save() {
        Task task = new Task(request.JSON)
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors
            return
        }
        response.status = 201
        render toMap(task) as JSON
    }

    def update(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(id); return }
        task.properties = request.JSON
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            respond task.errors
            return
        }
        render toMap(task) as JSON
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        try {
            taskService.delete(id)
        } catch (IllegalArgumentException e) {
            notFound(id)
            return
        }
        render status: 204
    }

    private Map toMap(Task t) {
        [id: t.id, title: t.title, description: t.description,
        deadline: g.formatDate(date: t.deadline, format: 'yyyy-MM-dd'),
        status: t.status.name(),
        assignedTo: [id: t.assignedTo.id, username: t.assignedTo.username],
        dateCreated: t.dateCreated, lastUpdated: t.lastUpdated]
    }

    private void notFound(Long id) {
        render(status: 404, contentType: 'application/json', text: ([error: "Task ${id} not found."] as JSON).toString())
    }
}