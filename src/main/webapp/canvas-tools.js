class Canvas {
    constructor(shapeCreator, canvasContainerEl, canvasEl) {
        this.drawObjects = [];
        this.canvasContainerEl = canvasContainerEl;
        this.canvasEl = canvasEl;
        this.shapeCreator = shapeCreator;
        this.lineEntryStarted = false;
        this.storedX = null;
        this.storedY = null;
        this.__onCanvasClick.bind(this);
       
        const boundOnClick = this.__onCanvasClick.bind(this);
        canvasContainerEl.addEventListener('click', boundOnClick);
    }

    refresh() {
        this.canvasEl.innerHTML = "";
        for (let i = 0; i < this.drawObjects.length; i++) {
            this.canvasEl.appendChild(this.drawObjects[i].getElement());
        }
    }

    __drawObject(obj) {
        this.addDrawObject(obj);
        this.__drawObjectToScreen(obj);
     }

    __drawObjectToScreen(obj) {
        this.canvasEl.appendChild(obj.getElement());
    }
     
    __handleLineDrawing(x, y) {
         if (this.lineEntryStarted) {
             let line = this.shapeCreator.createLine([this.storedX, this.storedY], [x, y], "black");
             this.lineEntryStarted = false;
             this.__drawObject(line);
         } else {
            this.storedX = x;
            this.storedY = y;
             this.lineEntryStarted = true;
         }
     }
     
     // Should take a Line object:
    
     
    // Mouse click handling and buttons
    
    
    __onCanvasClick(res) {
        console.log(this);
        const x = event.clientX;
        const y = event.clientY;
        if (true) { // should decide according to drawing mode
            this.__handleLineDrawing(x, y);
        }
     }

    addDrawObject(obj) {
        this.drawObjects.push(obj);
        obj.id = this.drawObjects.length;
    }

}

class ShapeCreator {
    createLine(pos1, pos2, color) {
        let line = {};
        line.type = "line";
        line.id = null;
        line.pos1 = pos1;
        line.pos2 = pos2;
        line.color = color;
        line.getElement = function() {
           return createLineElement(this.pos1, this.pos2);
        };
        return line;
    }

    createDrawObjectFromData(obj) {
        if (obj.type == 'line') {
            return this.__createLineFromData(obj);
        }
    }
    __createLineFromData(data) {
        return this.createLine(data.pos1, data.pos2, data.color);
    }
}

function createLineElement(pos1, pos2) {
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