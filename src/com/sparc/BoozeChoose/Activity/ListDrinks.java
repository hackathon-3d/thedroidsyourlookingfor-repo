package com.sparc.BoozeChoose.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import com.sparc.BoozeChoose.Adapter.DrinkAdapter;
import com.sparc.BoozeChoose.Adapter.IngredientAdapter;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 11:01 PM
 * Description:
 */
public class ListDrinks extends BoozeChoose {


    public Drink[] drinkData;
    public DrinkAdapter adapter;
    public ListView drinkListView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_list);

        Bundle extras = getIntent().getExtras();
        List<Drink> drinks = new ArrayList<Drink>(0);
        if (extras!=null) {
            for (int x=0; x<extras.size();x++) {
               drinks.add(x,(Drink) extras.getParcelable("drinks_"+x));
            }
        }

        // populate drinks

        if (!(drinks.size() < 1)) {

            drinkData = drinks.toArray(new Drink[drinks.size()]);

            adapter = new DrinkAdapter(this, ListDrinks.this, R.layout.drink_list_item, drinkData);

            drinkListView = (ListView) findViewById(R.id.drinkListView);

            drinkListView.setAdapter(adapter);

        }

        Button closeButton = (Button)findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ListDrinks.this,BoozeChoose.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                for (int x=0;x<BoozeChoose.myIngredients.size();x++) {
                    BoozeChoose.myIngredients.remove(x);
                }
                startActivity(i);
            }
        });
    }



}