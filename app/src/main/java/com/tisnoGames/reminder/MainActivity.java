package com.tisnoGames.reminder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private CardView mainCard;
    private ConstraintLayout layout;
    private ArrayList<CardView> allCards = new ArrayList<>();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mainCard = (CardView) findViewById(R.id.card_view);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        layout = (ConstraintLayout) findViewById(R.id.container);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void newCard(View view){
        System.out.println("test");
        CardView temp = new CardView(this);
        temp.setCardBackgroundColor(Color.BLUE);
        temp.setMinimumWidth(1020);
        temp.setMinimumHeight(300);
        temp.setCardElevation(10);
        temp.setY(300f);
        temp.setX(30);
        layout.addView(temp);
    }

}
