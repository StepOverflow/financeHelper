<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Список транзакций</title>
</head>
<body>
<h1>Список транзакций</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Сумма</th>
        <th>От аккаунта</th>
        <th>К аккаунту</th>
    </tr>
    <tbody>
    <#list transactions as transaction>
        <tr>
            <td>${transaction.id}</td>
            <td>${transaction.amountPaid}</td>
            <td>${transaction.fromAccount.name}</td>
            <td>${transaction.toAccount.name}</td>
        </tr>
    </#list>
    </tbody>
</table>
<p><a href="/accounts/list">Назад к списку аккаунтов</a></p>
<p><a href="/transactions/output">Список исходящих транзакций</a></p>
<p><a href="/transactions/input">Список исходящих транзакций</a></p>
</body>
</html>
