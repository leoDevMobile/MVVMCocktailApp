package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.DrinksAdapter
import com.androiddevscocktail.cocktail.adapters.LatestDrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.androiddevscocktail.cocktail.util.Resource
import com.androiddevscocktail.customZoom.CenterZoomLayout
import kotlinx.android.synthetic.main.fragment_popular_cocktails.*


class PopularCocktailsFragment : Fragment(R.layout.fragment_popular_cocktails) {

    lateinit var viewModel: DrinksViewModel
    lateinit var drinksAdapter: DrinksAdapter
    lateinit var latestDrinksAdapter: LatestDrinksAdapter

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
        latestDrinksAdapter = LatestDrinksAdapter()
        drinksAdapter = DrinksAdapter()
        rvList.apply {
            adapter = drinksAdapter

            val layoutManager =  CenterZoomLayout(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            rvList.layoutManager = layoutManager

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvList)
            rvList.isNestedScrollingEnabled = false
            //layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)


            viewModel.latestCocktails.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { drinksResponse ->
                            latestDrinksAdapter.differs.submitList(drinksResponse.drinks)

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

            rvLatestList.apply {
                adapter = latestDrinksAdapter

                val layoutManager =  CenterZoomLayout(requireContext())
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager.reverseLayout = true
                layoutManager.stackFromEnd = true
                rvLatestList.layoutManager = layoutManager

                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(rvLatestList)
                rvLatestList.isNestedScrollingEnabled = false

        }

    }
}

