package com.androiddevscocktail.cocktail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_drink_preview.view.*

class DrinksAdapter : RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    inner class DrinkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_popular_cocktails,
                parent,
                false

            )
        )
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(drink.strDrinkThumb).into(ivDrinkImage)
            TvTitle.text = drink.strDrink
            setOnClickListener {
              onItemClickListener?.let { it(drink) }
            }

        }
    }

    private var onItemClickListener: ((Drink)  -> Unit)? = null

    fun setOnItemClickListener(listener: (Drink) -> Unit) {
        onItemClickListener = listener
    }
}