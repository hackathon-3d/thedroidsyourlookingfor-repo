package com.sparc.BoozeChoose.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.sparc.BoozeChoose.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Whitney Champion
 * Date: 8/23/13
 * Time: 10:12 PM
 * Description:
 */
public class Splash extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /** set time to splash out */
        final int welcomeScreenDisplay = 3000;
        printHashKey();
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    /**
                     * use while to get the splash time. Use sleep() to increase
                     * the wait variable for every 100L.
                     */
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    /**
                     * Called after splash times up. Do some action after splash
                     * times up. Here we moved to another main activity class
                     */
                    startActivity(new Intent(Splash.this,
                            BoozeChoose.class));
                    finish();
                }
            }
        };
        welcomeThread.start();



    }

    private void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.sparc.BoozeChoose",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String fuck = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                System.out.println(fuck);
                Log.e("fuck", fuck);
                Log.i("TEMPTAGHASH KEY:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("fuck", "Could not find package with name com.sparc.BoozeChoose.", e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("fuck", "Could find algorithm SHA.", e);
        }
    }
}