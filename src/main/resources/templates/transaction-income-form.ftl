<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет о поступлениях</title>
</head>
<body>
<h2>Отчет о поступлениях</h2>
<form method="post" action="/transactions/income">
    <label>Количество дней: <input type="text" name="days"></label><br>
    <button type="submit">Получить отчет</button>
</form>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>