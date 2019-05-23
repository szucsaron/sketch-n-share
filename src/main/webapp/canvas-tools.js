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

    loadDrawObjects(drawObjects) {
        this.drawObjects = drawObjects;
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
             let line = new LineShape([this.storedX, this.storedY], [x, y], "black");
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
    createDrawObjectFromData(obj) {
        if (obj.type == 'line') { // should branch to other types later
            return new LineShape(obj.pos1, obj.pos2, obj.color);
        }
    }
}

class LineShape {
    constructor(pos1, pos2, color) {
        this.type = "line";
        this.id = null;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = color;
    }

    getElement() {
        const x1 = this.pos1[0];
        const y1 = this.pos1[1];
        const x2 = this.pos2[0];
        const y2 = this.pos2[1];
        
        const el = document.createElement("div");
        const angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        //let content = "a";
        const style = "font-size:0px;transform: rotate("+angle+"deg);position:absolute;top:"+y1+"px;left:"+x1+"px;"
                    +  "width:" + length + "px";
        el.setAttribute("style", style);
        el.setAttribute("class", "draw_object line");
        return el;
    }
}

function convertDrawObjectsToDto(drawObjects) {
    let dto = {}
    dto.lines = []
    for (let i = 0; i < drawObjects.length; i++) {
        const obj = drawObjects[i]
        if (obj.type = 'line') {
            dto.lines.push([obj.pos1, obj.pos2, obj.color])
        }
    }
    return dto;
}

function convertDtoToDrawObjects(dto) {
    let drObjs = [];
    let idCount = 0;
    for (let i = 0; i < dto.lines.length; i++) {
        const line = dto.lines[i];
        let lineObj = new LineShape(line[0], line[1], line[2]);
        lineObj.id = idCount;
        drObjs.push(lineObj);
        idCount ++;
    }
    return drObjs;
}