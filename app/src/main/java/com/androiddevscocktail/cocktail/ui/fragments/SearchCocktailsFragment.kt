package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.DrinksAdapter
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
    val TAG = "SearchCocktailsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
        setupRecyclerView()
        searchDrinksAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("drink", it)
            }

            findNavController().navigate(
                R.id.action_searchCocktailsFragment_to_cocktailsFragment, bundle

            )
        }

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_COCKTAIL_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
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
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_SHORT).show()

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
