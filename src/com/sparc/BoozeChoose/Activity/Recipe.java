package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.R;

/**
 * User: stephen quick
 * Date: 8/24/13
 * Time: 4:39 AM
 */
public class Recipe extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        Bundle extras = getIntent().getExtras();
        Drink drink = null;
        if (extras != null) {
            drink = extras.getParcelable("drink");
        }

        TextView recipeName = (TextView) findViewById(R.id.recipeName);
        recipeName.setText(drink.getName());

        TextView recipeText = (TextView) findViewById(R.id.recipeText);
        recipeText.setText(drink.getIngredients());

    }
}