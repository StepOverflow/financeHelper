<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список аккаунтов</title>
</head>
<body>
<h1>Список аккаунтов</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Имя</th>
    </tr>
    </thead>
    <tbody>
    <#list accounts as account>
        <tr>
            <td>${account.id}</td>
            <td>${account.accountName}</td>
        </tr>
    </#list>
    </tbody>
</table>
x
<br>
<a href="/accounts/create">Создать новый аккаунт</a> <br>
<a href="/accounts/delete">Удалить аккаунт</a><br>
<a href="/accounts/edit">Редактировать аккаунт</a><br>
<a href="/">Вернуться на главную</a><br>
</body>
</html>