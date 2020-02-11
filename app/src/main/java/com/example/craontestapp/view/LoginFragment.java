package com.example.craontestapp.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.craontestapp.R;
import com.example.craontestapp.util.TextUtil;
import com.example.craontestapp.util.Validator;
import com.example.craontestapp.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.katso.livebutton.LiveButton;

public class LoginFragment extends Fragment {

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


    public LoginFragment() {}


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

    @Override
    public void onStart() {
        super.onStart();

        // gestione campo email e icona per cancellare il testo inserito
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, cancelEmail);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        cancelEmail.setOnClickListener(v -> {
            textUtil.cancelEmailBehaviour(cancelEmail, emailET);
        });

        // gestione campo password e icona per rendere visibile la password
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, showPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        showPassword.setOnClickListener(v -> {
            textUtil.showPasswordBehaviour(showPassword, passwordET);
        });

        // validazione login e controllo esistenza tente su database
        loginButton.setOnClickListener(v -> {

            if (validator.validateEmail(emailET)){
                // TODO: flusso login
                // controllo utente esistente

                // salvataggio utenza nelle shared preference

                // proseguo verso la main activity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        textUtil.resetTextFields(emailET, passwordET, null, null);
    }
}
