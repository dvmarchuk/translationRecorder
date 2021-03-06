package com.door43.login;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.door43.login.core.Profile;
import com.door43.widgets.dialogs.CustomAlertDialog;
import com.door43.login.tasks.RegisterDoor43Task;
import com.door43.sysutils.tasks.ManagedTask;
import com.door43.sysutils.tasks.TaskManager;
import com.door43.widgets.widgets.ViewUtil;

import org.unfoldingword.gogsclient.User;

public class RegisterDoor43Activity extends AppCompatActivity implements ManagedTask.OnFinishedListener {

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_door43);

        final EditText fullNameText = (EditText)findViewById(R.id.full_name);
        final EditText emailText = (EditText)findViewById(R.id.email);
        final EditText usernameText = (EditText)findViewById(R.id.username);
        final EditText passwordText = (EditText)findViewById(R.id.password);
        final EditText password2Text = (EditText)findViewById(R.id.password2);

        final ToggleButton showPasswordButton = (ToggleButton) findViewById(R.id.show_password_button);
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = showPasswordButton.isChecked() ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD;
                passwordText.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
                passwordText.setSelection(passwordText.getText().length());
                password2Text.setInputType(InputType.TYPE_CLASS_TEXT | inputType);
                password2Text.setSelection(password2Text.getText().length());
            }
        });
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button continueButton = (Button)findViewById(R.id.ok_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppContext.closeKeyboard(RegisterDoor43Activity.this);

                final String fullName = fullNameText.getText().toString().trim();
                final String username = usernameText.getText().toString();
                final String email = emailText.getText().toString().trim();
                final String password = passwordText.getText().toString();
                String password2 = password2Text.getText().toString();

                if(!fullName.isEmpty() && !username.trim().isEmpty() && !email.trim().isEmpty() && !password.trim().isEmpty()) {
                    if(password.equals(password2)) {
                        ProfileActivity.showPrivacyNotice(RegisterDoor43Activity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RegisterDoor43Task task = new RegisterDoor43Task(username, password, fullName, email);
                                showProgressDialog();
                                task.addOnFinishedListener(RegisterDoor43Activity.this);
                                TaskManager.addTask(task, RegisterDoor43Task.TASK_ID);
                            }
                        });
                    } else {
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), R.string.passwords_mismatch, Snackbar.LENGTH_SHORT);
                        ViewUtil.setSnackBarTextColor(snack, getResources().getColor(R.color.light_primary_text));
                        snack.show();
                    }
                } else {
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), R.string.complete_required_fields, Snackbar.LENGTH_SHORT);
                    ViewUtil.setSnackBarTextColor(snack, getResources().getColor(R.color.light_primary_text));
                    snack.show();
                }
            }
        });

        findViewById(R.id.privacy_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.showPrivacyNotice(RegisterDoor43Activity.this, null);
            }
        });


        RegisterDoor43Task task = (RegisterDoor43Task) TaskManager.getTask(RegisterDoor43Task.TASK_ID);
        if(task != null) {
            showProgressDialog();
            task.addOnFinishedListener(this);
        }
    }

    private void showProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }

        progressDialog.setTitle(getResources().getString(R.string.logging_in));
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void onFinished(ManagedTask task) {
        TaskManager.clearTask(task);

        if(progressDialog != null) {
            progressDialog.dismiss();
        }

        User user = ((RegisterDoor43Task)task).getUser();
        if(user != null) {
            // save gogs user to profile
            Profile profile = new Profile(user.fullName);
            profile.gogsUser = user;
            //AppContext.setProfile(profile);
            finish();
        } else {
            String error =((RegisterDoor43Task)task).getError();
            error = error == null ? getResources().getString(R.string.registration_failed) : error;
            // registration failed
            CustomAlertDialog.Create(this)
                    .setTitle(R.string.error)
                    .setMessage(error)
                    .setPositiveButton(R.string.label_ok, null)
                    .show("registration_failed");
        }
    }
}
