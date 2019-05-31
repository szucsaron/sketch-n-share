const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let gFolderItemList = null;
let gFolderShareItemList = null;
let gSketchItemList = null;
let gUserItemList = null;
let gCanvas = null;
let gShareMode = false;
let gLastPageId = '';
let gAdminMode = false;

function hideElementsByClassName(className) {
    const elements = document.getElementsByClassName(className);
    for (let i = 0; i < elements.length; i++) {
        elements[i].classList.add('hidden');
    }
}

function showElementsByClassName(className) {
    const elements = document.getElementsByClassName(className);
    for (let i = 0; i < elements.length; i++) {
        elements[i].classList.remove('hidden');
    }
}

function showElementById(id) {
    document.getElementById(id).classList.remove('hidden');
}

function hideElementById(id) {
    document.getElementById(id).classList.add('hidden');
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('page');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function handleMessage(resp) {
    handleTextMessage(JSON.parse(resp).message);
}

function handleTextMessage(txt) {
    document.getElementById('message-bar').textContent = txt;
}

function clearMessageBar() {
    document.getElementById('message-bar').textContent = '';
}

function handlePageTransition(pageElementId) {
    showContents(pageElementId);
    if (pageElementId != gLastPageId) {
        clearMessageBar();
    }
    gLastPageId = pageElementId;
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function enterAdminMode() {
    gAdminMode = true;
    hideElementsByClassName('user_text');
    showElementsByClassName('admin_text');
    document.getElementById('admin-mode-button').textContent = "Exit admin mode";
}

function exitAdminMode() {
    gAdminMode = false;
    showElementsByClassName('user_text');
    hideElementsByClassName('admin_text');
    document.getElementById('admin-mode-button').textContent = "Enter admin mode";
}

function hasAdminMode() {
    return gAdminMode;
}

function storeShareMode(isShareModeOn) {
    gShareMode = isShareModeOn
}

function hasShareMode() {
    return gShareMode
}

function storeItem(name, value) {
    return localStorage.setItem(name, JSON.stringify(value));
}

function retrieveItem(name) {
    return JSON.parse(localStorage.getItem(name));
}

function onLoad() {
    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const logoutButtonEl = document.getElementById('logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    const canvasBackButton = document.getElementById('canvas-back');
    canvasBackButton.addEventListener('click', onCanvasBackButtonClicked);

    document.getElementById('canvas-save').addEventListener('click', onCanvasSaveClick);

    const folderBackEl = document.getElementById('folder-back');
    folderBackEl.addEventListener('click', onFolderBackClicked);

    const folderShareBackEl = document.getElementById('folder-share-back');
    folderShareBackEl.addEventListener('click', onFolderShareBackClicked);

    const adminModeButton = document.getElementById('admin-mode-button');
    adminModeButton.addEventListener('click', onAdminModeButtonClicked);
    
    const usersListButton = document.getElementById('users-list-button');
    usersListButton.addEventListener('click', navigateToUsersListPage)

    const userListPageBackButton = document.getElementById('users-list-page-back');
    userListPageBackButton.addEventListener('click', navigateToFoldersViewer);
    
    const userMgrPageBackButton = document.getElementById('users-mgr-page-back');
    userMgrPageBackButton.addEventListener('click', navigateToUsersListPage);

    const userMgrPageSaveButton = document.getElementById('user-mgr-save-button');
    userMgrPageSaveButton.addEventListener('click', saveUser);
}

class XhrSender {
    constructor(method, url, onResponse) {
        this.method = method;
        this.url = url;
        this.onResponse = onResponse;
        this.params = new URLSearchParams();
    }

    addParam(name, value) {
        this.params.append(name, value)
    }

    send() {
        if (hasAdminMode()) {
            this.addParam('admin_mode', '1');
        }
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', this.onResponse);
        xhr.addEventListener('error', onNetworkError);
        
        if (this.method == 'POST') {
            xhr.open(this.method, this.url);
            xhr.send(this.params);
        } else {
            xhr.open(this.method, this.url + "?" + this.params.toString());
            xhr.send();
        }
    }
}

function createItemList(id, items, onItemClicked, onItemEditclicked, onItemDeleteClicked) {
    // items is an array which must contain data objects with id and name fields
    const tabEl = document.createElement('table');
    for (let i = 0; i < items.length; i++) {
        const trEl = document.createElement('tr');

        const itemNameEl = document.createElement('td');
        itemNameEl.setAttribute('item_id', items[i].id);
        itemNameEl.textContent = items[i].name;
        if (onItemClicked != undefined) {
            itemNameEl.addEventListener('click', onItemClicked);
        }

        const itemEditEl = document.createElement('td');
        itemEditEl.setAttribute('item_id', items[i].id);
        itemEditEl.textContent = "Edit";
        if (onItemEditclicked != undefined) {
            itemEditEl.addEventListener('click', onItemEditclicked);
        }
        
        const itemDeleteEl = document.createElement('td');
        itemDeleteEl.setAttribute('item_id', items[i].id);
        itemDeleteEl.textContent = "Delete";
        if (onItemDeleteClicked != undefined) {
            itemDeleteEl.addEventListener('click', onItemDeleteClicked);
        }

        trEl.appendChild(itemNameEl);
        trEl.appendChild(itemEditEl);
        trEl.appendChild(itemDeleteEl);
        tabEl.appendChild(trEl);
    }
    return tabEl;
}

function getClickEventTarget(res) {
    const el = res.originalTarget;
    if (el == undefined) {
        return res.srcElement;
    } else {
        return el;
    }
}

function onAdminModeButtonClicked() {
    if (hasAdminMode()) {
        exitAdminMode();
        navigateToFoldersViewer();
        handleTextMessage('Switched to normal mode');
    } else {
        enterAdminMode();
        navigateToFoldersViewer();
        handleTextMessage('Switched to admin mode');
    }
}



document.addEventListener('DOMContentLoaded', onLoad);
