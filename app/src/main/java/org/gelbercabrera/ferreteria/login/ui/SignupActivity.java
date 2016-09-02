package org.gelbercabrera.ferreteria.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import org.gelbercabrera.ferreteria.R;
import org.gelbercabrera.ferreteria.login.LoginPresenter;
import org.gelbercabrera.ferreteria.login.LoginPresenterImpl;
import org.gelbercabrera.ferreteria.main.ui.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class SignupActivity extends AppCompatActivity implements LoginView{

    @Bind(R.id.btnSignup)
    Button btnSignUp;
    @Bind(R.id.editTxtEmail)
    EditText inputEmail;
    @Bind(R.id.editTxtPassword)
    EditText inputPassword;
    @Bind(R.id.editTxtPasswordConfirmation)
    EditText passwordConfirm;
    @Bind(R.id.editTxtName)
    EditText inputName;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setTitle("Registro");

        loginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onPause() {
        loginPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnSignup)
    @Override
    public void handleSignUp() {
        if (validate()) {
            loginPresenter.registerNewUser(inputName.getText().toString(),
                    inputEmail.getText().toString(),
                    inputPassword.getText().toString());
        }
    }

    @Override
    public void handleSignIn() {
        throw new UnsupportedOperationException("Operation is not valid in this status");
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginError(String error) {
        throw new UnsupportedOperationException("Operation is not valid in this status");
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_signup, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enable) {
        inputName.setEnabled(enable);
        inputEmail.setEnabled(enable);
        inputPassword.setEnabled(enable);
        btnSignUp.setEnabled(enable);
    }

    public boolean validate() {
        boolean valid = true;

        String password = inputPassword.getText().toString();
        String confirmPassword = passwordConfirm.getText().toString();

        if (password.isEmpty() || password.length() < 4
                || password.length() > 10) {
            inputPassword.setError("La contraseña debe contener entre 4 " +
                    "y 10 caracteres alfanumericos");
            valid = false;
        }

        if (!confirmPassword.equals(password)) {
            passwordConfirm.setError("Las contraseñas no coinciden");
            valid = false;
        }

        return valid;
    }

}