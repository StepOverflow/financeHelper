<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактирование категории</title>
</head>
<body>
<h1>Редактирование категории</h1>
<form action="/categories/edit" method="post">
    <div>
        <label for="categoryId">Номер категории:</label>
        <input type="number" id="categoryId" name="categoryId" required>
    </div>
    <div>
        <label for="name">Новое имя:</label>
        <input type="text" id="name" name="name" value="${categoryForm.name!}" required>
    </div>
    <div>
        <button type="submit">Сохранить</button>
    </div>
</form>

<p><a href="/categories/list">Назад к списку категорий</a></p>
</body>
</html>
