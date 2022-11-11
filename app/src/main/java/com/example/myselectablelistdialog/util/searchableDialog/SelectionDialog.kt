package com.example.myselectablelistdialog.util.searchableDialog

import android.app.Activity
import android.widget.Filter
import androidx.appcompat.widget.SearchView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.myselectablelistdialog.R
import com.example.myselectablelistdialog.databinding.DialogSearchableListBinding
import com.example.myselectablelistdialog.model.NameData
import com.example.myselectablelistdialog.model.SearchableItem
import com.example.myselectablelistdialog.util.dialogUtil.DialogUtil
import com.example.myselectablelistdialog.util.rvAdapter.RVAdapter
import java.util.*
import kotlin.collections.ArrayList

class SelectionDialog<T>(
    private val activity: Activity,
    private val layoutId : Int
    ) {

    var rvlist = ArrayList<SearchableItem<T>>()
    var rvlistTemp = ArrayList<SearchableItem<T>>()
    var title = "Title"

    var listener : SelectionDialogListener<T>? = null
    var adapterListener : SelectionDialogAdapterListener<T>? = null

    private val dialogUtil = DialogUtil<DialogSearchableListBinding>(activity, R.layout.dialog_searchable_list).apply {
        height = (activity.resources.displayMetrics.heightPixels * 0.70).toInt()
        cancelable = true
    }
    private lateinit var adapter : RVAdapter<SearchableItem<T>>

    private lateinit var binding: DialogSearchableListBinding

    fun show(){
        rvlist = listener?.initList()!!
        dialogUtil.create()
        dialogUtil.show { binding, dialog ->
            this.binding = binding
            binding.tvTitle.text = title
            binding.ivClose.setOnClickListener {
                dialog.dismiss()
            }
            binding.tvSubmit.setOnClickListener {
                dialog.dismiss()
                listener?.onSubmit(rvlist)
            }
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })

            dialog.setOnDismissListener {
                listener?.onDismiss(rvlistTemp)
            }

            initRv(binding)
        }
    }

    private fun initRv(binding: DialogSearchableListBinding) {

        adapter = RVAdapter<SearchableItem<T>>(layoutId).apply {
            itemClickListener = object : RVAdapter.RecyclerViewOnItemClickListener{
                override fun onItemClick(id: Int, position: Int) {
                    adapterListener?.onItemClick(id, position, rvlist, this@apply)
                    if(rvlist[position].isSelected){
                        rvlistTemp.add(rvlist[position])
                    }
                }
            }
            itemFilter = object : RVAdapter.RecyclerViewFilterListener{
                override fun getFilter(): Filter = setFilter(this@apply, rvlist)
            }
        }
        binding.rvList.adapter = adapter
        adapter.replaceItems(rvlist)
    }

    private fun setFilter(
        lisAdapter: RVAdapter<SearchableItem<T>>,
        searchableItems: ArrayList<SearchableItem<T>>
    ): Filter {

        val allItems: MutableList<SearchableItem<T>> = searchableItems
        var selectedItems1: MutableList<SearchableItem<T>>

        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    selectedItems1 = allItems
                }else{
                    val tempList = java.util.ArrayList<SearchableItem<T>>()
                    for (row in allItems) {
                        if (row.text.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))
                        ) {
                            tempList.add(row)
                        }
                    }
                    selectedItems1 = tempList
                }
                val filterResults = FilterResults()
                filterResults.values = selectedItems1
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults) {

                selectedItems1 = filterResults.values as ArrayList<SearchableItem<T>>
                lisAdapter.replaceItems(selectedItems1)
            }

        }

    }

}