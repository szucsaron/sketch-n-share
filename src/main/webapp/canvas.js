
let gCanvas = null;



function navigateToCanvas() {
    user = getAuthorization();
    showContents("canvas")
    const xhr = new XhrSender('GET', 'protected/sketch', onCanvasResponse);
    xhr.addParam('sketch_id', retrieveSketchId());
    xhr.send();
}

function storeSketchId(sketch) {
    storeItem('sketch', sketch);
}

function retrieveSketchId() {
    return retrieveItem('sketch');
}

function storeSketchHeader(id, name, folderId) {
    let header = {}
    header.id = id;
    header.name = name;
    header.folderId = folderId;
    storeItem('sketchHeader', header);
}

function retrieveSketchHeader() {
    return retrieveItem('sketchHeader');
}

function onCanvasResponse() {

    const shapeCreator = new ShapeCreator();
    gCanvas = new Canvas(shapeCreator, document.getElementById('canvas'), document.getElementById('draw-canvas'));

    const data = JSON.parse(this.responseText);
    storeSketchHeader(data.id, data.name, data.folderId);
    const content = JSON.parse(data.content);
    for (let i = 0; i < content.length; i++) {
        const obj = shapeCreator.createDrawObjectFromData(content[i]);
        gCanvas.addDrawObject(obj);
    }
    const dto = JSON.stringify( convertDrawObjectsToDto(gCanvas.drawObjects));
    gCanvas.drawObjects = null;
    console.log(dto);
    const drObjs = (convertDtoToDrawObjects(JSON.parse(dto)));
    console.log(drObjs);
    gCanvas.drawObjects = drObjs;
    gCanvas.refresh()
}

// Save


function onCanvasSaveClick() {
    console.log('saveee');
    console.log(gCanvas.drawObjects)
    const header = retrieveSketchHeader();
    const drawObjectsJson = JSON.stringify(gCanvas.drawObjects);
    params = new URLSearchParams();
    params.append('sketch_id', retrieveSketchId());
    params.append('folder_id', header.folderId);
    params.append('name', header.name);
    params.append('content', drawObjectsJson);

    const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onCanvasSaveResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'protected/sketch');
        xhr.send(params);
}

function onCanvasSaveResponse() {
    const data = JSON.parse(this.responseText);
    alert(data.message);
}

// Data handling
