package taskmanager

class Task {

    String title
    String description
    Date deadline
    TaskStatus status = TaskStatus.TODO
    User assignedTo

    Date dateCreated
    Date lastUpdated

    static constraints = {
        title blank: false, maxSize: 150
        description nullable: true, maxSize: 1500
        deadline nullable: false
        status nullable: false
        assignedTo nullable: false
    }

    static mapping = {
        description sqlType: 'text'
    }
}