package com.example.craontestapp.view;


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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.craontestapp.R;
import com.example.craontestapp.model.User;
import com.example.craontestapp.util.TextUtil;
import com.example.craontestapp.util.Validator;
import com.example.craontestapp.viewmodel.RegisterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.katso.livebutton.LiveButton;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private TextUtil textUtil = new TextUtil();
    private Validator validator = new Validator();

    @BindView(R.id.cancelRegisterEmail)
    ImageView cancelEmail;
    @BindView(R.id.showRegisterPassword)
    ImageView showPsw;
    @BindView(R.id.showConfRegisterPassword)
    ImageView showConfPsw;
    @BindView(R.id.registrationEmail)
    EditText email;
    @BindView(R.id.registrationPassword)
    EditText psw;
    @BindView(R.id.registrationConfPassword)
    EditText confPsw;
    @BindView(R.id.registrationCheckBox)
    CheckBox checkBox;
    @BindView(R.id.confRegistrationButton)
    LiveButton registrationButton;


    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();

        // gestione campo email e icona per cancellare il testo inserito
        email.addTextChangedListener(new TextWatcher() {
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
            textUtil.cancelEmailBehaviour(cancelEmail, email);
        });

        // gestione campo password e icona per rendere visibile la password
        psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, showPsw);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        showPsw.setOnClickListener(v -> {
            textUtil.showPasswordBehaviour(showPsw, psw);
        });

        // gestione campo conf psw e icona per rendere visibile la password
        confPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textUtil.setUpTextField(s, showConfPsw);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        showConfPsw.setOnClickListener(v -> {
            textUtil.showPasswordBehaviour(showConfPsw, confPsw);
        });

        // validazione campi e registrazione nuovo utente
        registrationButton.setOnClickListener(v -> {
            if (validator.validateEmail(email)) {
                if (validator.validatePassword(psw)) {
                    if (validator.matchPsw(psw, confPsw)) {
                        if (checkBox.isChecked()) {
                            // controllo utente già esistente
                            registerViewModel.checkExistingUser(email.getText().toString(), (b -> {
                                if (b){
                                    // registrazione nuovo utente
                                    User u = new User(email.getText().toString(), psw.getText().toString());
                                    registerViewModel.insertUser(u);
                                    // proseguo verso login activity
                                    NavDirections action = RegisterFragmentDirections.actionRegistrationComplete();
                                    Navigation.findNavController(v).navigate(action);
                                } else {
                                    Toast.makeText(v.getContext(), "Utente già registrato!", Toast.LENGTH_SHORT).show();
                                }
                            }));

                        } else {
                            checkBox.setError("Devi accettare i Termini e Condizioni del servizio");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        textUtil.resetTextFields(email, psw, confPsw, checkBox);
    }


}
