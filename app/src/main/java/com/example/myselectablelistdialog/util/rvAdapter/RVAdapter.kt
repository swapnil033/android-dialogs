package com.example.myselectablelistdialog.util.rvAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class  RVAdapter<T>(private val layoutId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    var itemClickListener: RecyclerViewOnItemClickListener? = null
    var itemRefreshListener: RecyclerViewOnItemRefreshListener? = null
    var itemOnBindListener: RecyclerViewOnBindViewHolderListener? = null
    var itemFilter: RecyclerViewFilterListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(listItems: MutableList<T>) {
        items.clear()
        items.addAll(listItems)
        notifyDataSetChanged()
    }

    fun getRvList() : MutableList<T> = items


    fun addItem(listItem : T){
        items.add(listItem)
        refreshItemInsert()
    }

    fun refreshItemInsert() {
        notifyItemInserted(items.size - 1)
    }

    fun replaceItem(listItem : T ,position: Int){
        items[position] = listItem
        refreshItem(position)
    }

    fun refreshItem(position: Int){
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            else -> {
                RVHolder(
                    DataBindingUtil.inflate<ViewDataBinding>(
                        inflater!!,
                        layoutId,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RVHolder) {
            holder.binding.setVariable(BR.viewModel, items[position])
            holder.binding.setVariable(BR.adapterPosition, position)
            holder.binding.setVariable(BR.clickHandler, itemClickListener)
            //holder.binding.setVariable(BR.changeHandler, itemRefreshListener)
            //holder.binding.setVariable(BR.totalItems, itemCount)

            itemOnBindListener?.onBindViewHolder(holder, position)
            holder.binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return/* if (items[position] is MarketPlaceBrandEntity) {
            (items[position] as MarketPlaceBrandEntity).RVType
        } else*/ if (items[position] is RVAdapterItem) {
            (items[position] as RVAdapterItem).getViewType()
        } else 0
    }

    class RVHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    class RVLoaderViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
    class RVHeaderViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    interface RecyclerViewOnItemClickListener {
        fun onItemClick(id: Int, position: Int)
    }
    interface RecyclerViewOnItemRefreshListener {
        fun onItemRefresh(position: Int)
    }

    interface RecyclerViewOnBindViewHolderListener {
        fun onBindViewHolder(holder: RVHolder, position: Int)
    }

    interface RecyclerViewFilterListener {
        fun getFilter() : Filter
    }

    companion object {
        const val LOADER_TYPE = 420
        const val HEADER = 120
    }

    override fun getFilter(): Filter {
        return itemFilter!!.getFilter()
    }
}