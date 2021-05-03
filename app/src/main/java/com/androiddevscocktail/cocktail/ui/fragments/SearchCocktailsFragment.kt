package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.SearchDrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.Constants.Companion.SEARCH_COCKTAIL_DELAY
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.androiddevscocktail.cocktail.util.Resource
import kotlinx.android.synthetic.main.fragment_search_cocktails.*
import kotlinx.android.synthetic.main.item_search_drink_preview.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchCocktailsFragment : Fragment(R.layout.fragment_search_cocktails) {

    lateinit var viewModel: DrinksViewModel
    lateinit var searchDrinksAdapter: SearchDrinksAdapter
    val TAG = "SearchCocktailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
        setupRecyclerView()

        var job : Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_COCKTAIL_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchCocktails(editable.toString())
                    }
                }
            }

        }



        viewModel.searchCocktails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { drinksResponse ->
                        searchDrinksAdapter.differ.submitList(drinksResponse.drinks)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error Occured: $message")

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
    })


    }

    private fun hideProgressBar() {
        paginationProgressBar2.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar2.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        searchDrinksAdapter = SearchDrinksAdapter()
        rvSearchCocktails.apply {
            adapter = searchDrinksAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
