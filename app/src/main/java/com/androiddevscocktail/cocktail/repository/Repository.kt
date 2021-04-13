package com.androiddevscocktail.cocktail.repository

import com.androiddevscocktail.cocktail.api.RetrofitInstance
import com.androiddevscocktail.cocktail.util.Drink

class Repository {

    suspend fun getPopularCocktails(): Drink {
        return RetrofitInstance.api.getPopularCocktails()
    }
}