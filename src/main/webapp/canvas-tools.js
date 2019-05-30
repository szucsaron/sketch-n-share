const DRAWMODE = {
    drawLine: 0,
    delete: 1
}

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
        this.__drawMode = DRAWMODE.drawLine;
        this.__specialModeActive = false;

        canvasEl.addEventListener('click', this.__onCanvasClick.bind(this));
        canvasEl.addEventListener('mousedown', this.__onCanvasMouseDown.bind(this));
        canvasEl.addEventListener('mouseup', this.__onCanvasMouseUp.bind(this));
    }
    
    loadDrawObjects(drawObjects) {
        this.drawObjects = drawObjects;
        for (let i = 0; i < this.drawObjects.length; i++) {
            this.drawObjects[i].mouseOverCallback = this.__onDrawObjectMouseOver.bind(this);
        }
    }

    refresh() {
        this.canvasEl.innerHTML = "";
        for (let i = 0; i < this.drawObjects.length; i++) {
            this.canvasEl.appendChild(this.drawObjects[i].getElement());
        }
    }

    addDrawButton(el) {
        el.addEventListener('click', this.__onDrawButtonClicked.bind(this));
    }
    
    addDeleteButton(el) {
        el.addEventListener('click', this.__onDeleteButtonClicked.bind(this));
    }

    addMessageHandler(msgHandler) {
        this.__handleMessage = msgHandler;
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
             let line = new LineShape([this.storedX, this.storedY], [x, y], "black", this.__onDrawObjectMouseOver.bind(this));
             this.lineEntryStarted = false;
             this.__drawObject(line);
         } else {
            this.storedX = x;
            this.storedY = y;
             this.lineEntryStarted = true;
         }
     }

    __onDeleteButtonClicked() {
        this.__handleMessage('Delete mode: Hold down mouse and hover over elements to delete')
        this.__drawMode = DRAWMODE.delete;
    }

    __onDrawButtonClicked() {
        this.__handleMessage('Line drawing mode: Click on canvas to draw lines');
        this.__drawMode = DRAWMODE.drawLine;
    }

    __onDrawObjectMouseOver(res) {
        if (this.__specialModeActive) {
            const el = getClickEventTarget(res);
            switch (this.__drawMode) {
                case DRAWMODE.delete:
                    this.__deleteDrawObject(el);
                    break;
            }
        }
    }

    __deleteDrawObject(el) {
        const index = getDrawObjectIndexByElement(this.drawObjects, el);
        if (index) {
            console.log(index);
            el.parentElement.removeChild(el);
            this.drawObjects.splice(index, 1);
        }
    }
    
    __onCanvasClick(res) {
        const x = event.clientX;
        const y = event.clientY;
        switch (this.__drawMode) {
            case DRAWMODE.drawLine:
                this.__handleLineDrawing(x, y);
                break;
        }
    }

    __onCanvasMouseUp() {
        this.__specialModeActive = false;
    }

    __onCanvasMouseDown() {
        this.__specialModeActive = true;
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
    constructor(pos1, pos2, color, mouseOverCallback) {
        this.type = "line";
        this.id = null;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.color = color;
        this.mouseOverCallback = mouseOverCallback;
    }

    getElement() {
        const x1 = this.pos1[0];
        const y1 = this.pos1[1];
        const x2 = this.pos2[0];
        const y2 = this.pos2[1];
        
        const el = document.createElement('div');
        const angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        length = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        //let content = "a";
        const style = 'font-size:0px;transform: rotate('+angle+'deg);position:absolute;top:'+y1+'px;left:'+x1+'px;'
                    +  'width:' + length + 'px';
        el.setAttribute('style', style);
        el.setAttribute('class', 'draw_object line');
        el.setAttribute('id', this.id);
        if (this.mouseOverCallback != undefined && this.mouseOverCallback != null) {
            el.addEventListener('mouseover', this.mouseOverCallback);
        }
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
    if (dto == null) {
        return [];
    }
    let drObjs = [];
    for (let i = 0; i < dto.lines.length; i++) {
        const line = dto.lines[i];
        let lineObj = new LineShape(line[0], line[1], line[2]);
        lineObj.id = "drwobj|" + i;
        drObjs.push(lineObj);
    }
    return drObjs;
}

function getDrawObjectIndexByElement(drawObjects, el) {
    const id = el.getAttribute('id');
    for (let i = 0; i < drawObjects.length; i++) {
        if (drawObjects[i].id == id) {
            return i;
        }
    }
    return null;
}