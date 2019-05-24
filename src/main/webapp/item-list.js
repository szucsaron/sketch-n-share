class ItemList {
    constructor(id, items, onItemClicked, onEditDone, onDeleteClicked) {
        this.items = items;
        this._onItemClicked = onItemClicked;
        this._onEditDone = onEditDone;
        this._onDeleteClicked = onDeleteClicked;
        this.el = document.createElement('table');
        this.el.setAttribute('id', id);
        this._buildTable(items);
    }

    _buildTable() {
        for (let i = 0; i < this.items.length; i++) {
            const trEl = document.createElement('tr');
    
            const itemNameEl = document.createElement('td');
            itemNameEl.setAttribute('item_id', this.items[i].id);
            itemNameEl.textContent = this.items[i].name;
            if (onItemClicked != undefined) {
                itemNameEl.addEventListener('click', this._onItemClicked);
            }
    
            const itemEditEl = document.createElement('td');
            itemEditEl.setAttribute('item_id', this.items[i].id);
            itemEditEl.textContent = "Edit";
            if (onItemEditclicked != undefined) {
                itemEditEl.addEventListener('click', this._onEditClicked.bind(this));
            }
            
            const itemDeleteEl = document.createElement('td');
            itemDeleteEl.setAttribute('item_id', this.items[i].id);
            itemDeleteEl.textContent = "Delete";
            if (onItemDeleteClicked != undefined) {
                itemDeleteEl.addEventListener('click', this.__onDeleteClicked);
            }
    
            trEl.appendChild(itemNameEl);
            trEl.appendChild(itemEditEl);
            trEl.appendChild(itemDeleteEl);
            this.el.appendChild(trEl);
        }
    }

    _onEditClicked(res) {
        console.log(res);
        console.log(this);
    }
}