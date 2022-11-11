package com.example.myselectablelistdialog.util.searchableDialog

import com.example.myselectablelistdialog.model.SearchableItem

interface SelectionDialogListener<T> {
    fun initList() : ArrayList<SearchableItem<T>>
    fun onSubmit(list : ArrayList<SearchableItem<T>>)
    fun onDismiss(tempList : ArrayList<SearchableItem<T>>)
}