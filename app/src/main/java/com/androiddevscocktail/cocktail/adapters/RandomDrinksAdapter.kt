package com.androiddevscocktail.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.databinding.ItemDrinkRandomPreviewBinding
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_drink_random_preview.view.*


class RandomDrinksAdapter : RecyclerView.Adapter<RandomDrinksAdapter.DrinkRandomViewHolder>() {

    inner class DrinkRandomViewHolder(private val binding: ItemDrinkRandomPreviewBinding): RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkRandomViewHolder {
        return DrinkRandomViewHolder(
            ItemDrinkRandomPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false


            )

        )

    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DrinkRandomViewHolder, position: Int) {
        val drink = differ.currentList[position]
        holder.itemView.tour_image.resume()
        holder.itemView.apply {
            Glide.with(this).load(drink.strDrinkThumb).into(ivDrinkRandomImage)
            TvTitleRandom.text = drink.strDrink
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