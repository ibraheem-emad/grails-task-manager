<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>New Task</title>
</head>
<body>
<div class="container">
    <h1>New Task</h1>
    <g:form action="save" method="POST">
        <g:render template="form" model="[task: task, users: users]"/>
        <button type="submit" class="btn btn-primary">Create</button>
        <g:link action="index" class="btn btn-secondary">Cancel</g:link>
    </g:form>
</div>
</body>
</html>