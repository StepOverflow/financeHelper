<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет о расходах</title>
</head>
<body>
<h2>Отчет о расходах</h2>
<p>Результат:</p>
<ul>
    <#list report?keys as key>
        <li>${key}: ${report[key]}</li>
    </#list>
</ul>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>