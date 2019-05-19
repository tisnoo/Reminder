package com.tisnoGames.reminder;

import android.support.v7.widget.CardView;
import android.view.View;

public class deleteListener implements View.OnClickListener {
    private CardView cardView;

    public deleteListener(CardView temp){
        this.cardView = temp;
    }

    @Override
    public void onClick(View v) {
        MainActivity.removeCard(this.cardView);
    }
}
