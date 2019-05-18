package com.tisnoGames.reminder;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LongClickListener implements View.OnLongClickListener, View.OnClickListener {
    private CardView cardView;
    private TextView textView;
    private Context context;
    private boolean isSelected = false;
    private static  boolean editable = true;

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
            userinput.setText(this.textView.toString());
            userinput.setGravity(Gravity.TOP);
            userinput.setVerticalFadingEdgeEnabled(true);

            builder
                    .setView(userinput)
                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // When user clicks on create button, create new Card with the right text.
                        }
                    })
                    .setNegativeButton("close", new DialogInterface.OnClickListener() {
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
