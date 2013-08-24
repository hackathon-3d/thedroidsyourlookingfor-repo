package com.sparc.BoozeChoose.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.*;
import android.widget.*;
import com.sparc.BoozeChoose.Activity.BoozeChoose;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;

import java.util.List;

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
    Activity activity;

    public IngredientAdapter(Context context, Activity activity, int layoutResourceId, Ingredient[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.activity = activity;
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
        if (ingredient != null) {

            // set name
            holder.name.setText(ingredient.getName());

            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    List<Ingredient> ingredients =  BoozeChoose.myIngredients;
                    if(!ingredients.contains(ingredient)){
                        BoozeChoose.myIngredients.add(ingredient);
                    }
                    if (getContext().getClass().getName().equals("com.sparc.BoozeChoose.Activity.ListIngredients")) {
                        activity.finish();
                    }
                    else{
                        for (Ingredient i : ingredients)
                        {
                            if (ingredient.get_id().equals(i.get_id()))
                            {
                                BoozeChoose.myIngredients.remove(i);
                                break;
                            }
                        }
                    }
                }
            });


        } else {
            holder.name.setText("You have not added any ingredients!");
        }


        return row;
    }

    static class IngredientHolder {
        TextView _id;
        TextView name;
    }
}
