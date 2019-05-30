function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    localStorage.removeItem('adminMode');
    return localStorage.removeItem('user');
}

function onLoginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        // storeAdminMode(true);
        navigateToFoldersViewer();
    } else {
        handleError(this.JSON)
    }
}

function onLoginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="name"]');
    const passwordInputEl = loginFormEl.querySelector('input[name="password"]');

    const name = emailInputEl.value;
    const password = passwordInputEl.value;

    const xhr = new XhrSender('POST', 'login', onLoginResponse);
    xhr.addParam('name', name);
    xhr.addParam('password', password);
    xhr.send();

}

function onRegisterButtonClicked() {
    showContents(['register-content']);
}

function onLogoutResponse() {
    if (this.status === OK) {
        setUnauthorized();
        showContents(['login-page'])
    } else {
        onOtherResponse(logoutContentDivEl, this);
    }
}

function onLogoutButtonClicked(event) {
    const xhr = new XhrSender('POST', 'protected/logout', onLogoutResponse)
    xhr.send();
}

