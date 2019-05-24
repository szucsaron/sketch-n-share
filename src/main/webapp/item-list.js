class ItemList {
    constructor(id, items, onItemClicked, onEditDone, onDeleteClicked, onNewCreated) {
        this.items = items;
        this._onItemClickedCallback = onItemClicked;
        this._onEditDoneCallback = onEditDone;
        this._onDeleteClickedCallback = onDeleteClicked;
        this._onNewItemCallback = onNewCreated;

        this.el = document.createElement('div');
        this.el.setAttribute('id', id);
        this.tableEl = document.createElement('table');
        this.newBtEl = document.createElement('button');
        this.newBtEl.textContent = 'New';
        this.newBtEl.addEventListener('click', this._onNewClicked.bind(this));
        this.el.appendChild(this.tableEl);
        this.el.appendChild(this.newBtEl);
        this.refresh(items);
    }

    create() {
        return this.el;
    }

    refresh() {
        this.tableEl.innerHTML = '';
        for (let i = 0; i < this.items.length; i++) {
            const item = this.items[i];
            this.tableEl.appendChild(this._generateRow(item));
        }
    }

    _generateRow(item) {
        const trEl = document.createElement('tr');
        const itemNameEl = document.createElement('td');
        itemNameEl.setAttribute('item_id', item.id);
        itemNameEl.textContent = item.name;
        if (this._onItemClickedCallback != undefined) {
            itemNameEl.addEventListener('click', this._onItemClickedCallback);
        }

        const itemEditEl = document.createElement('td');
        itemEditEl.setAttribute('item_id', item.id);
        itemEditEl.setAttribute('mode', 'edit');
        itemEditEl.textContent = "Edit";
        if (this._onEditDoneCallback != undefined) {
            itemEditEl.addEventListener('click', this._onEditClicked.bind(this));
        }
        
        const itemDeleteEl = document.createElement('td');
        itemDeleteEl.setAttribute('item_id', item.id);
        itemDeleteEl.textContent = "Delete";
        if (this._onDeleteClickedCallback != undefined) {
            itemDeleteEl.addEventListener('click', this._onDeleteClicked.bind(this));
        }

        trEl.appendChild(itemNameEl);
        trEl.appendChild(itemEditEl);
        trEl.appendChild(itemDeleteEl);
        this.tableEl.appendChild(trEl);
        return trEl;
    }

    _get_target(res) {
        const el = res.originalTarget;
        if (el == undefined) {
            return res.srcElement;
        } else {
            return el;
        }
    }

    _onEditClicked(res) {
        const el = this._get_target(res);
        const mode = el.getAttribute('mode');
        if (mode == 'edit') {
            this._onEdit(res, el);
        } else if (mode == 'save') {
            this._onSave(res, el);
        }
    }

    _onEdit(res, el) {
        el.setAttribute('mode', 'save');
        el.textContent = 'Save';

        const nameEl = el.parentElement.childNodes[0];
        nameEl.removeEventListener('click', this._onItemClickedCallback);

        const inputEl = document.createElement('input');
        inputEl.value = nameEl.textContent;
        inputEl.setAttribute('item_id', el.getAttribute('item_id'));

        nameEl.textContent = '';
        nameEl.appendChild(inputEl);

        //nameEl.appendChild(inputEl);
    }

    _onSave(res, el) {
        const tdEl = el.parentElement.childNodes[0]
        const nameEl = tdEl.childNodes[0];
        const newName = nameEl.value;

        let result = {};
        const id = tdEl.getAttribute('item_id');
        result.name = nameEl.value;
        result.itemList = this;
        if (id == 'new') {
            this._onNewItemCallback(result);
        } else {
            result.id = id;
            this._onEditDoneCallback(result);
        }
    }

    _onDeleteClicked(res) {
        const el = this._get_target(res);
        let result = {};
        result.id = el.getAttribute('item_id');
        result.itemList = this;
        this._onDeleteClickedCallback(result);
    }

    _onNewClicked(res) {
        const el = this._get_target(res);
        let item = {}
        item.id = 'new';
        item.name = 'New';
        const row = this._generateRow(item);
    }
}