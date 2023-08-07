<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создать аккаунт</title>
</head>
<body>
<h1>Создать аккаунт</h1>
<form action="/accounts/create" method="post">
    <div>
        <label for="name">Имя аккаунта:</label>
        <input type="text" id="name" name="name" value="${accountForm.name!}" required>

    </div>
    <button type="submit">Создать</button>
</form>
<br>
<a href="/accounts/list">Вернуться к списку аккаунтов</a>
<br>
<a href="/">Вернуться на главную</a>
</body>
</html>