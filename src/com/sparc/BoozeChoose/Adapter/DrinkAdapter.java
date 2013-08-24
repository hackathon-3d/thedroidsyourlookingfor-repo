package com.sparc.BoozeChoose.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.sparc.BoozeChoose.Activity.BoozeChoose;
import com.sparc.BoozeChoose.Activity.ListIngredients;
import com.sparc.BoozeChoose.Activity.Recipe;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 9:40 PM
 * Description:
 */
public class DrinkAdapter extends ArrayAdapter<Drink> {

    Context context;
    int layoutResourceId;
    Drink data[] = null;
    Activity activity;

    public DrinkAdapter(Context context, Activity activity, int layoutResourceId, Drink[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DrinkHolder holder;
        View row = convertView;

        if ( row == null )
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new DrinkHolder();
            holder.name = (TextView) row.findViewById(R.id.drinkName);
            row.setTag(holder);


        } else {
            holder = (DrinkHolder)row.getTag();
        }

        final Drink drink = data[position];


        // if ingredients in list, populate data
        if (drink.getName() != null) {

            // set name
            holder.name.setText(drink.getName());

            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent i = new Intent(getContext(),Recipe.class);
                    i.putExtra("drink",new Drink(drink.getId(),drink.getName(),drink.getIngredients()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(i);

                }
            });


        } else {
            holder.name.setText("No drinks found..but FBGM-> Fuck Bitches; Get Money!!!!");
        }

        return row;
    }

    static class DrinkHolder {
        TextView _id;
        TextView name;
        TextView ingredients;
    }

}
