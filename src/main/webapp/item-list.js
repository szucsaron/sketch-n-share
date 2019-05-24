class ItemList {
    constructor(id, items, onItemClicked, onEditDone, onDeleteClicked) {
        this.items = items;
        this._onItemClicked = onItemClicked;
        this._onEditDone = onEditDone;
        this._onDeleteClicked = onDeleteClicked;
        this.el = document.createElement('table');
        this.el.setAttribute('id', id);
        this.refresh(items);
    }

    create() {
        return this.el;
    }

    refresh() {
        this.el.innerHTML = '';
        for (let i = 0; i < this.items.length; i++) {
            const trEl = document.createElement('tr');
    
            const itemNameEl = document.createElement('td');
            itemNameEl.setAttribute('item_id', this.items[i].id);
            itemNameEl.textContent = this.items[i].name;
            if (this._onItemClicked != undefined) {
                itemNameEl.addEventListener('click', this._onItemClicked);
            }
    
            const itemEditEl = document.createElement('td');
            itemEditEl.setAttribute('item_id', this.items[i].id);
            itemEditEl.setAttribute('mode', 'edit');
            itemEditEl.textContent = "Edit";
            if (this._onEditDone != undefined) {
                itemEditEl.addEventListener('click', this._onEditClicked.bind(this));
            }
            
            const itemDeleteEl = document.createElement('td');
            itemDeleteEl.setAttribute('item_id', this.items[i].id);
            itemDeleteEl.textContent = "Delete";
            if (this._onDeleteClicked != undefined) {
                itemDeleteEl.addEventListener('click', this._delete.bind(this));
            }
    
            trEl.appendChild(itemNameEl);
            trEl.appendChild(itemEditEl);
            trEl.appendChild(itemDeleteEl);
            this.el.appendChild(trEl);
        }
    }

    _delete(res) {
        const el = this._get_target(res);
        let result = {};
        result.id = el.getAttribute('item_id');
        result.itemList = this;
        this._onDeleteClicked(result);
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

    _get_target(res) {
        const el = res.originalTarget;
        if (el == undefined) {
            return res.srcElement;
        } else {
            return el;
        }
    }

    _onEdit(res, el) {
        el.setAttribute('mode', 'save');
        el.textContent = 'Save';

        const nameEl = el.parentElement.childNodes[0];
        nameEl.removeEventListener('click', this._onItemClicked);

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
        result.id = tdEl.getAttribute('item_id');
        result.name = nameEl.value;
        result.itemList = this;
        this._onEditDone(result);
    }
}