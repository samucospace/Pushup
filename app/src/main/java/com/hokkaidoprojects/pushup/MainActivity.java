package com.hokkaidoprojects.pushup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final String TAG = "Saving error";
    final String KEY = "current_progress";
    private int cachedLevel;
    private int cachedWeek;
    private int cachedDay;
    private int cachedRound;
    private int cachedPushups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        TextView levelNumber = (TextView)findViewById(R.id.levelNumber);
        TextView weekNumber = (TextView)findViewById(R.id.weekNumber);
        TextView dayNumber = (TextView)findViewById(R.id.dayNumber);
        TextView roundNumber = (TextView)findViewById(R.id.roundNumber);
        TextView pushupNumber = (TextView)findViewById(R.id.pushupNumber);
        Button finishedButton = (Button) findViewById(R.id.finishedButton);

        //load the array of set data and store in setsArray
        ArrayList<Set> setsArray = loadSetsXml();

        //store the size of the array in a variable
        int sizeInt = setsArray.size();

        //set click listener for the button when a user has finished that set
        finishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishedButton();
            }
        });


        try {
            // Retrieve the list from internal storage
            Set cachedProgress = (Set) InternalStorage.readObject(getApplicationContext(), KEY);

            //store the saved values in variables
            cachedLevel = cachedProgress.getLevel();
            cachedWeek = cachedProgress.getWeek();
            cachedDay = cachedProgress.getDay();
            cachedRound = cachedProgress.getRound();
            cachedPushups = cachedProgress.getPushups();

            int index = 0;
            while (index < sizeInt) {

                Set currentSet = setsArray.get(index);
                int level = currentSet.getLevel();
                int week = currentSet.getWeek();
                int day = currentSet.getDay();
                int round = currentSet.getRound();

                //System.out.println("i"+index+" l"+level+" w"+week+" d"+day+" r"+round);


                //find the set in the array that corresponds with the cached set and return pushups
                if (level==cachedLevel && week==cachedWeek && day==cachedDay && round==cachedRound) {
                    //System.out.println("the answer is "+currentSet.getPushups());
                    cachedPushups = currentSet.getPushups();

                }
                else {

                }

                index++;
            }

            //convert the cached values to strings for display
            String levelAsString = Integer.toString(cachedLevel);
            String weekAsString = Integer.toString(cachedWeek);
            String dayAsString = Integer.toString(cachedDay);
            String roundAsString = Integer.toString(cachedRound);
            String pushupAsString = Integer.toString(cachedPushups);

            //set the values in the UI
            levelNumber.setText(levelAsString);
            weekNumber.setText(weekAsString);
            dayNumber.setText(dayAsString);
            roundNumber.setText(roundAsString);
            pushupNumber.setText(pushupAsString);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void finishedButton() {
        //logic determines what changes to make to displayed numbers to show progress
        if(cachedRound<5){
            cachedRound = cachedRound+1;
            setNextSet();
        }

        else if (cachedRound==5){
            cachedRound=1;
            cachedDay=cachedDay+1;
            if (cachedDay==4) {
                if(cachedWeek == 2 || cachedWeek == 4 || cachedWeek == 5) {

                    getApplicationContext().deleteFile("current_progress");
                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                    intent.putExtra("week_from_main",cachedWeek);
                    startActivity(intent);
                    finish();
                }
                else if(cachedWeek == 6){
                    Toast toast = Toast.makeText(MainActivity.this, "Congrats! You've finished!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {

                    cachedDay = 1;
                    cachedWeek = cachedWeek+1;
                    setNextSet();

                }

            }

            else {
                setNextSet();
            }

        }
    }

    private void setNextSet() {
        //load the next set information into a new Set object
        Set nextSet = new Set();
        nextSet.setLevel(cachedLevel);
        nextSet.setWeek(cachedWeek);
        nextSet.setDay(cachedDay);
        nextSet.setRound(cachedRound);

        try {
            // Save the list of entries to internal storage
            InternalStorage.writeObject(getApplicationContext(), KEY, nextSet);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }


        //restart the activity with the new values
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



    private ArrayList<Set> loadSetsXml() {
        //make new array list to hold the sets xml data
        ArrayList<Set> setsArray = new ArrayList<Set>();

        //set up the xmlpullparser
        XmlPullParser xpp = getResources().getXml(R.xml.sets);

        try{
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {


                if (eventType == XmlPullParser.START_DOCUMENT) {
                    //System.out.println("Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    String nameString = xpp.getName();
                    //System.out.println("Start tag "+nameString);
                    if (nameString.equals("sets")) {
                        //System.out.println("Sets: nothing happens");
                    } else if (nameString.equals("set")) {
                        //System.out.println("Set: loads the set info");
                        xpp.next();

                        Set set = new Set();
                        int levelTemp = 0;
                        int weekTemp = 0;
                        int dayTemp = 0;
                        int roundTemp = 0;
                        int pushupsTemp = 0;

                        xpp.next();
                        //level
                        levelTemp = Integer.parseInt(xpp.getText());
                        xpp.next(); //close level
                        xpp.next(); //week
                        xpp.next();
                        weekTemp = Integer.parseInt(xpp.getText());

                        xpp.next(); //close week

                        xpp.next(); //round
                        xpp.next();
                        dayTemp = Integer.parseInt(xpp.getText());

                        xpp.next(); //close day

                        xpp.next(); //day
                        xpp.next();
                        roundTemp = Integer.parseInt(xpp.getText());

                        xpp.next(); //close day
                        xpp.next(); //pushups
                        xpp.next();
                        pushupsTemp = Integer.parseInt(xpp.getText());

                        set.setLevel(levelTemp);
                        //System.out.println(levelTemp);

                        set.setWeek(weekTemp);
                        //System.out.println(weekTemp);

                        set.setDay(dayTemp);
                        //System.out.println(dayTemp);

                        set.setRound(roundTemp);
                        //System.out.println(roundTemp);

                        set.setPushups(pushupsTemp);
                        //System.out.println(pushupsTemp);



                        setsArray.add(set);

                        //.out.println("Saving level" + set.getLevel());
                        //System.out.println("Saving week" + set.getWeek());
                        //System.out.println("Saving day" + set.getDay());
                        //System.out.println("Saving round" + set.getRound());
                        //System.out.println("Saving pushups" + set.getPushups());

                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    //System.out.println("End tag " + xpp.getName());
                }

                eventType = xpp.next();
            }
            //System.out.println("End document");
        }
        catch(XmlPullParserException e) {
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("stuff", "up");
        }
        //System.out.println("size of array"+setsArray.size());
        return setsArray;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:

                getApplicationContext().deleteFile("current_progress");
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();

                ;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
