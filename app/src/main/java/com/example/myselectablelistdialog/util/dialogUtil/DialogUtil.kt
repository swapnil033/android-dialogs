package com.example.myselectablelistdialog.util.dialogUtil

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class DialogUtil<T>(
    private val activity: Activity,
    private val layoutId : Int
    ) {

    private lateinit var binding : ViewDataBinding
    private val dialog = Dialog(activity)
    var cancelable = false


    var width : Int = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
    var height : Int = ViewGroup.LayoutParams.WRAP_CONTENT

    fun create(){
        dialog.setCancelable(cancelable)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutId, null, false)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(width, height)
    }

    fun show(callback : ((binding: T, dialog: Dialog) -> Unit)){
        callback.invoke(binding as T, dialog)
        dialog.show()
    }

}