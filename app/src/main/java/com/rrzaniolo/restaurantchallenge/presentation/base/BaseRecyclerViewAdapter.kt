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
    var dataList: ArrayList<T> = ArrayList()

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

    open fun setList(list: ArrayList<T>) {
        dataList = list
        notifyDataSetChanged()
    }

    fun addItems(items: ArrayList<T>) {
        val lastPos = dataList.size - 1
        dataList.addAll(items)
        notifyItemRangeInserted(lastPos, items.size)
    }

    fun getItem(position: Int): T {
        return getList()!![position]
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    abstract class BaseRecyclerItem
}