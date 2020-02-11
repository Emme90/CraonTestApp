package com.example.craontestapp.util;

import android.util.Patterns;
import android.widget.EditText;

public class Validator {

    public boolean validateEmail(EditText email){
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            return true;
        } else {
            email.setError("Inserisci una email valida");
            return false;
        }
    }

    public boolean validatePassword(EditText psw){
        String password = psw.getText().toString();
        if (password.isEmpty() || password.length() <= 4){
            psw.setError("Inserisci una password maggiore di 4 caratteri");
            return false;
        } else {
            return true;
        }
    }

    public boolean matchPsw(EditText psw, EditText confPsw){
        String p1 = psw.getText().toString();
        String p2 = confPsw.getText().toString();

        if (p1.equals(p2)){
            return true;
        } else {
            confPsw.setError("Le password non coincidono");
            return false;
        }
    }
}
