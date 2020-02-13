package com.example.craontestapp.view;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.craontestapp.R;
import com.example.craontestapp.util.TextUtil;
import com.example.craontestapp.util.Validator;
import com.example.craontestapp.viewmodel.LoginViewModel;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.katso.livebutton.LiveButton;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);

    private LoginViewModel viewModel;
    private TextUtil textUtil = new TextUtil();
    private Validator validator = new Validator();

    @BindView(R.id.emailLoginEditText)
    EditText emailET;
    @BindView(R.id.passwordLoginEditText)
    EditText passwordET;
    @BindView(R.id.cancelLoginEmail)
    ImageView cancelEmail;
    @BindView(R.id.showLoginPassword)
    ImageView showPassword;
    @BindView(R.id.goToRegisterButton)
    LiveButton goToRegister;
    @BindView(R.id.loginButton)
    LiveButton loginButton;


    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        goToRegister.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionToRegistration();
            Navigation.findNavController(v).navigate(action);
        });

    }

    @SuppressLint("CheckResult")
    @Override
    public void onStart() {
        super.onStart();

        // gestione campo email e icona per cancellare il testo inserito
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, cancelEmail);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cancelEmail.setOnClickListener(v -> {
            textUtil.cancelEmailBehaviour(cancelEmail, emailET);
        });

        // gestione campo password e icona per rendere visibile la password
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, showPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        showPassword.setOnClickListener(v -> {
            textUtil.showPasswordBehaviour(showPassword, passwordET);
        });

        // validazione login e controllo esistenza tente su database
        loginButton.setOnClickListener(v -> {

            if (validator.validateEmail(emailET)) {
                // TODO: flusso login
                // controllo utente esistente
                Single.fromCallable(() -> viewModel.loginUser(emailET.getText().toString(), passwordET.getText().toString()))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(provider.bindToLifecycle())
                        .subscribe(
                                (Boolean result) -> {
                                    if (result != null) {
                                        if (result) {
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            getActivity().startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            Toast.makeText(v.getContext(), "Email o password non corretti", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                (Throwable throwable) -> {
                                    if (throwable != null) {
                                        Log.e(TAG, throwable.getMessage());
                                    }
                                });
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        textUtil.resetTextFields(emailET, passwordET, null, null);
    }
}
