package com.androiddevscocktail.cocktail.api


import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailsAPI {

    @GET("popular.php")
    fun getPopularCocktails(
        @Query("drinks")
        StrDrink: String = "Drinks"
    ): Call<Drinks>

}