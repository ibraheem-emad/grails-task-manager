<g:hasErrors bean="${task}">
    <div class="alert alert-danger">Please fix the errors below.</div>
</g:hasErrors>

<div class="mb-3">
    <label for="title" class="form-label">Title</label>
    <g:textField name="title" value="${task?.title}"
                 class="form-control ${hasErrors(bean: task, field: 'title', 'is-invalid')}"/>
    <g:hasErrors bean="${task}" field="title">
        <div class="invalid-feedback d-block"><g:fieldError bean="${task}" field="title"/></div>
    </g:hasErrors>
</div>

<div class="mb-3">
    <label for="description" class="form-label">Description</label>
    <g:textArea name="description" value="${task?.description}" rows="4"
                class="form-control ${hasErrors(bean: task, field: 'description', 'is-invalid')}"/>
    <g:hasErrors bean="${task}" field="description">
        <div class="invalid-feedback d-block"><g:fieldError bean="${task}" field="description"/></div>
    </g:hasErrors>
</div>

<div class="mb-3">
    <label for="deadline" class="form-label">Deadline</label>
    <input type="date" name="deadline" id="deadline"
           value="${formatDate(date: task?.deadline, format: 'yyyy-MM-dd')}"
           class="form-control ${hasErrors(bean: task, field: 'deadline', 'is-invalid')}"/>
    <g:hasErrors bean="${task}" field="deadline">
        <div class="invalid-feedback d-block"><g:fieldError bean="${task}" field="deadline"/></div>
    </g:hasErrors>
</div>

<div class="mb-3">
    <label for="status" class="form-label">Status</label>
    <g:select name="status" from="${taskmanager.TaskStatus.values()}" optionValue="label"
              value="${task?.status}"
              class="form-select ${hasErrors(bean: task, field: 'status', 'is-invalid')}"/>
    <g:hasErrors bean="${task}" field="status">
        <div class="invalid-feedback d-block"><g:fieldError bean="${task}" field="status"/></div>
    </g:hasErrors>
</div>

<div class="mb-3">
    <label for="assignedTo" class="form-label">Assigned to</label>
    <g:select name="assignedTo.id" id="assignedTo" from="${users}" optionKey="id" optionValue="username"
              value="${task?.assignedTo?.id}" noSelection="['': '-- Choose a user --']"
              class="form-select ${hasErrors(bean: task, field: 'assignedTo', 'is-invalid')}"/>
    <g:hasErrors bean="${task}" field="assignedTo">
        <div class="invalid-feedback d-block"><g:fieldError bean="${task}" field="assignedTo"/></div>
    </g:hasErrors>
</div>