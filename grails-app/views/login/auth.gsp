<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Sign in</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-12 col-sm-8 col-md-6 col-lg-4">
        <div class="card shadow-sm mt-4">
            <div class="card-body p-4">
                <h1 class="h4 text-center mb-1">UBS intern training</h1>
                <p class="text-muted text-center mb-4">Sign in to the task manager</p>

                <g:if test="${flash.message}">
                    <div class="alert alert-danger">${flash.message}</div>
                </g:if>

                <form action="${postUrl ?: '/login/authenticate'}" method="POST" autocomplete="off">
                    <div class="mb-3">
                        <label for="username" class="form-label">Email</label>
                        <input type="text" class="form-control" id="username"
                               name="${usernameParameter ?: 'username'}" autofocus required/>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password"
                               name="${passwordParameter ?: 'password'}" required/>
                    </div>
                    <div class="form-check mb-3">
                        <input type="checkbox" class="form-check-input" id="remember_me"
                               name="${rememberMeParameter ?: 'remember-me'}"
                               <g:if test="${hasCookie}">checked</g:if>/>
                        <label for="remember_me" class="form-check-label">Remember me</label>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Sign in</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>