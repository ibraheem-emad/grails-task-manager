package taskmanager

import grails.plugin.springsecurity.annotation.Secured

@Secured('isAuthenticated()')
class DashboardController {

    TaskService taskService

    def index() {
        [statusCounts: taskService.countByStatus(),
         overdueCount: taskService.countOverdue()]
    }
}