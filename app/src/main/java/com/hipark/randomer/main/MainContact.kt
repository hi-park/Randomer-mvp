package com.hipark.randomer.main

import com.hipark.randomer.BasePresenter
import com.hipark.randomer.BaseView
import com.hipark.randomer.data.Item

interface MainContact {

    interface View: BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showItems(items: List<Item>)

        fun showAddItem()

        fun showItemDetailsUi(itemId: String)

        fun showLoadingItemsError()

        fun showNoItems()

        fun showSuccessfullySavedMessage()

    }

    interface Presenter: BasePresenter {

        fun result(requestCode: Int, resultCode: Int)

        fun loadItems(forceUpdate: Boolean)

        fun addNewItem()

        fun openItemDetails(requestedItem: Item)
    }
}