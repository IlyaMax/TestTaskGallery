package com.example.testtask.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.models.ImageItem


abstract class BaseRecyclerViewAdapter<T, VH : BaseViewHolder<T>>() :
    RecyclerView.Adapter<VH>() {

    protected var items = ArrayList<T>()

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.onBind(position, item)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<T>) {
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }

    fun changeItem(position: Int, item: T) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    fun getItemByIndex(position: Int) = this.items[position]

}