function navigateToFolderContent() {
    user = getAuthorization();
    showContents("folder-content")
    console.log(user)
}

function storeFolder(folder) {
    storeItem('folder', folder);
}

function retrieveFolder() {
    return retrieveItem('folder');
}