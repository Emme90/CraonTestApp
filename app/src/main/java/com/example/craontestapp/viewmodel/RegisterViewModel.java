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
    private CheckExistingUserTask checkTask;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void checkExistingUser(String email, Task task) {
        checkTask = new CheckExistingUserTask();
        checkTask.execute(email, task);
    }

    public void insertUser(User user) {
        insertTask = new InsertUserTask();
        insertTask.execute(user);

    }

    public interface Task {
        void execute(boolean b);
    }

    // check for existing user
    private class CheckExistingUserTask extends AsyncTask<Object, Void, Boolean> {

        private Task task;

        @Override
        protected Boolean doInBackground(Object... strings) {
            List<User> existingUsers;
            UserDAO userDAO = UserDatabase.getInstance(getApplication()).userDAO();
            existingUsers = userDAO.getAllUser();
            String userEmail = (String) strings[0];
            task = (Task) strings[1];
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
            task.execute(aBoolean);
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
