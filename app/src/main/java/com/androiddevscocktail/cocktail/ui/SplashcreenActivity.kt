package com.androiddevscocktail.cocktail.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddevscocktail.cocktail.R
import kotlinx.android.synthetic.main.activity_splashcreen.*

class SplashcreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashcreen)



        iv_cocktail.alpha = 1f
        iv_cocktail.animate().setDuration(3620).alpha(2f).withEndAction {
            val i = Intent(this, CocktailsActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }


    }
}