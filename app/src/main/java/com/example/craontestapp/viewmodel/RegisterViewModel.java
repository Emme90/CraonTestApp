package com.example.craontestapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.craontestapp.model.User;
import com.example.craontestapp.model.UserDAO;
import com.example.craontestapp.model.UserDatabase;

import java.util.List;

public class RegisterViewModel extends AndroidViewModel {

    private InsertUserTask insertTask;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean registerUser(String email, String psw) {
        List<User> userList = UserDatabase.getInstance(getApplication()).userDAO().getAllUser();
        if (userList.size() == 0) {
            // registrazione nuovo utente
            User u = new User(email, psw);
            insertUser(u);
            // proseguo verso login activity
            return true;
        } else {
            for (User u : userList) {
                if (u.email.equals(email)) {
                    return false;
                }
            }
            // registrazione nuovo utente
            User user = new User(email, psw);
            insertUser(user);
            // proseguo verso login activity
            return true;
        }
    }

    public void insertUser(User user) {
        insertTask = new InsertUserTask();
        insertTask.execute(user);
    }

    // saving new user
    private class InsertUserTask extends AsyncTask<User, Void, User> {
        @Override
        protected User doInBackground(User... users) {
            User u = users[0];
            UserDAO userDAO = UserDatabase.getInstance(getApplication()).userDAO();
            userDAO.insertUser(u);
            return null;
        }

        @Override
        protected void onPostExecute(User user) {

        }
    }
}
