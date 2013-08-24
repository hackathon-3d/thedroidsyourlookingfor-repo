package com.sparc.BoozeChoose.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.*;
import android.widget.*;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 9:48 PM
 * Description:
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    Context context;
    int layoutResourceId;
    Ingredient data[] = null;

    public IngredientAdapter(Context context, int layoutResourceId, Ingredient[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final IngredientHolder holder;
        View row = convertView;

        if ( row == null )
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new IngredientHolder();
            holder.name = (TextView) row.findViewById(R.id.ingredientName);
            row.setTag(holder);


        } else {
            holder = (IngredientHolder)row.getTag();
        }

        final Ingredient ingredient = data[position];


        // if ingredients in list, populate data
        if (ingredient.getName() != null) {

            // set name
            holder.name.setText(ingredient.getName());



        } else {
            holder.name.setText("No ingredients found");
        }

        return row;
    }

    static class IngredientHolder {
        TextView _id;
        TextView name;
    }
}
