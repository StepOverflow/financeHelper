<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактирование аккаунта</title>
</head>
<body>
<h1>Редактирование аккаунта</h1>
<form action="/accounts/edit" method="post">
    <div>
        <label for="accountId">Номер аккаунта:</label>
        <input type="number" id="accountId" name="accountId" required>
    </div>
    <div>
        <label for="name">Новое имя:</label>
        <input type="text" id="name" name="name" value="${accountForm.name!}" required>
    </div>
    <div>
        <button type="submit">Сохранить</button>
    </div>
</form>

<p><a href="/accounts/list">Назад к списку аккаунтов</a></p>
</body>
</html>