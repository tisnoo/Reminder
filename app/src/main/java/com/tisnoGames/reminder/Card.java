package com.tisnoGames.reminder;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class Card {
    //Save the textView and deletebutton
    private final ImageView deleteButton;
    private final TextView cardText;
    final CardView temp;


    //Constructor
    public Card(Context context,String text){
        //Setting up the width for the card
        int cardWidth = 1020;
        final ConstraintLayout layout = MainActivity.layout;
        //Setting up the text to be inserted into the card
        cardText = new TextView(context);
        //Setting up the delete button to be inserted into the card later
        deleteButton = new ImageView(context);

        //Creating a new cardview
        temp = new CardView(context);

        //Setting properties for the text on the card
        cardText.setText(text);
        cardText.setY(30);
        cardText.setX(30);

        //Settin properties for the deletebutton
        deleteButton.setImageResource(R.mipmap.delete);



        //Setting properties for the new card
        temp.setPreventCornerOverlap(false);
        temp.setCardBackgroundColor(Color.WHITE);
        temp.setMinimumWidth(cardWidth);
        cardText.setMaxWidth(cardWidth-300);

        //Adding the text to the card.
        temp.addView(cardText);
        temp.addView(deleteButton);
        cardText.getLineCount();
        temp.setCardElevation(10f);
        temp.setY(10f);
        temp.setX(30f);
        temp.setAlpha(0f);

        //Set x and y for deletebutton
        deleteButton.setX(cardWidth-65);
        deleteButton.setY(15);
        deleteButton.getLayoutParams().width = 50;
        deleteButton.getLayoutParams().height= 50;

        //Add a clicklistener to the deletebutton
        deleteButton.setOnClickListener(new deleteListener(temp));
        deleteButton.setVisibility(View.INVISIBLE);
        deleteButton.setEnabled(false);


        //Add com.tisnoGames.reminder.Card to The Layout (inside of the scrollView)
        layout.addView(temp);

        //Create a new clicklistener
        LongClickListener cl = new LongClickListener(this,context);


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
                        MainActivity.layoutHeight += temp.getMinimumHeight()+40;
                        layout.setMinHeight(MainActivity.layoutHeight);

                        cardText.getViewTreeObserver().removeGlobalOnLayoutListener( this );

                        //Move the cards before adding the newly made card to the cardArraylist so that the card doesnt move down
                        MainActivity.moveCards(temp,temp.getHeight() +80);

                        //Add the card to the card Arraylist
                        MainActivity.allCards.add(temp);
                    }

                });
    }

    //Get the Deletebutton or the CardText or the CardView
    public CardView getTemp() {
        return temp;
    }

    public ImageView getDeleteButton() {
        return deleteButton;
    }

    public TextView getCardText() {
        return cardText;
    }


}
