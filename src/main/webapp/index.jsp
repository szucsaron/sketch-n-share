<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sketch n Share</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:url value="/style.css" var="styleUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <c:url value="/folders-viewer.js" var="foldersViewerUrl"/>
    <c:url value="/folder-content.js" var="folderContentUrl"/>

    <script src="${indexScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${foldersViewerUrl}"></script>
    <script src="${folderContentUrl}"></script>


    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="login-content" class="content">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="name" placeholder="Name">
            <input type="password" name="password" placeholder="Password">
            <button id="login-button">Login</button>
            <button id="register-button">Register</button>
        </form>
    </div>
    <div id="register-content" class="hidden content">
        <h1>Register</h1>
        <form id="register-form" onsubmit="return false;">
            <input type="text" name="email" placeholder="Email">
            <br>
            <input type="password" name="password" placeholder="Password">
            <br>
            <input type="password" name="repassword" placeholder="Re-enter password">
            <br>
            <input type="text" name="name" placeholder="Name">
            <br>
            <button id="registration-button">Register</button>
        </form>
    </div>
    <div id="folders-viewer" class="hidden content">
        These are folders
    </div>
    <div id="folder-content" class="hidden content">
            This is a folder
    </div>
    <div id="canvas" class="hidden content">
            This is a canvas
    </div>
    <div id="navbar" >
        <button id="logout-button">Logout</button>
    </div>
</body>
</html>
