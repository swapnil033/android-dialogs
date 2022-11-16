package com.example.myselectablelistdialog

import android.app.Dialog
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment


abstract class BaseDialogFragment() : DialogFragment() {

    private lateinit var binding : ViewDataBinding
    var width : Int = 0
    var cancelable1 = false
    var height : Int = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        width = (resources.displayMetrics.widthPixels * 0.90).toInt()

        dialog.setCancelable(cancelable1)
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), getRootLayout(), null, false)
        init(binding)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(width, height)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view, savedInstanceState)
    }

    abstract fun getRootLayout(): Int
    abstract fun init(binding: ViewDataBinding)

    abstract fun viewCreated(view: View, savedInstanceState: Bundle?)

}