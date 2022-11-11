package com.example.myselectablelistdialog.util.searchableDialog

import com.example.myselectablelistdialog.model.SearchableItem
import com.example.myselectablelistdialog.util.rvAdapter.RVAdapter

interface SelectionDialogAdapterListener<T> {
    fun onItemClick(id: Int, position: Int ,list : ArrayList<SearchableItem<T>>, adapter : RVAdapter<SearchableItem<T>>)
}