package com.example.craontestapp.util;

import android.graphics.Typeface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class TextUtil {

    private boolean isChecked = false;

    public void showPasswordBehaviour(ImageView view, EditText editText) {
        if (isChecked) {
            editText.setInputType(0x00000081);
            editText.setTypeface(Typeface.DEFAULT);
            isChecked = false;
            setShowPswBehaviour(editText);
        } else {
            editText.setInputType(0x0000001);
            editText.setTypeface(Typeface.DEFAULT);
            isChecked = true;
            setShowPswBehaviour(editText);
        }
    }

    public void cancelEmailBehaviour(ImageView view, EditText editText) {
        editText.getText().clear();
    }

    private void setShowPswBehaviour(EditText pswET) {
        int position = pswET.getText().length();
        pswET.setSelection(position);
    }

    public void setUpTextField(CharSequence s, ImageView view) {
        if (!s.toString().isEmpty()) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void resetTextFields(EditText email, EditText psw, EditText confPsw, CheckBox checkBox) {
        if (email != null) {
            email.getText().clear();
        }
        if (psw != null) {
            psw.getText().clear();
        }
        if (confPsw != null) {
            confPsw.getText().clear();
        }
        if (checkBox != null) {
            checkBox.setChecked(false);
        }
    }
}
