<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Чек транзакции</title>
</head>
<body>
<h1>Чек транзакции</h1>
<p>Транзакция успешно совершена:</p>
<p>Сумма перевода: ${transaction.sum}</p>
<p>Дата и время: ${transaction.createdDate}</p>
<p>От аккаунта: ${transaction.sender}</p>
<p>К аккаунту: ${transaction.recipient}</p>
<p><a href="/transactions/transfer">Вернуться к переводам</a></p>
</body>
</html>