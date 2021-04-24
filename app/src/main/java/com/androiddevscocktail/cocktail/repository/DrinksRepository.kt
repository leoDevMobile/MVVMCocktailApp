package com.androiddevscocktail.cocktail.repository

import com.androiddevscocktail.cocktail.api.RetrofitInstance
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import com.androiddevscocktail.cocktail.repository.database.model.DrinkDatabase
import retrofit2.Call

class DrinksRepository(
    val db: DrinkDatabase
) {
    suspend fun getPopularCocktails(drinks: String) =
        RetrofitInstance.api.getPopularCocktails(drinks)
}