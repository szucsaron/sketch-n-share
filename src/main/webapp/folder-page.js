function navigateToFolderContent() {
    let xhr;
    if (hasShareMode()) {
        xhr = new XhrSender('GET', 'protected/sketches_shared', onSharedFolderResponse);
    } else {
        xhr = new XhrSender('GET', 'protected/sketches', onFolderResponse);
    }
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.send();
}

function storeFolderId(folder) {
    storeItem('folderId', folder);
}

function retrieveFolderId() {
    return retrieveItem('folderId');
}

function onFolderResponse() {
    handlePageTransition('folder-page');
    const folderContentEl = document.getElementById('folder-page-content');
    removeAllChildren(folderContentEl);
    gSketchItemList = new ItemList('sketch-item-list', onSketchClicked);
    gSketchItemList.setAsEditable(onSketchEditRequested);
    gSketchItemList.setAsCreatable(onSketchCreateRequested);
    gSketchItemList.setAsDeletable(onSketchDeleteRequested);
    folderContentEl.appendChild(gSketchItemList.create());
    gSketchItemList.refreshWithNew(JSON.parse(this.responseText));
}

function onSharedFolderResponse() {
    handlePageTransition('folder-page');
    const folderContentEl = document.getElementById('folder-page-content');
    removeAllChildren(folderContentEl);
    gSketchItemList = new ItemList('sketch-item-list', onSketchClicked);
    folderContentEl.appendChild(gSketchItemList.create());
    gSketchItemList.refreshWithNew(JSON.parse(this.responseText));
}

function onSketchClicked() {
    storeSketchId(this.getAttribute("item_id"));
    navigateToCanvas()
}

function onSketchEditRequested(res) { 
    console.log(res)
    const xhr = new XhrSender('PUT', 'protected/sketches', onSketchesUpdateResponse)
    xhr.addParam('id', res.id);
    xhr.addParam('name', res.name);
    xhr.send();
}

function onSketchDeleteRequested(res) {
    const xhr = new XhrSender('DELETE', 'protected/sketches', onSketchesUpdateResponse)
    xhr.addParam('id', res.id);
    xhr.send();
}

function onSketchCreateRequested(res) {
    console.log(res)
    const xhr = new XhrSender('POST', 'protected/sketches', onSketchesUpdateResponse)
    xhr.addParam('folder_id', retrieveFolderId());
    xhr.addParam('name', res.name);
    xhr.send();
}

function onSketchesUpdateResponse() {
    handleMessage(this.responseText);
    navigateToFolderContent();
}

function onFolderBackClicked() {
    navigateToFoldersViewer();
}