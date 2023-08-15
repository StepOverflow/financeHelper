<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание категории</title>
</head>
<body>
<h1>Создание категории</h1>

<form action="/categories/create" method="post">
    <div>
        <label for="name">Название категории:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div>
        <button type="submit">Создать</button>
    </div>
</form>

<p><a href="/categories/list">Список категорий</a></p>
</body>
</html>