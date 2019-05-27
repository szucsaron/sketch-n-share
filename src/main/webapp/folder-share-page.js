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
    const fasz = []
    fasz[0] = {id: 0, name: 'fasz'};
    fasz[1] = {id: 1, name: 'pöcs'};
    gFolderShareItemList.refreshWithNew(JSON.parse(this.responseText));

    document.getElementById('folder-share-page').appendChild(gFolderShareItemList.create());
}

function onShareClicked() {

}

function onFolderShareCreateRequested(res) {
    alert('create share');
    console.log(res)
}

function onFolderShareDeleteRequested(res) {
    alert('delete share');
    console.log(res);
}