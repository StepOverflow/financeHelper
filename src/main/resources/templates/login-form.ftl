<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<h2>Вход</h2>
<form action="/login" method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br>
    <button type="submit">Войти</button>
</form>
<p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
</body>
</html>