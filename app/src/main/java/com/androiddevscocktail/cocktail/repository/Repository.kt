package com.androiddevscocktail.cocktail.repository

import com.androiddevscocktail.cocktail.api.RetrofitInstance
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import retrofit2.Call

class Repository {

    fun getPopularCocktails(drinks: String):Call<Drinks> {
        return RetrofitInstance.api.getPopularCocktails(drinks)
    }

}
