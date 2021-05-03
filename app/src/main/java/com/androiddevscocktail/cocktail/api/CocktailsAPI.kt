package com.androiddevscocktail.cocktail.api


import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drinks
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailsAPI {

    @GET("popular.php")
    suspend fun getPopularCocktails(
        @Query("drinks")
        StrDrink: String = "Drinks"
    ): Response<Drinks>

    @GET("randomselection.php")
    suspend fun getRandomCocktails(
        @Query("drinks")
        StrDrink: String = "Drinks"
    ): Response<Drinks>

    @GET("latest.php")
    suspend fun getLatestCocktails(
        @Query("drinks")
        StrDrink: String = "Drinks"
    ): Response<Drinks>

    @GET("search.php?s=")
    suspend fun getsearchCocktails(
        @Query("drinks")
        StrDrink: String = "Drinks"
    ): Response<Drinks>

}