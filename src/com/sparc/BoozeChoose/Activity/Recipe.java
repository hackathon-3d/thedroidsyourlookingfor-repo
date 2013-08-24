package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
        super.onCreate(savedInstanceState);

        View bgImage = findViewById(R.id.recipeBackground);
        Drawable background = bgImage.getBackground();
        background.setAlpha(80);

        TextView recipeText = (TextView) findViewById(R.id.recipeText);
        recipeText.setText("Hello World");
    }
}