<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<br>
<br>
<br>
<div class="container">
    <div class="jumbotron">
        <h3 class="text-center">Вход</h3>
        <br>
        <br>
        <form action="/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Войти</button>
        </form>
        <br>
        <br>
        <a class="btn btn-dark" href="/register" role="button">Зарегистрироваться</a>
    </div>
</div>
<script src="js/bootstrap.min.js"/>
</body>
</html>