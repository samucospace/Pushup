package com.hokkaidoprojects.pushup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StartActivity extends AppCompatActivity {
    final String TAG = "Saving error";
    final String KEY = "current_progress";
    private int weekFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        try {
            // Retrieve the list from internal storage
            Set cachedProgress = (Set) InternalStorage.readObject(getApplicationContext(), KEY);

            if (cachedProgress!=null) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
            }

        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        final EditText numberEditText = (EditText) findViewById(R.id.numberEditText);
        final TextView startTextView = (TextView) findViewById(R.id.startTextView);
        Button goButton = (Button) findViewById(R.id.goButton);

        //receive week number from MainActivity if the program has reached week 2, 4, 5 or 6
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weekFromMain = extras.getInt("week_from_main");
            //set EditText with the right text
            switch(weekFromMain){
                case 2:
                    startTextView.setText(R.string.first_test_prompt);
                    break;

                case 4:
                    startTextView.setText(R.string.second_test_prompt);
                    break;

                case 5:
                    startTextView.setText(R.string.second_test_prompt);
                    break;
            }
        }

        //logic for Go button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", numberEditText.getText().toString());
                String numberString = numberEditText.getText().toString();
                if (numberString.equals("")) {
                    Toast toast = Toast.makeText(StartActivity.this, "Please enter a number", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    int number = Integer.parseInt(numberString);
                    //Log.i("int", "" + number);

                    Set currentProgress = new Set();
                    currentProgress.setDay(1);
                    currentProgress.setRound(1);

                    //test to see whether the test is brand new or week 3, 5, or 6
                    if(weekFromMain == 0 ) {

                        if (number < 6) {
                            currentProgress.setLevel(1);
                            currentProgress.setWeek(1);

                        } else if (number > 5 && number < 11) {
                            currentProgress.setLevel(2);
                            currentProgress.setWeek(1);
                        } else if (number > 10 && number < 21) {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(1);
                        } else if (number > 20 && number < 26) {
                            currentProgress.setLevel(2);
                            currentProgress.setWeek(3);
                        } else {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(3);
                        }

                        try {
                            // Save the list of entries to internal storage
                            InternalStorage.writeObject(getApplicationContext(), KEY, currentProgress);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    //else test must be from a later week

                    else if (weekFromMain == 2) {
                        if (number < 16) {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(2);

                        } else if (number > 15 && number < 21) {
                            currentProgress.setLevel(1);
                            currentProgress.setWeek(3);
                        } else if (number > 20 && number < 26) {
                            currentProgress.setLevel(2);
                            currentProgress.setWeek(3);
                        } else {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(3);
                        }

                        try {
                            // Save the list of entries to internal storage
                            InternalStorage.writeObject(getApplicationContext(), KEY, currentProgress);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else if (weekFromMain == 4) {
                        if (number < 31) {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(4);

                        } else if (number > 30 && number < 36) {
                            currentProgress.setLevel(1);
                            currentProgress.setWeek(5);
                        } else if (number > 35 && number < 41) {
                            currentProgress.setLevel(2);
                            currentProgress.setWeek(5);
                        } else {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(5);
                        }

                        try {
                            // Save the list of entries to internal storage
                            InternalStorage.writeObject(getApplicationContext(), KEY, currentProgress);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else if (weekFromMain == 5) {
                        if (number < 46) {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(5);

                        } else if (number > 45 && number < 51) {
                            currentProgress.setLevel(1);
                            currentProgress.setWeek(6);
                        } else if (number > 50 && number < 61) {
                            currentProgress.setLevel(2);
                            currentProgress.setWeek(6);
                        } else {
                            currentProgress.setLevel(3);
                            currentProgress.setWeek(6);
                        }

                        try {
                            // Save the list of entries to internal storage
                            InternalStorage.writeObject(getApplicationContext(), KEY, currentProgress);
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage());
                        }

                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }



                    }



            }
        });




    }
}
