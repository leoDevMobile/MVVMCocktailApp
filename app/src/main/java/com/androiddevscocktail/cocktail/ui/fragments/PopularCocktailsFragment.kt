package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.DrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.androiddevscocktail.cocktail.util.Resource
import kotlinx.android.synthetic.main.fragment_popular_cocktails.*
import kotlinx.android.synthetic.main.fragment_search_cocktails.*

class PopularCocktailsFragment : Fragment(R.layout.fragment_popular_cocktails) {

    lateinit var viewModel: DrinksViewModel
    lateinit var drinksAdapter: DrinksAdapter

    val TAG = "PopularCocktailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
        setupRecyclerView()

        viewModel.popularCocktails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { drinksResponse ->
                        drinksAdapter.differ.submitList(drinksResponse.drinks)

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
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        drinksAdapter = DrinksAdapter()
        rvList.apply {
            adapter = drinksAdapter

            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

    }
}