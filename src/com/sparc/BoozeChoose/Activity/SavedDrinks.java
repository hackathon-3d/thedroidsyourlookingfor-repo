package com.sparc.BoozeChoose.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import com.sparc.BoozeChoose.Adapter.DrinkAdapter;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/24/13
 * Time: 8:42 AM
 * Description:
 */
public class SavedDrinks extends BoozeChoose {

    public Drink[] drinkData;
    public DrinkAdapter adapter;
    public ListView drinkListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_drinks);

        // populate drinks

        List<Drink> drinks = getSavedDrinks();
        if (!(drinks.size() < 1)) {

            drinkData = drinks.toArray(new Drink[drinks.size()]);

            adapter = new DrinkAdapter(this, SavedDrinks.this, R.layout.drink_list_item, drinkData);

            drinkListView = (ListView) findViewById(R.id.drinkListView);

            drinkListView.setAdapter(adapter);
        } else {
            drinkData = drinks.toArray(new Drink[1]);

            adapter = new DrinkAdapter(this, SavedDrinks.this, R.layout.drink_empty_list_item, drinkData);

            drinkListView = (ListView) findViewById(R.id.drinkListView);

            drinkListView.setAdapter(adapter);
        }

    }

    public List<Drink> getSavedDrinks() {
        ArrayList<Drink> result = new ArrayList<Drink>();
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        Cursor myCursor = db.query("my_drinks", new String[] {"_id","name","ingredients"}, null, null, null, null, null);

        try{
            if (myCursor.moveToFirst()){
                do{
                    Drink drink = new Drink();
                    drink.setName(myCursor.getString((myCursor.getColumnIndex("name"))));
                    drink.setId(myCursor.getString((myCursor.getColumnIndex("_id"))));
                    drink.setIngredients(myCursor.getString((myCursor.getColumnIndex("ingredients"))));
                    result.add(drink);
                }while(myCursor.moveToNext());
            }
        }finally{
            myCursor.close();
        }
        db.close();
        return result;
    }
}
