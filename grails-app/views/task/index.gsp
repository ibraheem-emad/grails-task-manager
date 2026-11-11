<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Tasks</title>
</head>
<body>
<div class="container">
    <h1>Tasks</h1>

    <p>
        <g:each in="${statusCounts}" var="entry">
            ${entry.key.label}: <strong>${entry.value}</strong> &nbsp;
        </g:each>
        Overdue: <strong>${overdueCount}</strong>
    </p>

    <g:link action="create" class="btn btn-primary mb-3">New Task</g:link>

    <g:form action="index" method="GET" class="mb-3">
        <g:textField name="search" value="${params.search}" placeholder="Search by title"/>
        <button type="submit" class="btn btn-secondary btn-sm">Search</button>
    </g:form>

    <table class="table table-striped">
        <tr><th>Title</th><th>Deadline</th><th>Status</th><th>Assigned to</th></tr>
        <g:each in="${taskList}" var="t">
            <tr>
                <td><g:link action="show" id="${t.id}">${t.title}</g:link></td>
                <td><g:formatDate date="${t.deadline}" format="dd-MM-yyyy"/></td>
                <td>${t.status.label}</td>
                <td>${t.assignedTo.username}</td>
            </tr>
        </g:each>
    </table>
    <g:if test="${!taskList}">
        <p>No tasks found.</p>
    </g:if>

    <g:paginate total="${taskCount}" params="[search: params.search]"/>
</div>
</body>
</html>