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
    const folders = JSON.parse(this.response);
    const foldersViewerEl = document.getElementById('folders-page');
    removeAllChildren(foldersViewerEl);
    const itemList = new ItemList('folders-table', folders, onFolderClicked, onFolderEditDone, onFolderDeleteClicked);
    const foldersTable = itemList.create();
    foldersViewerEl.appendChild(foldersTable);
}

function onFolderClicked() {
    storeFolderId(this.getAttribute('item_id'));
    navigateToFolderContent();
}

function onFolderEditDone(res) {
    console.log(res);
    res.itemList.refresh();
}

function onFolderDeleteClicked(res) {
    console.log(res)
    res.itemList.refresh();
}
