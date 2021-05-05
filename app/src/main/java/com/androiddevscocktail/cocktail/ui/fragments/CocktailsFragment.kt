package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.DrinksAdapter
import com.androiddevscocktail.cocktail.adapters.ItemAdapter
import com.androiddevscocktail.cocktail.adapters.LatestDrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.androiddevscocktail.cocktail.util.Resource
import kotlinx.android.synthetic.main.fragment_cocktails.*
import kotlinx.android.synthetic.main.fragment_popular_cocktails.*
import kotlinx.android.synthetic.main.item_drink_information.view.*

class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {

    lateinit var viewModel: DrinksViewModel
    lateinit var itemAdapter: ItemAdapter
    val args: CocktailsFragmentArgs by navArgs()

    val TAG = "CocktailsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel



    }
}




