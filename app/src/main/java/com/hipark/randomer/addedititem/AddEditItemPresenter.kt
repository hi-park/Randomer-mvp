package com.hipark.randomer.addedititem

import com.hipark.randomer.data.Item
import com.hipark.randomer.data.Source.ItemsDataSource

class AddEditItemPresenter(
    private val itemId: String?,
    val itemRepository: ItemsDataSource,
    val addItemView: AddEditItemConstract.View
) : AddEditItemConstract.Presenter, ItemsDataSource.GetItemCallback {

    init {
        addItemView.presenter = this
    }

    override fun start() {
        if(itemId != null) {
            populateItem()
        }
    }

    override fun saveItem(title: String, description: String) {

        if(itemId == null) {
            createItem(title, description)
        } else {
            updateItem(title, description)
        }
    }

    private fun createItem(title: String, description: String) {
        val newItem = Item(title, description)
        if(newItem.isEmpty) {
            addItemView.showEmptyItemError()
        } else {
            itemRepository.saveItem(newItem)
            addItemView.showItemsList()
        }
    }

    private fun updateItem(title: String, description: String) {
        if(itemId == null) {
            throw RuntimeException("updateitem() was called but item is new.")
        }
        itemRepository.saveItem(Item(title, description, itemId!!))
        addItemView.showItemsList()
    }

    override fun populateItem() {
        if(itemId == null) {
            throw RuntimeException("populateItem() was called but item is new.")
        }

        itemRepository.getItem(itemId, this)
    }

    override fun onItemLoaded(item: Item) {
        addItemView.setTitle(item.title)
        addItemView.setDescription(item.description)
    }

    override fun onDataNotAvailable() {

    }

}