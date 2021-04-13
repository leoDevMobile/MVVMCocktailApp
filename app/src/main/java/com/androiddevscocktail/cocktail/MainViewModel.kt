package com.androiddevscocktail.cocktail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.cocktail.util.Drink
import com.androiddevscocktail.cocktail.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Drink> = MutableLiveData()

    fun getPopularCocktails() {
        viewModelScope.launch {
            val response = repository.getPopularCocktails()
            myResponse.value = response
        }
    }
}