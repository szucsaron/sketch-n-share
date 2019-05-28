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
    <c:url value="/item-list.js" var="itemListUrl"/>
    <c:url value="/login-page.js" var="loginScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/register-page.js" var="registerScriptUrl"/>
    <c:url value="/folders-page.js" var="foldersViewerUrl"/>
    <c:url value="/folder-page.js" var="folderContentUrl"/>
    <c:url value="/folder-share-page.js" var="folderShareUrl"/>
    <c:url value="/canvas-tools.js" var="canvasToolsUrl"/>
    <c:url value="/canvas-page.js" var="canvasUrl"/>

    <script src="${indexScriptUrl}"></script>
    <script src="${itemListUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <script src="${foldersViewerUrl}"></script>
    <script src="${folderContentUrl}"></script>
    <script src="${folderShareUrl}"></script>
    <script src="${canvasToolsUrl}"></script>

    <script src="${canvasUrl}"></script>



    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="canvas-page" class="hidden page">
        <div id="canvas-toolbar">
            <button id="canvas-save">Save</button>
            <button id="canvas-back">Back</button>
        </div>
        <div id="canvas">
        </div>
    </div>
    <div id="login-page" class="page">
        <h1>Login</h1>
        <form id="login-form" onsubmit="return false;">
            <input type="text" name="name" placeholder="Name">
            <input type="password" name="password" placeholder="Password">
            <button id="login-button">Login</button>
            <button id="register-button">Register</button>
        </form>
    </div>
    <div id="register-page" class="hidden page">
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
    <div id="folders-page" class="hidden page">
        My folders
        <div id="owner-folders"></div>
        Folders shared with me:
        <div id="shared-folders"></div>
    </div>
    <div id="folder-page" class="hidden page">
            This is a folder
    </div>
    <div id="folder-share-page" class="hidden page">
        Shares of folder ...
    </div>
    
    <div id="navbar" >
        <button id="logout-button">Logout</button>
    </div>
</body>
</html>
