<div class="mb-3">
    <label for="title" class="form-label">Title</label>
    <g:textField name="title" value="${task?.title}" class="form-control"/>
</div>
<div class="mb-3">
    <label for="description" class="form-label">Description</label>
    <g:textArea name="description" value="${task?.description}" rows="4" class="form-control"/>
</div>
<div class="mb-3">
    <label for="deadline" class="form-label">Deadline</label>
    <input type="date" name="deadline" id="deadline" class="form-control"
           value="${formatDate(date: task?.deadline, format: 'yyyy-MM-dd')}"/>
</div>
<div class="mb-3">
    <label for="status" class="form-label">Status</label>
    <g:select name="status" from="${taskmanager.TaskStatus.values()}" optionValue="label"
              value="${task?.status}" class="form-select"/>
</div>
<div class="mb-3">
    <label for="assignedTo" class="form-label">Assigned to</label>
    <g:select name="assignedTo.id" id="assignedTo" from="${users}" optionKey="id" optionValue="username"
              value="${task?.assignedTo?.id}" noSelection="['': '-- Choose a user --']" class="form-select"/>
</div>