package com.rrzaniolo.movieapichallenge.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/12/2019.
 * All rights reserved.
 */
abstract class BaseRecyclerAdapter<T : BaseRecyclerAdapter.BaseRecyclerItem>:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: OnItemClickListener? = null
    var dataList: MutableList<T> = mutableListOf()

    fun getList(): List<T>? {
        return dataList
    }

    @Synchronized
    fun clear() {
        val size = dataList.size
        if (size > 0) {
            dataList.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    open fun setList(list: MutableList<T>) {
        dataList = list
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<T>) {
        val lastPos = dataList.size - 1
        dataList.addAll(items)
        notifyItemRangeInserted(lastPos, items.size)
    }

    fun getItem(position: Int): T? {
        return getList()?.let{
            it[position]
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    abstract class BaseRecyclerItem
}