package com.androiddevscocktail.cocktail.repository

import com.androiddevscocktail.cocktail.api.RetrofitInstance
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import com.androiddevscocktail.cocktail.repository.database.model.DrinkDatabase
import retrofit2.Call

class DrinksRepository(val database: DrinkDatabase
) {
    suspend fun getPopularCocktails(drinks: String) =
        RetrofitInstance.api.getPopularCocktails(drinks)


    suspend fun getRandomCocktails(drinks: String) =
        RetrofitInstance.api.getRandomCocktails(drinks)

    suspend fun getLatestCocktails(drinks: String) =
        RetrofitInstance.api.getLatestCocktails(drinks)

    suspend fun getSearchCocktails(drinks: String)  =
        RetrofitInstance.api.getsearchCocktails(drinks)

}