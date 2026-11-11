<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title><g:layoutTitle default="Task Manager"/></title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>
<body class="d-flex flex-column min-vh-100">

<header>
    <nav class="navbar navbar-expand-md bg-primary" data-bs-theme="dark">
        <div class="container">
            <g:link class="navbar-brand fw-semibold" uri="/">Task Manager</g:link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav"
                    aria-controls="mainNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mainNav">
                <ul class="navbar-nav ms-auto">
                    <sec:ifLoggedIn>
                        <li class="nav-item"><g:link class="nav-link ${controllerName == 'dashboard' ? 'active' : ''}" controller="dashboard">Dashboard</g:link></li>
                        <li class="nav-item"><g:link class="nav-link ${controllerName == 'task' ? 'active' : ''}" controller="task">Tasks</g:link></li>
                        <li class="nav-item">
                            <span class="navbar-text text-white ms-md-3">
                                Welcome, <sec:loggedInUserInfo field="username"/>
                            </span>
                        </li>
                        <li class="nav-item my-2 my-md-0">
                            <form action="${createLink(uri: '/logout')}" method="POST">
                                <button type="submit" class="btn btn-outline-light btn-sm ms-md-3">Log out</button>
                            </form>
                        </li>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <li class="nav-item">
                            <g:link class="nav-link" controller="login" action="auth">Log in</g:link>
                        </li>
                    </sec:ifNotLoggedIn>
                </ul>
            </div>
        </div>
    </nav>
</header>

<main class="flex-grow-1 py-4">
    <div class="container">
        <g:layoutBody/>
    </div>
</main>

<footer class="bg-light border-top py-3 mt-auto">
    <div class="container text-center text-muted small">
        UBS Intern Training, Task Manager
    </div>
</footer>

<asset:javascript src="application.js"/>
</body>
</html>