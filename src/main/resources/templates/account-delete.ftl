<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Удаление аккаунта</title>
</head>
<body>
<h1>Удаление аккаунта</h1>

<form action="/accounts/delete" method="post">
    <div>
        <label for="accountId">Номер аккаунта:</label>
        <input type="number" id="accountId" name="accountId" required>

    </div>

    <div>
        <button type="submit">Удалить аккаунт</button>
    </div>
</form>

<p><a href="/accounts/list">Назад к списку аккаунтов</a></p>
</body>
</html>