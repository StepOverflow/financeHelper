<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Удаление аккаунта</title>
</head>
<body>
<h1>Удаление аккаунта</h1>

<form action="/categories/delete" method="post">
    <div>
        <label for="categoryId">Номер аккаунта:</label>
        <input type="number" id="categoryId" name="categoryId" required>

    </div>

    <div>
        <button type="submit">Удалить аккаунт</button>
    </div>
</form>

<p><a href="/categories/list">Назад к списку аккаунтов</a></p>
</body>
</html>