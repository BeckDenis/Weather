package com.denisbeck.weather.screens.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.denisbeck.weather.R
import com.denisbeck.weather.persistence.Preferences
import com.denisbeck.weather.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {
    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }

    private val preferences: Preferences by inject()
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var adapter: LocationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LocationsAdapter(
            selectClickListener = {
                preferences.storeSelectedLocation(it)
                search_auto_locate.isChecked = false
                requireActivity().onBackPressed()
            },
            deleteClickListener = {
                preferences.deleteSavedLocations(it.name)
                viewModel.deleteWeather(it)
            })

        search_back.setOnClickListener { requireActivity().onBackPressed() }
        search_search.setOnClickListener { search() }
        search_recycler.adapter = adapter
        search_auto_locate.run {
            isChecked = preferences.getAutoLocate()
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    preferences.storeAutoLocate(true)
                } else {
                    preferences.storeAutoLocate(false)
                }
            }
        }

        preferences.getSavedLocations()?.forEach {
            getWeather(it)
        }

        viewModel.weather.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "weather: $it ")
            adapter.submitList(it.toList())
            adapter.notifyDataSetChanged()
        })

    }

    private fun search() {
        getWeather(search_edit_text.text.toString())
    }

    private fun getWeather(location: String) {
        viewModel.getWeather(location).observe(viewLifecycleOwner, Observer { resource ->
            resource.data?.let { data ->
                viewModel.updateWeather(data)
                preferences.storeSavedLocations(data.name)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard(requireActivity())
    }

}