<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход выполнен</title>
</head>
<body>
<h1>Вход успешно выполнен</h1>
<p>Вы вошли как: ${userDto.email}</p>
<a href="/accounts/list">Аккаунты</a>
<a href="/categories/list">Категории</a>
<a href="/transactions/transfer">Перевод</a>
<a href="/transactions/income">Входящие транзакции</a>
<a href="/transactions/expense">Исходящие транзакции</a>

<a href="/">Перейти на главную страницу</a>
<a href="/logout">Выйти</a>
</body>
</html>