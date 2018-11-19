package com.hipark.randomer.addedititem

import com.hipark.randomer.BasePresenter
import com.hipark.randomer.BaseView

interface AddEditItemConstract {

    interface View : BaseView<Presenter> {

        fun showEmptyItemError()

        fun showItemsList()

        fun setTitle(title: String)

        fun setDescription(descripion: String)
    }

    interface Presenter : BasePresenter {

        fun saveItem(title: String, description: String)

        fun populateItem()
    }

}