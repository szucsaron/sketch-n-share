function navigateToFolderContent() {
    user = getAuthorization();
    showContents('folder-content');
    const xhr = new XhrSender('GET', 'protected/sketch', onFolderResponse);
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
    console.log(this.responseText);
    const sketches = JSON.parse(this.response);
    const folderContentEl = document.getElementById('folder-content');
    removeAllChildren(folderContentEl);
    const sketchesTable = createItemList('sketches-table', sketches, onSketchClicked, onSketchEditClicked, onSketchDeleteClicked);
    folderContentEl.appendChild(sketchesTable);
}

function onSketchClicked() {
    storeSketchId(this.getAttribute("item_id"));
    navigateToCanvas()
}

function onSketchEditClicked() {
    
}

function onSketchDeleteClicked() {
    
}