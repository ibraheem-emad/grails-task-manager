<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>${task.title}</title>
</head>
<body>
<div class="container">
    <h1>${task.title}</h1>
    <dl class="row">
        <dt class="col-sm-3">Description</dt>
        <dd class="col-sm-9">${task.description ?: '-'}</dd>
        <dt class="col-sm-3">Deadline</dt>
        <dd class="col-sm-9"><g:formatDate date="${task.deadline}" format="dd-MM-yyyy"/></dd>
        <dt class="col-sm-3">Status</dt>
        <dd class="col-sm-9">${task.status.label}</dd>
        <dt class="col-sm-3">Assigned to</dt>
        <dd class="col-sm-9">${task.assignedTo.username}</dd>
        <dt class="col-sm-3">Created</dt>
        <dd class="col-sm-9"><g:formatDate date="${task.dateCreated}" format="dd-MM-yyyy HH:mm"/></dd>
        <dt class="col-sm-3">Last updated</dt>
        <dd class="col-sm-9"><g:formatDate date="${task.lastUpdated}" format="dd-MM-yyyy HH:mm"/></dd>
    </dl>
    <g:form action="delete" id="${task.id}" method="DELETE">
        <g:link action="edit" id="${task.id}" class="btn btn-primary">Edit</g:link>
        <button type="submit" class="btn btn-danger" onclick="return confirm('Delete this task?')">Delete</button>
        <g:link action="index" class="btn btn-secondary">Back to list</g:link>
    </g:form>
</div>
</body>
</html>