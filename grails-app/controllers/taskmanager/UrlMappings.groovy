package taskmanager

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/tasks"(resources: 'taskRest', excludes: ['create', 'edit'])
        "/"(controller: 'dashboard')
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
