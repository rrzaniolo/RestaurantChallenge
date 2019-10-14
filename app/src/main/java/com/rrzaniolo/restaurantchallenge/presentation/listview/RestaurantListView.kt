package com.rrzaniolo.restaurantchallenge.presentation.listview

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rrzaniolo.movieapichallenge.presentation.base.BaseRecyclerAdapter
import com.rrzaniolo.movieapichallenge.presentation.base.BaseView
import com.rrzaniolo.movieapichallenge.presentation.base.isVisible
import com.rrzaniolo.movieapichallenge.presentation.base.showErrorDialog
import com.rrzaniolo.restaurantchallenge.di.modules.loadListViewModule
import com.rrzaniolo.restaurantchallenge.domain.entities.RestaurantEntity
import kotlinx.android.synthetic.main.view_restaurant_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Rodrigo Rodrigues Zaniolo on 10/14/2019.
 * All rights reserved.
 */
class RestaurantListView: BaseView(), BaseRecyclerAdapter.OnItemClickListener{
    private val viewModel: RestaurantListViewModel by viewModel()
    private val restaurantListAdapter by lazy { RestaurantListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.rrzaniolo.restaurantchallenge.R.layout.view_restaurant_list)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(com.rrzaniolo.restaurantchallenge.R.menu.restaurant_list_view_menu, menu)

        val searchItem = menu.findItem(com.rrzaniolo.restaurantchallenge.R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                restaurantListAdapter.filter.filter(newText)
                return false
            }
        })
        return true
    }

    private fun init() {
        loadListViewModule()
        setupToolbar()
        initViewModel()
        setUpRecyclerView()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = "Main View"
    }

    private fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL

        restaurantListAdapter.listener = this

        restaurantListViewRecycler.layoutManager = layoutManager
        restaurantListViewRecycler.itemAnimator = DefaultItemAnimator()
        restaurantListViewRecycler.adapter = restaurantListAdapter
    }

    private fun initViewModel(){
        viewModel.state.observe(this, Observer{state ->
            hideLoading()
            when(state){
                is RestaurantListViewState.ShowLoading -> showLoading()
                is RestaurantListViewState.showList -> showList(state.restaurants)
                is RestaurantListViewState.ShowError -> showError()
                is RestaurantListViewState.SaveSuccess -> showShortToast(getString(com.rrzaniolo.restaurantchallenge.R.string.restaurant_save_success))
                is RestaurantListViewState.SaveError -> showShortToast(getString(com.rrzaniolo.restaurantchallenge.R.string.restaurant_save_error))
                is RestaurantListViewState.DeleteSuccess -> showShortToast(getString(com.rrzaniolo.restaurantchallenge.R.string.restaurant_delete_success))
                is RestaurantListViewState.DeleteError -> showShortToast(getString(com.rrzaniolo.restaurantchallenge.R.string.restaurant_delete_error))
            }
        })
        viewModel.getRestaurants()
    }

    private fun showList(list: ArrayList<RestaurantEntity>){
        restaurantListViewRecycler.visibility = View.VISIBLE
        restaurantListAdapter.setList(list)
    }

    private fun showError(){
        showErrorDialog(this, { viewModel.getRestaurants() }, { /* Do Nothing. */ })
    }

    private fun showLoading(){
        restaurantListViewLoading.isVisible(true)
    }

    private fun hideLoading(){
        restaurantListViewLoading.isVisible(false)
    }

    override fun onItemClick(view: View, position: Int) {
       viewModel.saveOrRemoveRestaurant(restaurantListAdapter.getItem(position))
    }
}