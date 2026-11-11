<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Dashboard</title>
    </head>
    <body>
    <div class="container">
        <h1 class="mb-4">Dashboard</h1>

        <div class="row g-3">
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="card text-bg-secondary h-100">
                    <div class="card-body">
                        <h5 class="card-title">To-Do</h5>
                        <p class="display-6 mb-0">${statusCounts[taskmanager.TaskStatus.TODO]}</p>
                    </div>
                </div>
            </div>
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="card text-bg-primary h-100">
                    <div class="card-body">
                        <h5 class="card-title">In-Progress</h5>
                        <p class="display-6 mb-0">${statusCounts[taskmanager.TaskStatus.IN_PROGRESS]}</p>
                    </div>
                </div>
            </div>
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="card text-bg-success h-100">
                    <div class="card-body">
                        <h5 class="card-title">Done</h5>
                        <p class="display-6 mb-0">${statusCounts[taskmanager.TaskStatus.DONE]}</p>
                    </div>
                </div>
            </div>
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="card text-bg-danger h-100">
                    <div class="card-body">
                        <h5 class="card-title">Overdue</h5>
                        <p class="display-6 mb-0">${overdueCount}</p>
                    </div>
                </div>
            </div>
        </div>

        <g:link controller="task" class="btn btn-outline-primary mt-4">View all tasks</g:link>
    </div>
    </body>
</html>