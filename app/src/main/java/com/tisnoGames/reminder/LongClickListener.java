package com.tisnoGames.reminder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

public class LongClickListener implements View.OnLongClickListener, View.OnClickListener {
    final private CardView cardView;
    final private TextView textView;
    private Context context;
    private boolean isSelected = false;
    private static  boolean editable = true;
    private int differenceInHeight;

    public LongClickListener(CardView cardView, TextView textView, Context context)
    {
        this.cardView   = cardView;
        this.textView   = textView;
        this.context    = context;
    }

    @Override
    public void onClick(View v)
    {
        //Only if the card is selected the selection can be reversed
        if (isSelected)
        {
            MainActivity.reverseLongClick(v);
            isSelected = false;
            editable = true;
            return;
        }
        //Else if the card is not selected it can be updated
        if (editable){
            //Build the builder
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final EditText userinput = new EditText(context);
            userinput.setHeight(300);
            userinput.setText(this.textView.getText().toString());
            userinput.setGravity(Gravity.TOP);
            userinput.setVerticalFadingEdgeEnabled(true);

            builder
                    .setView(userinput)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // When user clicks on create button, create new Card with the right text.
                            if (!(userinput.getText().toString().trim().isEmpty())) {
                                textView.setText(userinput.getText().toString());

                                //Save the difference in height of the cardview as int and adjust the cardview
                                differenceInHeight = cardView.getMinimumHeight();

                                cardView.getViewTreeObserver().addOnGlobalLayoutListener(
                                        new ViewTreeObserver.OnGlobalLayoutListener(){

                                            @Override
                                            public void onGlobalLayout() {
                                                // gets called after layout has been done but before display
                                                // so we can get the height then hide the view
                                                cardView.setMinimumHeight(textView.getHeight()+60);

                                                //Add height to the navigation so that the user cant scroll lower than the cards, but all the cards are still visible
                                                differenceInHeight = cardView.getMinimumHeight() - differenceInHeight;

                                                MainActivity.layoutHeight += differenceInHeight;
                                                MainActivity.layout.setMinHeight(MainActivity.layoutHeight);

                                                cardView.getViewTreeObserver().removeGlobalOnLayoutListener( this );

                                                //Move the cards before adding the newly made card to the cardArraylist so that the card doesnt move down
                                                MainActivity.moveCards(cardView,differenceInHeight);
                                                }

                                        });

                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog

                        }
                    });
            builder.show();
        }

    }

    public boolean onLongClick(View v)
    {
        MainActivity.onLongClick(v);
        MainActivity.cardHighlighted = true;
        isSelected = true;
        editable = false;
        return true;
    }
}
