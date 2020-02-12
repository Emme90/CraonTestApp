package com.example.craontestapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.craontestapp.model.User;
import com.example.craontestapp.model.UserDAO;
import com.example.craontestapp.model.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel extends AndroidViewModel {

    private InsertUserTask insertTask;
    private CheckExistingUserTask checkTask;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean checkExistingUser(String email) {
        checkTask = new CheckExistingUserTask();
        if (checkTask.doInBackground(email)){
            return true;
        }
        return false;
    }

    public void insertUser(User user) {
        insertTask = new InsertUserTask();
        insertTask.execute(user);

    }

    // check for existing user
    private class CheckExistingUserTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            List<User> existingUsers;
            UserDAO userDAO = UserDatabase.getInstance(getApplication()).userDAO();
            existingUsers = userDAO.getAllUser();
            String userEmail = strings[0];
            if (existingUsers.size() == 0) {
                return true;
            } else {
                for (User u : existingUsers) {
                    if (u.email.equals(userEmail)) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

        }
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
