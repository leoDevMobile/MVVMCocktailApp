package com.androiddevscocktail.cocktail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.cocktail.repository.Repository
import com.androiddevscocktail.cocktail.repository.database.model.Drink
import com.androiddevscocktail.cocktail.repository.database.model.Drinks
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Drink> = MutableLiveData()


    fun getPopularCocktails(search: String) {
        viewModelScope.launch {
            val response = repository.getPopularCocktails(search)
//            myResponse.value =
            response.enqueue(object : Callback<Drinks> {
                override fun onResponse(call: Call<Drinks>, response: Response<Drinks>) {
                    val allDrink = response.body()
                    if (response.code() == 200) {
                        Log.d("response", "true: ${allDrink}")
                    } else if (response.code() == 404) {
                        Log.d("response", "onResponse: 404")
                    }
                }

                override fun onFailure(call: Call<Drinks>, t: Throwable) {
                    Log.d("response", "onFailure: null values")
                }
            })

        }
    }
}