<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>A1-№3</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<form class="form-inline" action="/" method="post">
    <div class="form-group mb-2">
            <label for="pwd">Дата поставки с:</label>
            <input type="date" class="form-control" name="dateStart">
    </div>
    <div class="form-group mx-sm-3 mb-2">
        <label for="pwd">Дата поставки По:</label>
        <input type="date" class="form-control" name="dateEnd">
    </div>
    <div class="form-group mx-sm-3 mb-2">
    <select class="browser-default custom-select" name="select">
        <option selected>Авторизованная поставка?</option>
        <option value="false">нет</option>
        <option value="true">да</option>
    </select>
    </div>
    <button type="submit" class="btn btn-primary mb-2">Search</button>
</form>

</div>
<div class="container">
<table class="table table-bordered">
    <thead>
        <tr>
            <th scope="col">Mat.Doc</th>
            <th scope="col">Item</th>
            <th scope="col">Doc. Date</th>
            <th scope="col">Pstng. Date</th>
            <th scope="col">Material Description</th>
            <th scope="col">Quantity</th>
            <th scope="col">BUn</th>
            <th scope="col">Amount LC</th>
            <th scope="col">Crcy</th>
            <th scope="col">User Name</th>
            <th scope="col">Авторизованная поставка</th>

        </tr>
    </thead>
    <tbody>
    <#list postings as posting>
        <tr>
            <td>${posting.matDoc}</td>
            <td>${posting.item}</td>
            <td>${posting.docDate}</td>
            <td>${posting.pstngDate}</td>
            <td>${posting.materialDescription}</td>
            <td>${posting.quantity}</td>
            <td>${posting.bUn}</td>
            <td>${posting.amountLC}</td>
            <td>${posting.crcy}</td>
            <td>${posting.userName}</td>
            <td>${posting.authorizedDelivery?string('true', 'false')}</td>
        </tr>
    </#list>
    </tbody>
</table>
<div>


<table class="table table-bordered">
    <thead>
    <tr>
        <th scope="col">Application</th>
        <th scope="col">AppAccountName</th>
        <th scope="col">IsActive</th>
        <th scope="col">JobTitle</th>
        <th scope="col">Department</th>
    </tr>
    </thead>
    <tbody>
    <#list logins as login>
        <tr>
            <td>${login.application}</td>
            <td>${login.appAccountName}</td>
            <td>${login.active?string('true', 'false')}</td>
            <td>${login.jobTitle}</td>
            <td>${login.department}</td>
        </tr>
    </#list>
    </tbody>
</table>


</body>
</html>