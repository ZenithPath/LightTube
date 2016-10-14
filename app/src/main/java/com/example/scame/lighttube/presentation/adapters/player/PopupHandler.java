package com.example.scame.lighttube.presentation.adapters.player;


import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;

import com.example.scame.lighttube.R;

public class PopupHandler {

    private String identifier;

    public PopupHandler(String identifier) {
        this.identifier = identifier;
    }

    public void showPopup(View anchor, String resourceIdentifier) {
        if (resourceIdentifier.equals(identifier)) {
            showAuthoredPopup(anchor);
        } else {
            showCommonPopup(anchor);
        }
    }

    private void showAuthoredPopup(View anchor) {
        PopupMenu authoredPopup = new PopupMenu(anchor.getContext(), anchor);
        authoredPopup.inflate(R.menu.comment_popup);
        authoredPopup.getMenu().removeItem(R.id.report_option);

        authoredPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.delete_option) {
                Log.i("onxDelete", "click");
            } else if (item.getItemId() == R.id.edit_option) {
                Log.i("onxEdit", "click");
            }
            return false;
        });

        authoredPopup.show();
    }

    private void showCommonPopup(View anchor) {
        PopupMenu commonPopup = new PopupMenu(anchor.getContext(), anchor);
        commonPopup.inflate(R.menu.comment_popup);
        commonPopup.getMenu().removeItem(R.id.delete_option);
        commonPopup.getMenu().removeItem(R.id.edit_option);

        commonPopup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.report_option) {
                Log.i("onxReport", "click");
            }

            return false;
        });

        commonPopup.show();
    }
}
