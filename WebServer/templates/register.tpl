<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
<#if errorMsg??>
    <p id="error" style="color:red;">${errorMsg}</p>
</#if>

<#if infoMsg??>
    <p id="info" style="color:green;">${infoMsg}</p>
</#if>

<form method="post">
    <label for=username title="Username: ">
        <input id="username" type="text" name="username"/>
    </label>
    <br/>
    <label for="password" title="Password: ">
        <input id="password" type="text" name="password"/>
    </label>
    <input type="submit" value="Register!"/>
</form>
<div>
    <img src="/kot3.jpeg"/>
</div>
</body>
</html>