package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel

class SearchCocktailsFragment : Fragment(R.layout.fragment_search_cocktails)  {

    lateinit var viewModel: DrinksViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
    }
}