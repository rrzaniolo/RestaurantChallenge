package com.rrzaniolo.restaurantchallenge.presentation.listview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.rrzaniolo.movieapichallenge.presentation.base.BaseRecyclerAdapter
import com.rrzaniolo.restaurantchallenge.R
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantStatus
import kotlinx.android.synthetic.main.item_view_restaurant.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/14/2019.
 * All rights reserved.
 */
class RestaurantListAdapter: BaseRecyclerAdapter<RestaurantEntity>(), Filterable {

    private var dataListComplete = ArrayList<RestaurantEntity>()

    private val restaurantFilter = object : Filter() {
        override fun performFiltering(query: CharSequence?): FilterResults {
            var dataListFiltered = ArrayList<RestaurantEntity>()

            if(query.isNullOrEmpty()) dataListFiltered.addAll(dataListComplete)
            else {
                dataListComplete.filter { restaurant ->
                    restaurant.name.toLowerCase(Locale.ROOT).contains(
                        query.toString().toLowerCase(Locale.ROOT).trim()
                    )
                }.toList().apply { dataListFiltered = ArrayList(this) }
            }

            return FilterResults().apply {
                this.values = dataListFiltered
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(query: CharSequence?, results: FilterResults?) {
            results?.apply {
                dataList.clear()
                dataList.addAll(this.values as ArrayList<RestaurantEntity>)
                notifyDataSetChanged()
            }
        }

    }

    override fun setList(list: ArrayList<RestaurantEntity>) {
        dataList = list
        dataListComplete = ArrayList(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_restaurant, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as ViewHolder).bind(dataList[position])

    override fun getFilter(): Filter {
        return restaurantFilter
    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(view) {

        fun bind(restaurant: RestaurantEntity) = with(view) {
            restaurantName.text = restaurant.name
            restaurantStatus.text = RestaurantStatus.statusNameFromStatusCode(restaurant.status)

            //TODO - Figure out a way to display the sorted property.

            restaurantFavorite.setOnClickListener { onClickListener?.onItemClick(view, adapterPosition) }
        }
    }
}