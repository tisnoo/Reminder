package com.tisnoGames.reminder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;
    private ConstraintLayout layout;
    private ArrayList<CardView> allCards = new ArrayList<>();
    private int layoutHeight = 30;

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


    //Create a new card with a reminder on it.
    public void newCard(View view)
    {

        //Create popup known as "InputDialog"
        FragmentManager fm = getSupportFragmentManager();
        InputDialog id = new InputDialog();
        id.showNow(fm,"go");

        //Setting up the text to be inserted into the card
        TextView cardText = new TextView(this);
        cardText.setText("\"Lorem ipsum dolor sit amet, consect d officia deserunt mollit anim id est laborum l;fsjdalkj ;lkjsfd;alkj flkjdsflkj dslkjfdsf s fdsdf sfd ljsdf slfd jdsfnsdfn jfsldf jls fdlsjdf jsfdj.\"");
        cardText.setY(30);
        cardText.setX(30);


        //Setting properties for newly made card
        CardView temp = new CardView(this);
        temp.setCardBackgroundColor(Color.WHITE);
        temp.setMinimumWidth(1020);
        cardText.setWidth(temp.getMinimumWidth()-30);

        //Adding the text to the card.
        temp.addView(cardText);
        cardText.getLineCount();
        temp.setCardElevation(10f);
        temp.setY(10f);
        temp.setX(30f);
        temp.setMinimumHeight(300);
        temp.setAlpha(0f);


        //Add Card to The Layout (inside of the scrollView)
        layout.addView(temp);

        //Move the cards before adding the newly made card to the cardArraylist so that the card doesnt move down
        moveCards(temp);

        //Add the card to the card Arraylist
        allCards.add(temp);

        //Add height to the navigation so that the user cant scroll lower than the cards, but all the cards are still visible
        layoutHeight += temp.getMinimumHeight()+40;
        layout.setMinHeight(layoutHeight);
    }

    //Move the cards down so the new card is above everything else.
    private void moveCards(CardView newCard)
    {
        //Make every card in the arrayList move
        for (CardView temp : allCards)
        {
            temp.animate().y(temp.getY()+temp.getHeight()+40).setDuration(500);
        }
        newCard.animate().alpha(1).setDuration(800);
    }
}
