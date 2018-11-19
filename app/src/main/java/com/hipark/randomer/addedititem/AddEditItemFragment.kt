package com.hipark.randomer.addedititem

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hipark.randomer.R
import com.hipark.randomer.util.showSnackBar

class AddEditItemFragment : Fragment(), AddEditItemConstract.View {

    override lateinit var presenter: AddEditItemConstract.Presenter

    private lateinit var title: TextView
    private lateinit var description: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_item_done)?.apply {
            setOnClickListener {
                presenter.saveItem(title.text.toString(), description.text.toString())
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_add_edit_item, container, false)
        with(root) {
            title = findViewById(R.id.add_item_title)
            description = findViewById(R.id.add_item_description)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showEmptyItemError() {
        title.showSnackBar(getString(R.string.empty_item_message), Snackbar.LENGTH_LONG)
    }

    override fun showItemsList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setDescription(description: String) {
        this.description.text = description
    }

    companion object {
        const val ARGUMENT_EDIT_ITEM_ID = "EDIT_ITEM_ID"

        fun newInstance(itemId: String?) =
                AddEditItemFragment().apply {
                    arguments = Bundle().apply {
                        putString(AddEditItemFragment.ARGUMENT_EDIT_ITEM_ID, itemId)
                    }
                }
    }
}