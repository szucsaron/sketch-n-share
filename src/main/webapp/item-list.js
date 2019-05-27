class ItemList {
    constructor(id, onItemClicked) {
        this._onItemClickedCallback = onItemClicked;
        this._onEditDoneCallback = null;
        this._onDeleteClickedCallback = null;
        this._onNewItemCallback = null;
        this._onShareClickedCallback = null;

        this.el = document.createElement('div');
        this.el.setAttribute('id', id);
        this.tableEl = document.createElement('table');
        
        this.el.appendChild(this.tableEl);

        this._fieldCreators = [];

    }

    create() {
        return this.el;
    }

    setAsEditable(editCallback) {
        this._onEditDoneCallback = editCallback;
        this._fieldCreators.push(this._generateEditField.bind(this));
    }

    setAsDeletable(deleteCallback) {
        this._onDeleteClickedCallback = deleteCallback;
        this._fieldCreators.push(this._generateDeleteField.bind(this));
    }

    setAsShareable(shareCallback) {
        this._onShareClickedCallback = shareCallback;
        this._fieldCreators.push(this._generateShareField.bind(this));
    }

    setAsCreatable(newItemCallback) {
        this._onNewItemCallback = newItemCallback;
        this.newBtEl = document.createElement('button');
        this.newBtEl.textContent = 'New';
        this.newBtEl.addEventListener('click', this._onNewClicked.bind(this));
        this.el.appendChild(this.newBtEl);
    }

    refreshWithNew(items) {
        this.items = items;
        this.refresh();
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
        if (this._onItemClickedCallback != null) {
            itemNameEl.addEventListener('click', this._onItemClickedCallback);
            trEl.appendChild(itemNameEl);
        }
        for (let i = 0; i < this._fieldCreators.length; i++) {
            trEl.appendChild(this._fieldCreators[i](item));
        }
        return trEl;
    }

    _generateEditField(item) {
        const itemEditEl = document.createElement('td');
        itemEditEl.setAttribute('item_id', item.id);
        itemEditEl.setAttribute('mode', 'edit');
        itemEditEl.textContent = "Edit";
        itemEditEl.addEventListener('click', this._onEditClicked.bind(this));
        return itemEditEl;
    }

    _generateEditField(item) {
        const itemEditEl = document.createElement('td');
        itemEditEl.setAttribute('item_id', item.id);
        itemEditEl.setAttribute('mode', 'edit');
        itemEditEl.textContent = "Edit";
        itemEditEl.addEventListener('click', this._onEditClicked.bind(this));
        return itemEditEl;
    }

    _generateDeleteField(item) {
        const itemDeleteEl = document.createElement('td');
        itemDeleteEl.setAttribute('item_id', item.id);
        itemDeleteEl.textContent = "Delete";
        itemDeleteEl.addEventListener('click', this._onDeleteClicked.bind(this));
        return itemDeleteEl;
    }

    _generateShareField(item) {
        const itemDeleteEl = document.createElement('td');
        itemDeleteEl.setAttribute('item_id', item.id);
        itemDeleteEl.textContent = "Manage shares";
        itemDeleteEl.addEventListener('click', this._onShareClickedCallback);
        return itemDeleteEl;
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
            this._transformFieldToEditable(el);
        } else if (mode == 'save') {
            this._saveItem(el);
        }
    }

    _transformFieldToEditable(el) {
        el.setAttribute('mode', 'save');
        if (el.getAttribute('item_id') == 'new') {
            el.textContent = 'Create';
        } else {
            el.textContent = 'Save';
        }

        const nameEl = el.parentElement.childNodes[0];
        nameEl.removeEventListener('click', this._onItemClickedCallback);

        const inputEl = document.createElement('input');
        inputEl.value = nameEl.textContent;
        inputEl.setAttribute('item_id', el.getAttribute('item_id'));

        nameEl.textContent = '';
        nameEl.appendChild(inputEl);

        //nameEl.appendChild(inputEl);
    }

    _saveItem(el) {
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
        let item = {}
        item.id = 'new';
        item.name = 'New';
        const rowEl = this._generateRow(item);
        this.tableEl.appendChild(rowEl);
    }
}