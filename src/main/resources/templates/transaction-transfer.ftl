<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Перевод денег</title>
</head>
<body>
<h1>Перевод денег</h1>
<form action="/transactions/transfer" method="post">
    <label for="fromAccountId">От аккаунта:</label>
    <select id="fromAccountId" name="fromAccountId">
        <#list userAccounts as account>
            <option value="${account.id}">${account.name}</option>
        </#list>
    </select>
    <br>
    <label for="toAccountNumber">На аккаунт (номер):</label>
    <input type="text" name="toAccountNumber" required><br>
    <label for="sum">Сумма:</label>
    <input type="number" id="sum" name="sum">
    <br>
    <button type="submit">Перевести</button>
</form>
<p><a href="/accounts/list">Назад к списку аккаунтов</a></p>
</body>
</html>
