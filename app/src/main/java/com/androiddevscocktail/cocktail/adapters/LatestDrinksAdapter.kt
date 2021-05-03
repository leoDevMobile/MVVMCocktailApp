package com.androiddevscocktail.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.databinding.ItemDrinkPreviewBinding
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_drink_preview.view.*


class LatestDrinksAdapter : RecyclerView.Adapter<LatestDrinksAdapter.LatestDrinkViewHolder>() {

    inner class LatestDrinkViewHolder(private val binding: ItemDrinkPreviewBinding): RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differs = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestDrinkViewHolder {
        return LatestDrinkViewHolder(
            ItemDrinkPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )
    }


    override fun getItemCount(): Int {
        return differs.currentList.size
    }

    override fun onBindViewHolder(holder: LatestDrinkViewHolder, position: Int) {
        val drink = differs.currentList[position]
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