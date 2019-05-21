const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let registerContentDivEl;
let profileContentDivEl;
let taskContentDivEl;
let tasksContentDivEl;
let scheduleContentDivEl;
let schedulesContentDivEl;
let backToProfileContentDivEl;
let logoutContentDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
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

    if (hasAuthorization()) {
        onProfileLoad(getAuthorization());
    }
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
        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', this.onResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open(this.method, this.url);
        xhr.send(this.params);
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

document.addEventListener('DOMContentLoaded', onLoad);
