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
    <c:url value="/users-list-page.js" var="usersListUrl"/>
    <c:url value="/user-mgr-page.js" var="userMgrUrl"/>



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
    <script src="${usersListUrl}"></script>
    <script src="${userMgrUrl}"></script>




    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>
<body>
    <div id="navbar" class="inner_utility hidden">
        <button id="admin-mode-button" class="hidden">Enter admin mode</button>
        <button id="logout-button">Logout</button>
    </div>
    <div id="message-bar"></div>
    <div id="canvas-page" class="hidden page">
        <div id="canvas-toolbar">
            <button id="canvas-save">Save</button>
            <button id="canvas-back">Back</button>
            <button id="canvas-drawmode-draw">Draw</button>
            <button id="canvas-drawmode-delete">Delete</button>
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
        </form>
    </div>
    <div id="folders-page" class="hidden page">
        <div class="user_text">My Folders</div>
        <div class="admin_text hidden">
            <button id="users-list-button">
                View users
            </button>
            <br>
            All Folders
        </div>
        <div id="owner-folders"></div>
        Folders shared with me:
        <div id="shared-folders"></div>
    </div>
    <div id="folder-page" class="hidden page">
        Sketches in folder
        <div id ="folder-page-content"></div>
        <button id="folder-back">Back</button>
    </div>
    <div id="folder-share-page" class="hidden page">
        Users with shares
        <div id ="folder-share-page-content"></div>
        <button id="folder-share-back">Back</button>
    </div>
    <div id="users-list-page" class="hidden page">
        Click on a user for editing
        <div id="users-list-page-content"></div>
        <button id="users-list-page-back">Back</button>
    </div>
    <div id="user-mgr-page" class="hidden page">
        <h1>Manage User</h1>
        <form id="user-mgr-form" onsubmit="return false;">
            Name<br>
            <input id="user-mgr-input-name" type="text" name="name" ><br>
            Password<br>
            <input id="user-mgr-input-password" type="text" name="password"><br>
            Role<br>
            Regular<input id="user-mgr-input-rad-regular" type="radio" name="role" value="REGULAR" checked><br>
            Admin<input id="user-mgr-input-rad-admin" type="radio" name="role" value="ADMIN" checked><br>
            <button id="user-mgr-save-button">Save</button>
        </form>
        <button id="users-mgr-page-back">Back</button>
    </div>
    
    
</body>
</html>
