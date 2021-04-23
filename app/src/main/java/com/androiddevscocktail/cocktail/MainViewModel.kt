package com.androiddevscocktail.cocktail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevscocktail.cocktail.util.Drink
import com.androiddevscocktail.cocktail.repository.Repository
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
            response.enqueue(object : Callback<List<Drink>> {
                override fun onResponse(call: Call<List<Drink>>, response: Response<List<Drink>>) {
                    val allDrink = response.body()
                    Log.d("response", "onResponse:")
                    if (response.code() == 200) {
                        Log.d("response", "true: ${allDrink}")
                    } else if (response.code() == 404) {
                        Log.d("response", "onResponse: 404")
                    }
                }

                override fun onFailure(call: Call<List<Drink>>, t: Throwable) {
                    Log.d("response", "onFailure: null values")
                }
            })

        }
    }
}