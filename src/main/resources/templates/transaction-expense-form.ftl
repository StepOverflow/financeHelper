<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет о расходах</title>
</head>
<body>
<h2>Отчет о расходах</h2>
<form method="post" action="/transactions/expense">
    <label>Количество дней: <input type="text" name="days"></label><br>
    <button type="submit">Получить отчет</button>
</form>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>