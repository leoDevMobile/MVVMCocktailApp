package com.androiddevscocktail.cocktail.util


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.CocktailsApplication
import com.androiddevscocktail.cocktail.repository.DrinksRepository
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks

import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import java.time.format.ResolverStyle

class DrinksViewModel(
    app: Application,
    private val drinksRepository: DrinksRepository
) : AndroidViewModel(app) {

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
            safeCoktailCall(drinks)


    }

     fun searchCocktails(drinks: String) = viewModelScope.launch {
       safesearchCoktailCall(drinks)
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
        safeRandomCoktailCall(drinks)
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
        safelatestCoktailCall(drinks)
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

    private suspend fun safelatestCoktailCall(drinks: String) {
        latestCocktails.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = drinksRepository.getLatestCocktails(drinks)
                latestCocktails.postValue(handleLatestCocktailsResponse(response))
            } else {
                latestCocktails.postValue(Resource.Error("No internet Connection"))
            }

        } catch (t: Throwable) {
            when(t) {
                is IOException -> latestCocktails.postValue(Resource.Error("Network Failure"))
                else -> latestCocktails.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private suspend fun safesearchCoktailCall(drinks: String) {
        searchCocktails.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = drinksRepository.searchCocktails(drinks)
                searchCocktails.postValue(handleSearchCocktailsResponse(response))
            } else {
                searchCocktails.postValue(Resource.Error("No internet Connection"))
            }

        } catch (t: Throwable) {
            when(t) {
                is IOException -> searchCocktails.postValue(Resource.Error("Network Failure"))
                else -> searchCocktails.postValue(Resource.Error("Conversion Error"))
            }
        }

    }


    private suspend fun safeRandomCoktailCall(drinks: String) {
        randomCocktails.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = drinksRepository.getRandomCocktails(drinks)
                randomCocktails.postValue(handleRandomCocktailsResponse(response))
            } else {
                randomCocktails.postValue(Resource.Error("No internet Connection"))
            }

        } catch (t: Throwable) {
            when(t) {
                is IOException -> randomCocktails.postValue(Resource.Error("Network Failure"))
                else -> randomCocktails.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private suspend fun safeCoktailCall(drinks: String) {
        popularCocktails.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = drinksRepository.getPopularCocktails(drinks)
                popularCocktails.postValue(handlePopularCocktailsResponse(response))
            } else {
                popularCocktails.postValue(Resource.Error("No internet Connection"))
            }

        } catch (t: Throwable) {
                when(t) {
                    is IOException -> popularCocktails.postValue(Resource.Error("Network Failure"))
                    else -> popularCocktails.postValue(Resource.Error("Conversion Error"))
                }
        }

    }

    private fun hasInternetConnection() : Boolean {

    val connectivityManager = getApplication<CocktailsApplication>().getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}