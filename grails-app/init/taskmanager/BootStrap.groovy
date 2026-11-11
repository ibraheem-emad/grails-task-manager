package taskmanager

class BootStrap {

    def init = { servletContext ->
        Role.withTransaction {
            Role adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
            Role userRole  = Role.findByAuthority('ROLE_USER')  ?: new Role(authority: 'ROLE_USER').save(failOnError: true)

            User admin  = User.findByUsername('admin@ubs.com')  ?: new User(username: 'admin@ubs.com',  password: 'admin123').save(failOnError: true)
            User intern = User.findByUsername('intern@ubs.com') ?: new User(username: 'intern@ubs.com', password: 'intern123').save(failOnError: true)

            if (!UserRole.exists(admin.id, adminRole.id)) {
                UserRole.create(admin, adminRole, true)
            }
            if (!UserRole.exists(intern.id, userRole.id)) {
                UserRole.create(intern, userRole, true)
            }
        }
    }

    def destroy = {
    }

}
