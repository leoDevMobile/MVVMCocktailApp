package com.androiddevscocktail.cocktail.repository.database.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink


@Dao
interface DrinkDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(drink: Drink) : Long

    @Query("SELECT * FROM drinks")
    fun getAllDrinks(): LiveData<List<Drink>>

    @Delete
    suspend fun  deleteDrink(drink: Drink)
}