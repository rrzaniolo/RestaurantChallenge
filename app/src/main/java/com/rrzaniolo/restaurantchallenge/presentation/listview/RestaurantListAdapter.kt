package com.rrzaniolo.restaurantchallenge.presentation.listview

import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rrzaniolo.movieapichallenge.presentation.base.BaseRecyclerAdapter
import com.rrzaniolo.restaurantchallenge.R
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantSortingOptions
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantStatus
import kotlinx.android.synthetic.main.item_view_restaurant.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/14/2019.
 * All rights reserved.
 */
class RestaurantListAdapter: BaseRecyclerAdapter<RestaurantEntity>(), Filterable {

    private var dataListComplete = mutableListOf<RestaurantEntity>()

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

    private var sortingOption = RestaurantSortingOptions.DEFAULT

    @Suppress("UNCHECKED_CAST")
    override fun setList(list: MutableList<RestaurantEntity>) {
        list.forEach {
            dataList.add(it.copy())
            dataListComplete.add(it.copy())
        }
        sortList(sortingOption)
    }

    fun setFavorite(position: Int){
        getItem(position)?.apply {
            Log.d("WTF", "dataList: " + this.isFavorite + " -> dataListComplete: " + dataListComplete[position].isFavorite)
            this.isFavorite = !this.isFavorite
            Log.d("WTF", "dataList: " + this.isFavorite + " -> dataListComplete: " + dataListComplete[position].isFavorite)
            dataListComplete
                .first { it.name == this.name }
                .apply { this.isFavorite = !this.isFavorite }
            Log.d("WTF", "dataList: " + this.isFavorite + " -> dataListComplete: " + dataListComplete[position].isFavorite)
            notifyItemChanged(position)
        }
        Log.d("WTF", "Between")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_restaurant, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = (holder as ViewHolder).bind(dataList[position], sortingOption)

    override fun getFilter(): Filter {
        return restaurantFilter
    }

    fun sortList(sortOption: RestaurantSortingOptions){
        sortingOption = sortOption
        val comparator = when(sortingOption) {
            RestaurantSortingOptions.AVERAGE_PRODUCT_PRICE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::averageProductPrice)
            RestaurantSortingOptions.BEST_MATCH -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::bestMatch)
            RestaurantSortingOptions.DELIVERY_COST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::deliveryCosts)
            RestaurantSortingOptions.DISTANCE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::distance)
            RestaurantSortingOptions.MIN_COST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::minCost)
            RestaurantSortingOptions.NEWEST -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::newest)
            RestaurantSortingOptions.POPULARITY -> compareBy({!it.isFavorite}, RestaurantEntity::status, {it.popularity})
            RestaurantSortingOptions.RATING_AVERAGE -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::ratingAverage)
            else -> compareBy({!it.isFavorite}, RestaurantEntity::status, RestaurantEntity::name)
        }
        dataList.sortWith(comparator)
        dataListComplete.sortWith(comparator)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(view) {

        fun bind(restaurant: RestaurantEntity, sortOption: RestaurantSortingOptions) = with(view) {
            val context = view.context
            restaurantName.text = restaurant.name
            restaurantStatus.text = RestaurantStatus.statusNameFromStatusCode(restaurant.status)
            restaurantFavorite.setColorFilter(
                ContextCompat.getColor(context, if(restaurant.isFavorite) R.color.floralWhite else R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN
            )

            val sortingOptions = context.resources.getStringArray(R.array.sorting_options)
            when(sortOption) {
                RestaurantSortingOptions.AVERAGE_PRODUCT_PRICE -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[1], restaurant.averageProductPrice.toString())
                RestaurantSortingOptions.BEST_MATCH -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[2], restaurant.bestMatch.toString())
                RestaurantSortingOptions.DELIVERY_COST -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[3], restaurant.deliveryCosts.toString())
                RestaurantSortingOptions.DISTANCE -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[4], restaurant.distance.toString())
                RestaurantSortingOptions.MIN_COST -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[5], restaurant.minCost.toString())
                RestaurantSortingOptions.NEWEST -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[6], restaurant.newest.toString())
                RestaurantSortingOptions.POPULARITY -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[7], restaurant.popularity.toString())
                RestaurantSortingOptions.RATING_AVERAGE -> restaurantSort.text = context.getString(R.string.sortingDisplay, sortingOptions[8], restaurant.ratingAverage.toString())
                else -> restaurantSort.text = ""
            }

            restaurantFavorite.setOnClickListener { onClickListener?.onItemClick(view, adapterPosition) }
        }
    }
}