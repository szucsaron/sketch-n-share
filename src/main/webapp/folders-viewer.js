function navigateToFoldersViewer() {
    user = getAuthorization();
    showContents("folders-viewer")
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
    console.log(this)
}