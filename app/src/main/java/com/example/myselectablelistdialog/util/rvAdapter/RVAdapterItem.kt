package com.example.myselectablelistdialog.util.rvAdapter

interface RVAdapterItem {
    fun getViewType(): Int
    fun getHeaderText(): String?
}