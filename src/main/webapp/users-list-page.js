function navigateToUsersListPage() {
    const xhr = new XhrSender('GET', 'protected/users', usersListPageResponse)
    xhr.send();
    console.log('xhr sent');
}

function usersListPageResponse() {
    handlePageTransition('users-list-page');
    console.log(this.responseText);
    gUserItemList = new ItemList('user-item-list', onUserClicked);
    gUserItemList.setAsCreatable(onNewUserCreateRequest);
    gUserItemList.setAsDeletable(onUserDeleteRequest);

    const usersListEl = document.getElementById('users-list-page-content');
    removeAllChildren(usersListEl);
    usersListEl.appendChild(gUserItemList.create())
    gUserItemList.refreshWithNew(JSON.parse(this.responseText))
}

function onUserClicked(res) {
    storeUserId(this.getAttribute('item_id'));
    navigateToUserMgrPage();
}

function onUserListUpdateResponse() {
    handleMessage(this.responseText);
    navigateToUsersListPage();
}

function onNewUserCreateRequest(res) {
    const xhr = new XhrSender('POST', 'protected/users', onUserListUpdateResponse);
    xhr.addParam('user_name', res.name);
    xhr.addParam('password', 'password');
    xhr.addParam('role', 'REGULAR');
    xhr.send();
}

function onUserDeleteRequest(res) {
    const xhr = new XhrSender('DELETE', 'protected/users', onUserListUpdateResponse);
    xhr.addParam('user_id', res.id);
    xhr.send();
}