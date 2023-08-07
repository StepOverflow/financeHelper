<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<br>
<br>
<br>
<div class="container">
    <div class="jumbotron">
        <h3 class="text-center">Регистрация</h3>
        <br>
        <br>
        <div class="row">
            <div class="col-sm"></div>
            <div class="col-sm">
                <form action="/register" method="post">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Пароль:</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                </form>
                <br>
                <a class="btn btn-dark" href="/login" role="button">Уже есть аккаунт? Войти</a>
            </div>
            <div class="col-sm"></div>
        </div>
    </div>
</div>
</body>
</html>