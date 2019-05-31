function navigateToUserMgrPage() {
    const xhr = new XhrSender('GET', 'protected/user_edit', onUserGetResponse)
    xhr.addParam('user_id', retrieveUserId());
    xhr.send();
}

function storeUserId(id) {
    storeItem('userId', id);
}

function retrieveUserId() {
    return retrieveItem('userId');
}

function onUserGetResponse() {
    const user = JSON.parse(this.responseText);
    handlePageTransition('user-mgr-page');

    const nameEl = document.getElementById('user-mgr-input-name');
    nameEl.value = user.name;

    const passwdEl = document.getElementById('user-mgr-input-password');

    if (user.role == 'ADMIN') {
        document.getElementById('user-mgr-input-rad-admin').checked = true;
    } else {
        document.getElementById('user-mgr-input-rad-regular').checked = true;

    }
    if (getAuthorization().id == user.id) {
        handleTextMessage("Current user account. Modifying values must be followed with a re-login to take effect");
    }
    passwdEl.value = user.password;
    console.log(user);
}

function saveUser() {
    const name = document.getElementById('user-mgr-input-name').value;
    const password = document.getElementById('user-mgr-input-password').value;
    let role;
    if (document.getElementById('user-mgr-input-rad-admin').checked) {
        role = 'ADMIN';
    } else {
        role = 'REGULAR';
    }
    const userId = retrieveUserId();

    console.log(name + " " + password);
    const xhr = new XhrSender('POST', 'protected/user_edit', onUserMgrUpdateResponse);
    xhr.addParam('user_id', userId);
    xhr.addParam('user_name', name);
    xhr.addParam('password', password);
    xhr.addParam('role', role);
    xhr.send();
    if (getAuthorization().id == userId) {
        alert('Please, log in again to use your updated account');
        onLogoutButtonClicked();
    }
}

function onUserMgrUpdateResponse() {
    handleMessage(this.responseText);
    navigateToUserMgrPage();
}