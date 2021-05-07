package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cocktails.*
import kotlinx.android.synthetic.main.fragment_cocktails.view.*
import kotlinx.android.synthetic.main.item_drink_preview.view.*


class CocktailsFragment : Fragment(R.layout.fragment_cocktails) {

    lateinit var viewModel: DrinksViewModel
    private val args: CocktailsFragmentArgs by navArgs()

    val TAG = "CocktailsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel

        val drink = args.drink

        val title = args.drink.strDrink
        val ingredient1 = args.drink.strIngredient1
        val ingredient2 = args.drink.strIngredient2
        val ingredient3 = args.drink.strIngredient3
        val ingredient4 = args.drink.strIngredient4
        val ingredient5 = args.drink.strIngredient5
        val ingredient6 = args.drink.strIngredient6
        val measure1 = args.drink.strMeasure1
        val measure2 = args.drink.strMeasure2
        val measure3 = args.drink.strMeasure3
        val measure4 = args.drink.strMeasure4
        val measure5 = args.drink.strMeasure5
        val measure6 = args.drink.strMeasure6
        val glass = args.drink.strGlass
        val instruction = args.drink.strInstructions
        scrollView.apply {
            Glide.with(this).load(args.drink.strDrinkThumb).into(image_item)
           view.Title_item.text = title
            view.Ingredient_1.text = ingredient1
            view.Ingredient_2.text = ingredient2
            view.Ingredient_3.text = ingredient3
            view.Ingredient_4.text = ingredient4
            view.Ingredient_5.text = ingredient5
            view.Ingredient_6.text = ingredient6
            view.Measure_1.text = measure1
            view.Measure_2.text = measure2
            view.Measure_3.text = measure3
            view.Measure_4.text = measure4
            view.Measure_5.text = measure5
            view.Measure_6.text = measure6
            view.glass.text = glass
            view.instruction.text = instruction



        }

        fab.setOnClickListener {
            viewModel.saveDrink(drink)
            Snackbar.make(view, "Cocktail saved successfully", Snackbar.LENGTH_LONG).show()
        }

        //Log.e(TAG, "onViewCrated ${args.drink}")



    }
}




