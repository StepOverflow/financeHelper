<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет о доходах</title>
</head>
<body>
<h2>Отчет о доходах</h2>
<form method="get" action="/transactions/income">
    <label>Количество дней: <input type="text" name="days"></label><br>
    <button type="submit">Получить отчет</button>
</form>
<p>Результат:</p>
<p ${report}"></p>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>