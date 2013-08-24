package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.sparc.BoozeChoose.Model.Drink;
import com.sparc.BoozeChoose.Model.Ingredient;
import com.sparc.BoozeChoose.R;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * User: stephen quick
 * Date: 8/24/13
 * Time: 4:39 AM
 */
public class Recipe extends BoozeChoose
{
    private Facebook facebook = new Facebook("610775495621381");

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

        Button saveButton = (Button) findViewById(R.id.myFavButton);
        Button shareFbButton = (Button) findViewById(R.id.myShareFbButton);
        Button shareTwitterButton = (Button) findViewById(R.id.myShareFbButton);

        final Drink finalDrink = drink;
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                saveToFavs(finalDrink);

            }
        });
        shareFbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                shareToFacebook(finalDrink);

            }
        });
        shareTwitterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                shareToTwitter(finalDrink);

            }
        });


    }

    private void saveToFavs(Drink drink) {
        // add drink to database
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO my_drinks VALUES (null,\""+drink.getName()+"\",\""+drink.getIngredients()+"\")");
        Toast.makeText(Recipe.this, "Drink added to Favorites", Toast.LENGTH_SHORT).show();

    }

    private void shareToFacebook(Drink drink) {
        postToFB("is mixing up a " + drink.getName() + "!");
    }

    private void shareToTwitter(Drink drink) {
        String message = "I just mixed up a " + drink.getName() + "!";
        String tweetUrl = "https://twitter.com/intent/tweet?text="+message;
        Uri uri = Uri.parse(tweetUrl);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    public void postToFB(final String message){

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String token = prefs.getString("fbToken",null);

        if(token==null) {

            facebook.authorize(this,new String[] {"publish_stream"},new Facebook.DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    updateStatus(facebook.getAccessToken(),message);
                }

                @Override
                public void onFacebookError(FacebookError e) {
                    //To change body of implemented methods use File | Settings | File Templates.

                    String s = "";
                }

                @Override
                public void onError(DialogError e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                    String s = "";
                }

                @Override
                public void onCancel() {
                    String s = "";
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        } else {
            updateStatus(token,message);
        }
    }

    public void updateStatus(String accessToken, String message){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fbToken",accessToken);
        editor.commit();

        Bundle bundle = new Bundle();
        SecureRandom random = new SecureRandom();

        bundle.putString("message", message);
        bundle.putString(Facebook.TOKEN,accessToken);
        AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(facebook);
        mAsyncFbRunner.request("me/feed",bundle,"POST", new PostRequestListener(), null);
    }

        private final class PostRequestListener implements AsyncFacebookRunner.RequestListener {

        @Override
        public void onComplete(String response, Object state) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onIOException(IOException e, Object state) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onFileNotFoundException(FileNotFoundException e, Object state) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onMalformedURLException(MalformedURLException e, Object state) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onFacebookError(FacebookError e, Object state) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}