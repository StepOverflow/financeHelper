<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список категорий</title>
</head>
<body>
<h1>Список категорий</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
    </tr>
    </thead>
    <tbody>
    <#list categories as category>
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
        </tr>
    </#list>
    </tbody>
</table>
<br>
<p><a href="/categories/create">Создать новую категорию</a></p>
<p><a href="/categories/delete">Удалить категорию</a></p>
<p><a href="/categories/edit">Редактировать категорию</a></p>
<a href="/">Вернуться на главную</a><br>
</body>
</html>