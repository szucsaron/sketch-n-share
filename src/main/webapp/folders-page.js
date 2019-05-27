function navigateToFoldersViewer() {
    user = getAuthorization();
    showContents('folders-page')
    const xhr = new XhrSender('GET', 'protected/folder', onFoldersResponse);
    xhr.send()
}

function storeUserFolders(userFolders) {
    storeItem('userFolders', userFolders);
}

function retrieveUserFolders() {
    return retrieveItem('userFolders');
}

function onFoldersResponse() {
    const foldersViewerEl = document.getElementById('folders-page');
    removeAllChildren(foldersViewerEl);
    gFolderItemList = new ItemList('folders-table', onFolderClicked);
    gFolderItemList.setAsEditable(onFolderEditRequested);
    gFolderItemList.setAsDeletable(onFolderDeleteRequested);
    gFolderItemList.setAsCreatable(onFolderCreateRequested);
    gFolderItemList.setAsShareable(onFolderShareRequested);

    const foldersTable = gFolderItemList.create();
    foldersViewerEl.appendChild(foldersTable);
    gFolderItemList.refreshWithNew(JSON.parse(this.response));
}

function onFolderUpdateResponse() {
    handleMessage(this.responseText)
    navigateToFoldersViewer();
}

// Go to sketch

function onFolderClicked() {
    storeFolderId(this.getAttribute('item_id'));
    navigateToFolderContent();
}

// Create new

function onFolderCreateRequested(res) {
    const xhr = new XhrSender('POST', 'protected/folder', onFolderUpdateResponse)    
    xhr.addParam('name', res.name);
    xhr.send();
}

// Edit

function onFolderEditRequested(res) {
    const xhr = new XhrSender('PUT', 'protected/folder', onFolderUpdateResponse)
    xhr.addParam('folder_id', res.id);
    xhr.addParam('name', res.name);
    xhr.send();
}

// Delete

function onFolderDeleteRequested(res) {
    console.log(res);

    const xhr = new XhrSender('DELETE', 'protected/folder', onFolderUpdateResponse)
    xhr.addParam('folder_id', res.id);
    xhr.send();
}

// Share

function onFolderShareRequested() {
    const folderId = this.getAttribute('item_id');
    storeFolderId(folderId);
    navigateToFolderSharedPage();
}