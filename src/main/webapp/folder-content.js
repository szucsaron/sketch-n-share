function navigateToFolderContent() {
    user = getAuthorization();
    showContents('folder-content');
    const xhr = XhrSender('GET', 'protected/sketch', onFolderResponse);
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.send();
    console.log(user)
}

function storeFolderId(folder) {
    storeItem('folderId', folder);
}

function retrieveFolderId() {
    return retrieveItem('folderId');
}

function onFolderResponse() {

}