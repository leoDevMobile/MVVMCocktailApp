package com.androiddevscocktail.cocktail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.util.DrinksViewModel

class DrinksViewModelFactory(
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksViewModel(drinksRepository) as T
    }
}