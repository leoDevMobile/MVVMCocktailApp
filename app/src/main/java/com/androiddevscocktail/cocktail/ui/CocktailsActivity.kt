package com.androiddevscocktail.cocktail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevscocktail.cocktail.MainViewModel
import com.androiddevscocktail.cocktail.MainViewModelFactory
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.repository.Repository
import com.androiddevscocktail.cocktail.repository.database.model.Drink

import kotlinx.android.synthetic.main.activity_cocktails.*

class CocktailsActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var drink: Drink? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails)


        bottomNavigationView.setupWithNavController(cocktailsNavHostFragment.findNavController())



        val repository = Repository()
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        viewModel.getPopularCocktails("Mojito")
        viewModel.myResponse.observe(this, Observer { response ->
            Log.d("response", "${response.idDrink}")
            Log.d("response", "${response.strDrink}")

        })

    }
}
