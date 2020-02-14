package com.example.craontestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.craontestapp.model.User;
import com.example.craontestapp.model.UserDatabase;

public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean loginUser(String email, String psw) {
        User user = UserDatabase.getInstance(getApplication()).userDAO().getUserByEmail(email);
        if (user != null) {
            if (user.password.equals(psw)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
