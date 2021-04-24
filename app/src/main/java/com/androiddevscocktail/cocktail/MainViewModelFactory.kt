package com.androiddevscocktail.cocktail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevscocktail.cocktail.repository.Repository
import com.androiddevscocktail.cocktail.util.DrinksViewModel

class MainViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksViewModel(repository) as T
    }
}