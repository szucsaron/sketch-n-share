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
    const foldersTable = createItemList('folders-table', folders, onFolderClicked, onFolderEditClicked, onFolderDeleteClicked);
    foldersViewerEl.appendChild(foldersTable);
}

function onFolderClicked() {
    storeFolderId(this.getAttribute('item_id'));
    navigateToFolderContent();
}

function onFolderEditClicked() {
    alert('edit')
}

function onFolderDeleteClicked() {
    alert('delete')
}