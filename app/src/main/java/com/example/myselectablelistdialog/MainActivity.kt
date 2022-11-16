package com.example.myselectablelistdialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myselectablelistdialog.databinding.ActivityMainBinding
import com.example.myselectablelistdialog.databinding.DialogBinding
import com.example.myselectablelistdialog.model.NameData
import com.example.myselectablelistdialog.model.SearchableItem
import com.example.myselectablelistdialog.util.dialogUtil.DialogUtil
import com.example.myselectablelistdialog.util.rvAdapter.RVAdapter
import com.example.myselectablelistdialog.util.searchableDialog.SelectionDialog
import com.example.myselectablelistdialog.util.searchableDialog.SelectionDialogAdapterListener
import com.example.myselectablelistdialog.util.searchableDialog.SelectionDialogListener
import java.util.Collections


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var searchableItems = arrayListOf(
        SearchableItem(text = "Amit", modelClass = NameData(name = "Amit")),
        SearchableItem(text = "Amit1", modelClass = NameData(name = "Amit1")),
        SearchableItem(text = "Amit2", modelClass = NameData(name = "Amit2")),
        SearchableItem(text = "Amit3", modelClass = NameData(name = "Amit3")),
        SearchableItem(text = "Amit4", modelClass = NameData(name = "Amit4")),
        SearchableItem(text = "Amit", modelClass = NameData(name = "Amit")),
        SearchableItem(text = "Amit1", modelClass = NameData(name = "Amit1")),
        SearchableItem(text = "Amit2", modelClass = NameData(name = "Amit2")),
        SearchableItem(text = "Amit3", modelClass = NameData(name = "Amit3")),
        SearchableItem(text = "Amit4", modelClass = NameData(name = "Amit4")),
        SearchableItem(text = "Amit", modelClass = NameData(name = "Amit")),
        SearchableItem(text = "Amit1", modelClass = NameData(name = "Amit1")),
        SearchableItem(text = "Amit2", modelClass = NameData(name = "Amit2")),
        SearchableItem(text = "Amit3", modelClass = NameData(name = "Amit3")),
        SearchableItem(text = "Amit4", modelClass = NameData(name = "Amit4")),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.tvList2Text.setOnClickListener {
            showDialog2()
        }
    }


    private fun showDialog2() {
        val selectionDialog = SelectionDialog<NameData>(this, R.layout.row_search3_item).apply {
            title = "Select Names"
            listener = object : SelectionDialogListener<NameData>{
                override fun onDismiss(tempList : ArrayList<SearchableItem<NameData>>) {}
                override fun onSubmit(list: ArrayList<SearchableItem<NameData>>) = onNameSubmit(list)
                override fun initList(): ArrayList<SearchableItem<NameData>> = initDialogList()
            }
            adapterListener = object : SelectionDialogAdapterListener<NameData>{
                override fun onItemClick(
                    id: Int,
                    position: Int,
                    list: ArrayList<SearchableItem<NameData>>,
                    adapter: RVAdapter<SearchableItem<NameData>>
                ) = onNameItemClick(id, position, list, adapter)
            }
        }

        selectionDialog.show()
    }

    private fun initDialogList(): ArrayList<SearchableItem<NameData>> = searchableItems.map {
        it.copy(
            it.id, it.text, it.code,
            it.modelClass.copy(
                id = it.modelClass.id,
                name = it.modelClass.name
            )
        )
    } as ArrayList<SearchableItem<NameData>>

    private fun onNameItemClick(
        id: Int,
        position: Int,
        list: ArrayList<SearchableItem<NameData>>,
        adapter : RVAdapter<SearchableItem<NameData>>
    ){
        when(id){
            R.id.cl -> {
                list[position].isSelected = !list[position].isSelected
                searchableItems
                adapter.refreshItem(position)
            }
        }
    }

    private fun onNameSubmit(list: ArrayList<SearchableItem<NameData>>) {
        binding.tvList2Text.text = list
            .filter { it.isSelected }.joinToString { it.modelClass.name }
        searchableItems.clear()
        searchableItems.addAll(list)
    }

}