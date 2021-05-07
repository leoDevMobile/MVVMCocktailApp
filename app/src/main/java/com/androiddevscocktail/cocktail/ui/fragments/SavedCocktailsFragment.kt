package com.androiddevscocktail.cocktail.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevscocktail.cocktail.R
import com.androiddevscocktail.cocktail.adapters.SaveDrinksAdapter
import com.androiddevscocktail.cocktail.adapters.SearchDrinksAdapter
import com.androiddevscocktail.cocktail.ui.CocktailsActivity
import com.androiddevscocktail.cocktail.util.DrinksViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_cocktails.*
import java.util.*

class SavedCocktailsFragment : Fragment(R.layout.fragment_saved_cocktails) {

    lateinit var viewModel: DrinksViewModel
    lateinit var saveDrinksAdapter: SaveDrinksAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CocktailsActivity).viewModel
        setupRecyclerView()



        saveDrinksAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("drink", it)
            }

            findNavController().navigate(R.id.action_savedCocktailsFragment_to_cocktailsFragment, bundle)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val drink = saveDrinksAdapter.differ.currentList[position]
                viewModel.deleteDrink(drink)
                Snackbar.make(view, "Successfully delete", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveDrink(drink)
                    }.show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedDrinks)
        }

        viewModel.getSaveDrink().observe(viewLifecycleOwner, Observer { drinks ->
        saveDrinksAdapter.differ.submitList(drinks)
        })

    }

    private fun setupRecyclerView() {
            saveDrinksAdapter = SaveDrinksAdapter()
        rvSavedDrinks.apply {
            adapter = saveDrinksAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}