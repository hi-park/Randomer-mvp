package com.hipark.randomer.itemdetail

import com.hipark.randomer.data.Item
import com.hipark.randomer.data.Source.ItemsDataSource
import com.hipark.randomer.data.Source.ItemsRepository

class ItemDetailPresenter(
    private val itemId: String,
    private val itemsRepository: ItemsRepository,
    private val itemDetailView: ItemDetailContract.View
) : ItemDetailContract.Presenter {

    init {
        itemDetailView.presenter = this
    }

    override fun start() {
        openItem()
    }

    private fun openItem() {

        if(itemId.isEmpty()) {
            itemDetailView.showMissingItem()
            return
        }

        itemDetailView.setLoadingIndicator(true)
        itemsRepository.getItem(itemId, object: ItemsDataSource.GetItemCallback {
            override fun onItemLoaded(item: Item) {
                itemDetailView.setLoadingIndicator(false)
                showItem(item)
            }
            override fun onDataNotAvailable() {
                itemDetailView.showMissingItem()
            }
        })
    }

    override fun editItem() {
        if(itemId.isEmpty()) {
            itemDetailView.showMissingItem()
            return
        }
        itemDetailView.showEditItem(itemId)
    }

    override fun deleteItem() {
        if(itemId.isEmpty()) {
            itemDetailView.showMissingItem()
            return
        }
        itemsRepository.deleteItem(itemId)
        itemDetailView.showItemDeleted()
    }

    fun showItem(item: Item) {
        with(itemDetailView) {
            if(itemId.isEmpty()) {
                hideTitle()
                hideDescription()
            } else {
                showTitle(item.title)
                showDescription(item.description)
            }
        }
    }
}