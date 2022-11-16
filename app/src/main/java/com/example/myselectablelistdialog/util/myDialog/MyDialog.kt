package com.example.myselectablelistdialog.util.myDialog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.myselectablelistdialog.BaseDialogFragment
import com.example.myselectablelistdialog.R
import com.example.myselectablelistdialog.databinding.DialogFragmentBinding

class MyDialog(private val layoutId : Int) : BaseDialogFragment() {

    private lateinit var binding : DialogFragmentBinding

    override fun getRootLayout(): Int = layoutId

    override fun init(binding: ViewDataBinding) {
        this.binding = binding as DialogFragmentBinding
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {

        binding.yes.setOnClickListener {
            Log.e("yes", "Clicked Yes");
        }

        binding.no.setOnClickListener {
            Log.e("no", "Clicked No");
        }
    }

}