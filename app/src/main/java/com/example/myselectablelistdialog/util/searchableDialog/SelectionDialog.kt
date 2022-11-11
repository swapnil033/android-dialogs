package com.example.myselectablelistdialog.util.searchableDialog

import android.app.Activity
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

    val dialogUtil = DialogUtil<DialogSearchableListBinding>(activity, R.layout.dialog_searchable_list).apply {
        height = (activity.resources.displayMetrics.widthPixels * 0.90).toInt()
        cancelable = true
    }

    fun initRvList(){
        rvlistTemp.clear()

    }

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
            dialog.setOnDismissListener {
                listener?.onDismiss(rvlistTemp)
            }

            initRv(binding)
        }
    }

    private fun initRv(binding: DialogSearchableListBinding) {

        val adapter = RVAdapter<SearchableItem<T>>(layoutId).apply {
            itemClickListener = object : RVAdapter.RecyclerViewOnItemClickListener{
                override fun onItemClick(id: Int, position: Int) {
                    adapterListener?.onItemClick(id, position, rvlist, this@apply)
                    if(rvlist[position].isSelected){
                        rvlistTemp.add(rvlist[position])
                    }
                }
            }
        }

        binding.rvList.adapter = adapter

        adapter.replaceItems(rvlist)
    }

}