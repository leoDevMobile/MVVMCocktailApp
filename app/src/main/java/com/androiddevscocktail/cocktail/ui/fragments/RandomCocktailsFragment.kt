package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.RandomDrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.androiddevscocktail.cocktail.util.Resource
import com.androiddevscocktail.customZoom.CenterZoomLayout
import kotlinx.android.synthetic.main.fragment_random_cocktails.*

class RandomCocktailsFragment : Fragment(R.layout.fragment_random_cocktails) {


    lateinit var viewModel: DrinksViewModel
    lateinit var randomDrinksAdapter: RandomDrinksAdapter
    val TAG = "RandomCocktailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
        setupRecyclerView()
        randomDrinksAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("drink", it)
            }


            findNavController().navigate(
                R.id.action_randomCocktailsFragment_to_cocktailsFragment, bundle

            )
        }


        viewModel.randomCocktails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { drinksResponse ->
                        randomDrinksAdapter.differ.submitList(drinksResponse.drinks)

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
        progressBarRandom.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBarRandom.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        randomDrinksAdapter = RandomDrinksAdapter()
        rvListRandom.apply {
            adapter = randomDrinksAdapter


            val layoutManager = CenterZoomLayout(requireContext())
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            rvListRandom.layoutManager = layoutManager

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvListRandom)
            rvListRandom.isNestedScrollingEnabled = false

        }

    }
}