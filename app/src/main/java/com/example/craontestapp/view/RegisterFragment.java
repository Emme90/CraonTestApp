package com.example.craontestapp.view;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.craontestapp.R;
import com.example.craontestapp.util.TextUtil;
import com.example.craontestapp.util.Validator;
import com.example.craontestapp.viewmodel.RegisterViewModel;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle3.LifecycleProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.katso.livebutton.LiveButton;

public class RegisterFragment extends Fragment {

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private final LifecycleProvider<Lifecycle.Event> provider = AndroidLifecycle.createLifecycleProvider(this);

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

    @SuppressLint("CheckResult")
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
                            Single.fromCallable(() -> registerViewModel.registerUser(email.getText().toString(), psw.getText().toString()))
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .compose(provider.bindToLifecycle())
                                    .subscribe(
                                            (Boolean result) -> {
                                                if (result != null) {
                                                    if (result) {
                                                        Toast.makeText(v.getContext(), "Utente registrato con successo!", Toast.LENGTH_SHORT).show();
                                                        NavDirections action = RegisterFragmentDirections.actionRegistrationComplete();
                                                        Navigation.findNavController(v).navigate(action);
                                                    } else {
                                                        Toast.makeText(v.getContext(), "Utente già registrato!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            },
                                            (Throwable throwable) -> {
                                                if (throwable != null) {
                                                    Log.e(TAG, throwable.getMessage());
                                                }
                                            });
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
