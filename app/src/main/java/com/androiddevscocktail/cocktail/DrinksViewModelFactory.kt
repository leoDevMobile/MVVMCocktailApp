package com.androiddevscocktail.cocktail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.util.DrinksViewModel

class DrinksViewModelFactory(
    val app: Application,
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksViewModel(app, drinksRepository) as T
    }
}