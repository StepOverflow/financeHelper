<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Перевод средств</title>
</head>
<body>
<h2>Перевод средств</h2>
<form method="post" action="/transactions/transfer">
    <label>От счета (ID): <input type="text" name="fromAccountId"></label><br>
    <label>На счет (ID): <input type="text" name="toAccountId"></label><br>
    <label>Сумма: <input type="text" name="sum"></label><br>
    <label>Категории (через запятую): <input type="text" name="categoryIds"></label><br>
    <button type="submit">Перевести</button>
</form>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>