package com.sparc.BoozeChoose.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 9:37 PM
 * Description:
 */
public class Drink implements Parcelable {

    private String id;
    private String name;
    private String ingredients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Drink(String id, String name, String ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Drink() {

    }

    public Drink(Parcel in) {
        String[] data = new String[3];

        in.readStringArray(data);
        this.id = data[0];
        this.name = data[1];
        this.ingredients = data[2];
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id,this.name,this.ingredients});
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };
}
