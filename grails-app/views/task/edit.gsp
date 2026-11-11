<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Edit Task</title>
</head>
<body>
<div class="container">
    <h1>Edit Task</h1>
    <g:form action="update" id="${task.id}" method="PUT">
        <g:render template="form" model="[task: task, users: users]"/>
        <button type="submit" class="btn btn-primary">Save</button>
        <g:link action="show" id="${task.id}" class="btn btn-secondary">Cancel</g:link>
    </g:form>
</div>
</body>
</html>