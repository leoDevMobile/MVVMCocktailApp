package com.androiddevscocktail.cocktail.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.databinding.ItemSearchDrinkPreviewBinding
import com.androiddevscocktail.cocktail.repository.RemoteDataSource.Drink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_search_drink_preview.view.*


class SaveDrinksAdapter : RecyclerView.Adapter<SaveDrinksAdapter.SearchDrinkViewHolder>() {

    inner class SearchDrinkViewHolder(private val binding: ItemSearchDrinkPreviewBinding): RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDrinkViewHolder {
        return SearchDrinkViewHolder(
            ItemSearchDrinkPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchDrinkViewHolder, position: Int) {
        val drink = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(drink.strDrinkThumb).into(ivCocktailImage)
            tvTitleSearch.text = drink.strDrink
            tvDescription.text = drink.strCategory
            tvalcoolic.text = drink.strAlcoholic
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