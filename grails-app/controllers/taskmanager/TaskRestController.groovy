package taskmanager

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@Secured('isAuthenticated()')
class TaskRestController {

    TaskService taskService

    private static final List<String> FIELDS = ['title', 'description', 'deadline', 'status', 'assignedTo']

    def index(Integer max) {
        int pageSize = Math.min(max ?: 10, 100)
        int offset = params.int('offset') ?: 0
        params.max = pageSize
        params.offset = offset

        renderJson([
            total: taskService.count(params),
            page : offset.intdiv(pageSize) + 1,
            max  : pageSize,
            data : taskService.list(params).collect { toMap(it) }
        ])
    }

    def show(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(id); return }
        renderJson(toMap(task))
    }

    def save() {
        Task task = new Task()
        bindData(task, request.JSON, [include: FIELDS])
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            validationFailed(task)
            return
        }
        renderJson(toMap(task), 201)
    }

    def update(Long id) {
        Task task = taskService.get(id)
        if (!task) { notFound(id); return }
        bindData(task, request.JSON, [include: FIELDS])
        try {
            taskService.save(task)
        } catch (ValidationException e) {
            validationFailed(task)
            return
        }
        renderJson(toMap(task))
    }

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
        [id         : t.id,
         title      : t.title,
         description: t.description,
         deadline   : g.formatDate(date: t.deadline, format: 'yyyy-MM-dd'),
         status     : t.status.name(),
         assignedTo : [id: t.assignedTo.id, username: t.assignedTo.username],
         dateCreated: t.dateCreated,
         lastUpdated: t.lastUpdated]
    }

    private void renderJson(Object body, int status = 200) {
        response.status = status
        render body as JSON
    }

    private void notFound(Long id) {
        renderJson([error: "Task ${id} not found.".toString()], 404)
    }

    private void validationFailed(Task task) {
        renderJson([errors: task.errors.fieldErrors.collect {
            [field: it.field, message: message(error: it)]
        }], 422)
    }
}