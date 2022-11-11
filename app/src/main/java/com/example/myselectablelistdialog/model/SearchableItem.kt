package com.example.myselectablelistdialog.model

import javax.security.auth.callback.Callback

data class SearchableItem<T>(
    var id: Int = 0,
    var text: String = "",
    var code: String = "",
    var modelClass: T,
    var isSelected: Boolean = false
)