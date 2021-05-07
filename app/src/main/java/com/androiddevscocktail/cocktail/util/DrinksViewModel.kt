package com.androiddevscocktail.cocktail.util


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks

import kotlinx.coroutines.launch
import retrofit2.Response

class DrinksViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val popularCocktails: MutableLiveData<Resource<Drinks>> = MutableLiveData()
    val randomCocktails: MutableLiveData<Resource<Drinks>> = MutableLiveData()
    val latestCocktails: MutableLiveData<Resource<Drinks>> = MutableLiveData()
    val searchCocktails: MutableLiveData<Resource<Drinks>> = MutableLiveData()


    init {
        getPopularCocktails("drinks")
        getRandomCocktails("drinks")
        getLatestCocktails("drinks")
        searchCocktails("drinks")
    }


    private fun getPopularCocktails(drinks: String) = viewModelScope.launch {
        popularCocktails.postValue(Resource.Loading())
        val response = drinksRepository.getPopularCocktails(drinks)
        popularCocktails.postValue(handlePopularCocktailsResponse(response))


    }

     fun searchCocktails(drinks: String) = viewModelScope.launch {
        searchCocktails.postValue(Resource.Loading())
        val response = drinksRepository.searchCocktails(drinks)
        searchCocktails.postValue(handleSearchCocktailsResponse(response))
    }

    private fun handlePopularCocktailsResponse(response: Response<Drinks>): Resource<Drinks> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }
        return Resource.Error(response.message())
    }


    private fun getRandomCocktails(drinks: String) = viewModelScope.launch {
        randomCocktails.postValue(Resource.Loading())
        val response = drinksRepository.getRandomCocktails(drinks)
        randomCocktails.postValue(handleRandomCocktailsResponse(response))
    }

    private fun handleRandomCocktailsResponse(response: Response<Drinks>): Resource<Drinks> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())


    }

    private fun getLatestCocktails(drinks: String) = viewModelScope.launch {
        latestCocktails.postValue(Resource.Loading())
        val response = drinksRepository.getLatestCocktails(drinks)
        latestCocktails.postValue(handleLatestCocktailsResponse(response))
    }

    private fun handleLatestCocktailsResponse(response: Response<Drinks>): Resource<Drinks> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())


    }

    private fun handleSearchCocktailsResponse(response: Response<Drinks>): Resource<Drinks> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())


    }

    fun saveDrink(drink: Drink) = viewModelScope.launch {
        drinksRepository.upsert(drink)
    }

    fun getSaveDrink() = drinksRepository.getSaveDrink()

    fun deleteDrink(drink: Drink) = viewModelScope.launch {
        drinksRepository.deleteDrink(drink)
    }
}