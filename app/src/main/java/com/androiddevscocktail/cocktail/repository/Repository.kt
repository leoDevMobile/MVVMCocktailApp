package com.androiddevscocktail.cocktail.repository

import com.androiddevscocktail.cocktail.api.RetrofitInstance
import com.androiddevscocktail.cocktail.util.Drink
import retrofit2.Call

class Repository {

    fun getPopularCocktails(drinks: String): Call<List<Drink>> {
        return RetrofitInstance.api.getPopularCocktails(drinks)
    }

}
