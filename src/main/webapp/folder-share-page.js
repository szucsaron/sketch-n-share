function navigateToFolderSharedPage() {
    showContents('folder-share-page');
    const xhr = new XhrSender('GET', 'protected/folder_share', onSharedFoldersResponse);
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.send();
}

function onSharedFoldersResponse() {
    gFolderShareItemList = new ItemList('folder-share-item-list', onShareClicked);
    gFolderShareItemList.setAsCreatable(onFolderShareCreateRequested)
    gFolderShareItemList.setAsDeletable(onFolderShareDeleteRequested);
    gFolderShareItemList.refreshWithNew(JSON.parse(this.responseText));
    const sharedEl = document.getElementById('folder-share-page');
    removeAllChildren(sharedEl);
    sharedEl.appendChild(gFolderShareItemList.create());
}

function onShareClicked() {

}

function onFolderShareUpdateResponse() {
    handleMessage(this.responseText);
    navigateToFolderSharedPage();
}

function onFolderShareCreateRequested(res) {
    console.log(res)
    const xhr = new XhrSender('POST', 'protected/folder_share', onFolderShareUpdateResponse)
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.addParam('user_name', res.name);
    xhr.send();
}


function onFolderShareDeleteRequested(res) {
    console.log(res);
    const xhr = new XhrSender('DELETE', 'protected/folder_share', onFolderShareUpdateResponse)
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.addParam('user_id', res.id);
    xhr.send();
}