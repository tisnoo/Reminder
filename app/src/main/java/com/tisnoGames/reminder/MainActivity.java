package com.tisnoGames.reminder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private static View highlightedView;
    private TextView mTextMessage;
    public static ConstraintLayout layout;
    public static ArrayList<CardView> allCards = new ArrayList<>();
    private int layoutHeight = 30;
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
                        // When user clicks on create button, create new Card with the right text.
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

        final ConstraintLayout layout = MainActivity.layout;
        //Setting up the text to be inserted into the card
        final TextView cardText = new TextView(this);

        //Creating a new cardview
        final CardView temp = new CardView(this);

        //Setting properties for the text on the card
        cardText.setText(text);
        cardText.setY(30);
        cardText.setX(30);

        //Setting properties for the new card
        temp.setCardBackgroundColor(Color.WHITE);
        temp.setMinimumWidth(1020);
        cardText.setWidth(temp.getMinimumWidth()-30);

        //Adding the text to the card.
        temp.addView(cardText);
        cardText.getLineCount();
        temp.setCardElevation(10f);
        temp.setY(10f);
        temp.setX(30f);
        temp.setAlpha(0f);


        //Add Card to The Layout (inside of the scrollView)
        layout.addView(temp);

        //Create a new clicklistener
        LongClickListener cl = new LongClickListener(temp, cardText,this);


        //Add the onClickListeners to the card
        temp.setOnLongClickListener(cl);
        temp.setOnClickListener(cl);

        //Add a ongloballayoutlistener so that the height of the text can be caught
        temp.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view
                        temp.setMinimumHeight(cardText.getHeight()+60);

                        //Add height to the navigation so that the user cant scroll lower than the cards, but all the cards are still visible
                        layoutHeight += temp.getMinimumHeight()+40;
                        layout.setMinHeight(layoutHeight);

                        cardText.getViewTreeObserver().removeGlobalOnLayoutListener( this );

                        //Move the cards before adding the newly made card to the cardArraylist so that the card doesnt move down
                        moveCards(temp);

                        //Add the card to the card Arraylist
                        allCards.add(temp);
                    }

                });
    }

    //Move the cards down so the new card is above everything else.
    private void moveCards(CardView newCard)
    {
        //Make every card in the arrayList move
        for (CardView temp : allCards)
        {
            temp.animate().y(temp.getY()+newCard.getHeight()+80).setDuration(500);
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
}