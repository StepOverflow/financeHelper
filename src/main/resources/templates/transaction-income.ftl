<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отчет о поступлениях</title>
</head>
<body>
<h2>Отчет о поступлениях</h2>
<p>Результат:</p>
<ul>
    <#list report?keys as key>
        <li>${key}: ${report[key]}</li>
    </#list>
</ul>
<a href="/accounts/list">Вернуться к списку счетов</a>
</body>
</html>