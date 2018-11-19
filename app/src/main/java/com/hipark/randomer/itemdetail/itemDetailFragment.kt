package com.hipark.randomer.itemdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.hipark.randomer.R
import com.hipark.randomer.addedititem.AddEditItemActivity
import com.hipark.randomer.addedititem.AddEditItemFragment

class ItemDetailFragment : Fragment(), ItemDetailContract.View {

    private lateinit var detailTitle: TextView
    private lateinit var detailDescription: TextView
    private lateinit var detailDelete : Button

    override lateinit var presenter: ItemDetailContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_itemdetail, container, false)

        with(root) {

            detailTitle = findViewById(R.id.item_detail_title)
            detailDescription = findViewById(R.id.item_detail_description)
            detailDelete = findViewById(R.id.item_detail_delete)
            detailDelete.setOnClickListener {
                presenter.deleteItem()
            }
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_item)?.setOnClickListener {
            presenter.editItem()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showEditItem(itemId: String) {

        val intent = Intent(context, AddEditItemActivity::class.java)
        intent.putExtra(AddEditItemFragment.ARGUMENT_EDIT_ITEM_ID, itemId)
        startActivityForResult(intent, REQUEST_EDIT_ITEM)
    }

    override fun showMissingItem() {
        detailTitle.text = ""
        detailDescription.text = getString(R.string.no_data)
    }

    override fun showItemDeleted() {
        activity?.finish()
    }

    override fun hideTitle() {
        detailTitle.visibility = View.GONE
    }

    override fun hideDescription() {
        detailDescription.visibility = View.GONE
    }

    override fun showTitle(title: String) {
        with(detailTitle) {
            visibility = View.VISIBLE
            text = title
        }
    }

    override fun showDescription(description: String) {
        with(detailDescription) {
            detailDescription.visibility = View.VISIBLE
            text = description
        }
    }

    override fun setLoadingIndicator(activity: Boolean) {

        if(activity) {
            detailTitle.text = ""
            detailDescription.text = getString(R.string.loading)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_EDIT_ITEM) {
            if(resultCode == Activity.RESULT_OK) {
                activity?.finish()
            }
        }
    }

    companion object {

        private val ARGUMENT_ITEM_ID = "ITEM_ID"
        private val REQUEST_EDIT_ITEM = 1

        fun newInstance(itemId: String?) =
                ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_ITEM_ID, itemId)
                    }
                }
    }
}