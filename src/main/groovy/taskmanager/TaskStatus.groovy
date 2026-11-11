package taskmanager

enum TaskStatus {
    TODO('To-Do'),
    IN_PROGRESS('In-Progress'),
    DONE('Done')

    final String label

    TaskStatus(String label) {
        this.label = label
    }
}