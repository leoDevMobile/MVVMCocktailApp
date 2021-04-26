package com.androiddevscocktail.cocktail.util


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import kotlinx.coroutines.launch
import retrofit2.Response

class DrinksViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val popularCocktails: MutableLiveData<Resource<Drinks>> = MutableLiveData()

    init {
        getPopularCocktails("drinks")
    }


    private fun getPopularCocktails(drinks: String) = viewModelScope.launch {
        popularCocktails.postValue(Resource.Loading())
        val response = drinksRepository.getPopularCocktails(drinks)
        popularCocktails.postValue(handlePopularCocktailsResponse(response))

    }

    private fun handlePopularCocktailsResponse(response: Response<Drinks>): Resource<Drinks> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}