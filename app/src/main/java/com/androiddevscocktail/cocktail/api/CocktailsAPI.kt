package com.androiddevscocktail.cocktail.api

import com.androiddevscocktail.cocktail.util.Drink
import retrofit2.http.GET


interface CocktailsAPI {

    @GET("popular.php")
    suspend fun getPopularCocktails(): Drink

}