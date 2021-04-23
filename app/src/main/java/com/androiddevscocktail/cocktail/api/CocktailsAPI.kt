package com.androiddevscocktail.cocktail.api


import com.androiddevscocktail.cocktail.util.Drink
import retrofit2.Call
import retrofit2.http.GET


interface CocktailsAPI {

    @GET("popular.php")
    fun getPopularCocktails(drinks: String): Call<List<Drink>>

}