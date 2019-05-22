let gLineStarted = false;
let gX1 = 0;
let gY1 = 0;
let gDrawObjects = [];
let gMaxDrawObjectId = 0;


function navigateToCanvas() {
    user = getAuthorization();
    showContents("canvas")
    const xhr = new XhrSender('GET', 'protected/sketch', onCanvasResponse);
    xhr.addParam('sketch_id', retrieveFolderId());
    xhr.send();
}

function storeSketchId(sketch) {
    storeItem('sketch', sketch);
}

function retrieveSketchId() {
    return retrieveItem('sketch');
}

function onCanvasResponse() {
    const data = JSON.parse(this.responseText);
    const content = JSON.parse(data.content);
    for (let i = 0; i < content.length; i++) {
        const obj = createDrawObjectFromData(content[i]);
        addDrawObject(obj);
    }
    drawStoredObjectsToScreen();

}

function onCanvasClick() {
    if (true) { // should decide according to drawing mode
        lineDrawMode();
    }
 }
 
 function lineDrawMode() {
     const x = event.clientX;
     const y = event.clientY;
     if (gLineStarted) {
         let line = createLine([gX1, gY1], [x, y], "black");
         addDrawObject(line);
         drawObjectToScreen(line);
         gLineStarted = false;
     } else {
         gX1 = x;
         gY1 = y;
         gLineStarted = true;
     }
 }
 
 function createLineFromData(data) {
     return createLine(data.pos1, data.pos2, data.color);
 }
 
 function createLine(pos1, pos2, color) {
     let line = {};
     line.type = "line";
     line.id = null;
     line.pos1 = pos1;
     line.pos2 = pos2;
     line.color = color;
     line.getElement = function() {
        return drawLine(this.pos1, this.pos2);
     };
     return line;
 }
 
 // Should take a Line object:
 function drawLine(pos1, pos2) {
     // Adding event listener to clickable element
     const el = document.createElement("div");
 
     const x1 = pos1[0];
     const y1 = pos1[1];
     const x2 = pos2[0];
     const y2 = pos2[1];
     
     const angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
     length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
     //let content = "a";
     const style = "font-size:0px;transform: rotate("+angle+"deg);position:absolute;top:"+y1+"px;left:"+x1+"px;"
                    +  "width:" + length + "px";
     el.setAttribute("style", style);
     el.setAttribute("class", "draw_object line");
     return el;
 }
 
 function createDrawObjectFromData(obj) {
     if (obj.type == 'line') {
         return createLineFromData(obj);
     }
 }
 
 function addDrawObject(obj) {
     obj.id = gMaxDrawObjectId;
     gDrawObjects.push(obj);
     gMaxDrawObjectId++;
 }
 
 function getNextDrawObjectId() {
     return gMaxDrawObjectId;
 }
 
 function drawStoredObjectsToScreen() {
     const drawEl = document.getElementById("draw-canvas");
     drawEl.innerHTML = "";
     for (let i = 0; i < gDrawObjects.length; i++) {
         drawEl.appendChild(gDrawObjects[i].getElement());
         console.log(gDrawObjects[i].getElement());
     }
 }
 
 function drawObjectToScreen(obj) {
     document.getElementById("draw-canvas").appendChild(obj.getElement());
 }
 