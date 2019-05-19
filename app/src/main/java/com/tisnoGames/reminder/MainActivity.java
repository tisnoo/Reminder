package com.tisnoGames.reminder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private static View highlightedView;
    private TextView mTextMessage;
    public static ConstraintLayout layout;
    public static ArrayList<CardView> allCards = new ArrayList<>();
    public static int layoutHeight = 30;
    public static boolean cardHighlighted = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        layout = (ConstraintLayout) findViewById(R.id.ScrollLayout);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void initNewCard(final View view){
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        //Build the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText userinput = new EditText(this);
        userinput.setHeight(300);
        userinput.setGravity(Gravity.TOP);
        userinput.setVerticalFadingEdgeEnabled(true);
        //Focus on keyboard so that the keyboard automatically opens when this Dialog is called
        imm.showSoftInput(userinput, InputMethodManager.SHOW_IMPLICIT);

        builder
                .setView(userinput)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // When user clicks on create button, create new com.tisnoGames.reminder.Card with the right text.
                        if (!(userinput.getText().toString().trim().isEmpty())) {
                            newCard(userinput.getText().toString());
                        }
                        }
                })
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.show();


        //Reverse the longclick if there is need for
        if (highlightedView != null) {
            System.out.println("test");
            MainActivity.reverseLongClick(highlightedView);
        }
    }


    //Create a new card with a reminder on it.
    public void newCard(String text)
    {
        Card card = new Card(this,text);
    }

    //Move the cards down so the new card is above everything else.
    public static void moveCards(CardView newCard, int height)
    {
        //Make every card in the arrayList move
        for (CardView temp : allCards)
        {
            if (temp != newCard) {
                if (temp.getY() >= newCard.getY()) {
                    temp.animate().y(temp.getY() + height).setDuration(500);
                }
            }
        }
        newCard.animate().alpha(1).setDuration(800);
    }

    //Make the onLongClick animation and delete button appear
    public static void onLongClick(View v) {
        //Only run if its the first longClick in a row
        if (!MainActivity.cardHighlighted) {

            //Set v equal to v
            highlightedView = v;

            //First change the layout so everything fits
            MainActivity.layout.setMinHeight(MainActivity.layout.getMinHeight() + 80);

            //Then for all cardview move them down a bit
            for (CardView temp : MainActivity.allCards) {
                if (temp != v) {
                    temp.setAlpha(temp.getAlpha() / 2);

                    if (temp.getY() > v.getY()) {
                        temp.animate().y(temp.getY() + 80).setDuration(100);
                    }
                }
            }
        }
    }

    public static void reverseLongClick(View v){
        //Only run if cards are highlighted
        if (MainActivity.cardHighlighted) {

            //First change the layout so everything fits
            MainActivity.layout.setMinHeight(MainActivity.layout.getMinHeight() - 80);

            //Then for all cardview move them down a bit
            for (CardView temp : MainActivity.allCards) {
                if (temp != v) {
                    temp.setAlpha(1);

                    if (temp.getY() > v.getY()) {
                        temp.animate().y(temp.getY() - 80).setDuration(100);
                    }
                }
            }
            MainActivity.cardHighlighted = false;
        }
    }


    //If the delete button is clicked, remove the specified card
    public static void removeCard(CardView cardView){
        //First move the cards up so that there isnt a gap
        MainActivity.moveCards(cardView,-cardView.getHeight());
        //Then delete the specified card
        MainActivity.layout.removeView(cardView);

    }
}