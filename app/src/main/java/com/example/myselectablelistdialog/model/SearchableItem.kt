package com.example.myselectablelistdialog.model

data class SearchableItem<T>(
    var text: String = "",
    var code: String = "",
    var modelClass: T,
    var isSelected: Boolean = false
) {
}