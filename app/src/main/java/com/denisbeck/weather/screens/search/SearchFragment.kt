package com.denisbeck.weather.screens.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.denisbeck.weather.R

class SearchFragment : Fragment() {

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }

//    private val viewModel by activityViewModels<SearchViewModel> {
//        SearchViewModelFactory(getWeatherRepository())
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.weather.observe(viewLifecycleOwner, Observer { statusWeatherActions(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.run {
                isIconified = false
                onActionViewExpanded()
                maxWidth = Int.MAX_VALUE

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        Log.d(TAG, "onQueryTextSubmit: $query")
//                        viewModel.updateLastLocation(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?) = true

                })
            }
            super.onCreateOptionsMenu(menu, inflater)
        }

    }

}