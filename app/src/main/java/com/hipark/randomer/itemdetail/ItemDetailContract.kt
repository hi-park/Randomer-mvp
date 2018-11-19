package com.hipark.randomer.itemdetail

import com.hipark.randomer.BasePresenter
import com.hipark.randomer.BaseView

interface ItemDetailContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMissingItem()

        fun hideTitle()

        fun showTitle(title: String)

        fun hideDescription()

        fun showDescription(description: String)

        fun showEditItem(itemId: String)

        fun showItemDeleted()

    }

    interface Presenter : BasePresenter {

        fun editItem()

        fun deleteItem()
    }
}