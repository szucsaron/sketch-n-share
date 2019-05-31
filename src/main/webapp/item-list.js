


class ItemList {
    constructor(id, onItemClicked) {
        this._onItemClickedCallback = onItemClicked;
        this._onEditDoneCallback = null;
        this._onDeleteClickedCallback = null;
        this._onNewItemCallback = null;
        this._onShareClickedCallback = null;
        this._onOwnerChangeCallback = null;

        this.el = document.createElement('div');
        this.el.setAttribute('id', id);
        this.tableEl = document.createElement('table');
        
        this.el.appendChild(this.tableEl);

        this._fieldCreators = [];
        this._editValueEl = null;
        this.header = null;

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

    setAsOwnable(ownerChangeCallback) {
        this._onOwnerChangeCallback = ownerChangeCallback;
        this._fieldCreators.push(this._generateOwnerField.bind(this));
    }

    refreshWithNew(items) {
        this.items = items;
        if (items.length == 0 | items == null | items == undefined) {
        this.tableEl.innerHTML = '--------------------------------------';
        } else {
            this.refresh();
        }
    }

    refresh() {
        this.tableEl.innerHTML = '';
        if (this.header != null) {
            const headerRow = document.createElement('tr');
            for (let i =0; i < this.header.length; i++) {
                const tdEl = document.createElement('td');
                tdEl.textContent = this.header[i];
                headerRow.appendChild(tdEl);
            }
            this.tableEl.appendChild(headerRow);
        }
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

    _generateOwnerField(item) {
        const itemEditEl = document.createElement('td');
        itemEditEl.setAttribute('item_id', item.id);
        itemEditEl.textContent = item.owner;
        itemEditEl.addEventListener('click', this._onOwnerChangeClicked.bind(this));
        return itemEditEl;
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
        this._transformRowToEditable(el.parentElement);
    }

    _onDeleteClicked(res) {
        const el = this._get_target(res);
        let result = {};
        result.id = el.getAttribute('item_id');
        this._onDeleteClickedCallback(result);
    }

    _onNewClicked(res) {
        let item = {}
        item.id = 'new';
        item.name = 'New';
        const rowEl = this._generateRow(item);
        this._transformRowToEditable(rowEl);
        this.tableEl.appendChild(rowEl);
    }

    _onOwnerChangeClicked(res) {
        const el = this._get_target(res);
        this._transformFieldToEditable(el, this._changeOwner);
    }

    _transformRowToEditable(rowEl) {
        const el = rowEl.childNodes[0];
        this._transformFieldToEditable(el, this._saveItem);
    }

    _transformFieldToEditable(el, saveCallback) {
        // el.removeEventListener('click', this._onItemClickedCallback);
        const itemId = el.getAttribute('item_id');

        let new_element = el.cloneNode(true);
        el.parentNode.replaceChild(new_element, el);

        const inputEl = document.createElement('input');
        inputEl.value = new_element.textContent;
        inputEl.setAttribute('item_id', itemId);

        new_element.textContent = '';
        new_element.appendChild(inputEl);

        const createButtonEl = document.createElement('button');
        createButtonEl.setAttribute('item_id', itemId);
        createButtonEl.textContent = 'Save';
        createButtonEl.addEventListener('click', saveCallback.bind(this));
        new_element.appendChild(createButtonEl);
        this._editValueEl = inputEl;
    }

    _saveItem() {
        const id = this._editValueEl.getAttribute('item_id');
        let result = {};
        result.name = this._editValueEl.value;
        if (id == 'new') {
            this._onNewItemCallback(result);
        } else {
            result.id = id;
            this._onEditDoneCallback(result);
        }
    }

    _changeOwner() {
        let result = {};
        result.id = this._editValueEl.getAttribute('item_id');;
        result.name = this._editValueEl.value;
        this._onOwnerChangeCallback(result)
    }
}