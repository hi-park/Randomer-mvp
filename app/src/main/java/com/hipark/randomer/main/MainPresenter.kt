package com.hipark.randomer.main

import android.app.Activity
import com.hipark.randomer.addedititem.AddEditItemActivity
import com.hipark.randomer.data.Item
import com.hipark.randomer.data.Source.ItemsDataSource
import com.hipark.randomer.data.Source.ItemsRepository
import java.util.*

class MainPresenter(val itemsRepository: ItemsRepository,
                    val itemsView: MainContact.View)
    : MainContact.Presenter {

    private var firstLoad = true

    init {
        itemsView.presenter = this
    }

    override fun start() {
        loadItems(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        if(AddEditItemActivity.REQUEST_ADD_ITEM ==
                requestCode && Activity.RESULT_OK == resultCode) {
            itemsView.showSuccessfullySavedMessage()
        }
    }

    override fun loadItems(forceUpdate: Boolean) {
        loadItems(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadItems(forceUpdate: Boolean, showLoadingUI: Boolean) {

        if(showLoadingUI) {
            itemsView.setLoadingIndicator(true)
        }

        if(forceUpdate) {
            itemsRepository.refreshItems()
        }

        itemsRepository.getItems(object: ItemsDataSource.LoadItemsCallback {
            override fun onItemsLoaded(items: List<Item>) {

                val itemsToShow = ArrayList<Item>()

                for(item in items) {
                    itemsToShow.add(item)
                }

                if(showLoadingUI) {
                    itemsView.setLoadingIndicator(false)
                }

                processItems(itemsToShow)
            }

            override fun onDataNotAvaiilable() {

                itemsView.showLoadingItemsError()
            }
        })
    }

    override fun addNewItem() {
        itemsView.showAddItem()
    }

    override fun openItemDetails(requestedItem: Item) {
        itemsView.showItemDetailsUi(requestedItem.id)
    }

    private fun processItems(items: List<Item>) {

        if(items.isEmpty()) {
            processEmptyItems()
        } else {
            itemsView.showItems(items)
        }
    }

    private fun processEmptyItems() {
        itemsView.showItems(ArrayList<Item>())
        itemsView.showNoItems()
    }
}