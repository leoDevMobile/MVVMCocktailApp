package com.androiddevscocktail.cocktail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevscocktail.cocktail.DrinksViewModelFactory
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.androiddevscocktail.cocktail.repository.database.model.DrinkDatabase
import com.androiddevscocktail.cocktail.util.DrinksViewModel

import kotlinx.android.synthetic.main.activity_cocktails.*

class CocktailsActivity : AppCompatActivity() {

    lateinit var viewModel: DrinksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails)

        val drinksRepository = DrinksRepository(DrinkDatabase(this))
        val viewModelFactory = DrinksViewModelFactory(drinksRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DrinksViewModel::class.java)
        bottomNavigationView.setupWithNavController(cocktailsNavHostFragment.findNavController())


    }

}

