package com.najah.edu.fileuser;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.najah.edu.DatABasE.DB_helper;
import com.najah.edu.activity.MainActivity;
import com.najah.edu.rxloginsignup.R;

public class edit_info extends AppCompatActivity implements View.OnClickListener{
    private DB_helper nn;
    boolean flag_first_name = false;
    boolean flag_last_name = false;
    boolean flag_email_name = false;
    boolean flag_password_name = false;
    boolean flag_confirm_name = false;
    boolean flag_phone_name = false;
    // EditText
    private EditText firstname ;
    private EditText lastname ;
    private EditText email ;
    private EditText password ;
    private EditText password_confirm ;
    private EditText phone ;
    Button btn_update;
    // text input to show error
    private TextInputLayout text_input_email;
    private TextInputLayout text_input_pass;
    private TextInputLayout text_input_first_name;
    private TextInputLayout text_input_last_name;
    private TextInputLayout text_input_phone;
    private TextInputLayout text_input_password_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        nn = new DB_helper(this);
        // get data to this user
        nn.get_info(MainActivity.get_e);
        ////////
        firstname = (EditText)findViewById(R.id.EditFirstName);
        lastname = (EditText)findViewById(R.id.EditLastName);
        email = (EditText)findViewById(R.id.Edit_email);
        password = (EditText)findViewById(R.id.Edit_password);
        password_confirm = (EditText)findViewById(R.id.Edit_password_confirm);
        phone = (EditText)findViewById(R.id.Edit_phone);
        btn_update = (Button) findViewById(R.id.button2);
        btn_update.setOnClickListener(this);
        ///////// show data user
        firstname.setText(DB_helper.get_fname);
        lastname.setText(DB_helper.get_lname);
        password.setText(DB_helper.get_pass);
        password_confirm.setText(DB_helper.get_pass);
        email.setText(DB_helper.get_email);
        phone.setText(DB_helper.get_phone);
        // text input to show error
        text_input_email = (TextInputLayout) findViewById(R.id.text_input_email);
        text_input_pass = (TextInputLayout) findViewById(R.id.text_input_password);
        text_input_first_name = (TextInputLayout) findViewById(R.id.text_input_first_name);
        text_input_last_name = (TextInputLayout) findViewById(R.id.text_input_last_name);
        text_input_phone = (TextInputLayout) findViewById(R.id.text_input_phone);
        text_input_password_confirm = (TextInputLayout) findViewById(R.id.text_input_password_confirm);
        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (firstname.getText().length() == 0){
                    text_input_first_name.setError(getString(R.string.str_enter_first_name));
                    flag_first_name = false;
                }
                else{
                    text_input_first_name.setError(null);
                    flag_first_name = true;
                }

            }
        });
        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lastname.getText().length() == 0){
                    text_input_last_name.setError(getString(R.string.str_enter_last_name));
                    flag_last_name = false;
                }
                else{
                    text_input_last_name.setError(null);
                    flag_last_name = true;
                }

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                int count_dot = 0;
                int count_at = 0;
                int count_space = 0;
                String getemail = email.getText().toString();
                int len = getemail.length();
                for (int i = 0 ; i < len ; i++ ){
                    if (getemail.charAt(i) == '@')
                        count_at ++;
                    if (getemail.charAt(i) == ' ')
                        count_space ++;
                    if (getemail.charAt(i) == '.')
                        count_dot++ ;
                }
                if (count_at == 1 && count_dot == 1 && count_space == 0){

                    String spiltemail[] = getemail.split("@");
                    if (  spiltemail[0] == null || spiltemail[1] == null  ){
                        text_input_email.setError(getString(R.string.str_enter_valid_email));
                        flag_email_name = false;
                    }
                    else if (! spiltemail[1].contains(".") ){
                        text_input_email.setError(getString(R.string.str_enter_valid_email));
                        flag_email_name = false;
                    }
                    else{
                        text_input_email.setError(null);
                        flag_email_name = true;
                    }
                }
                else{
                    text_input_email.setError(getString(R.string.str_enter_valid_email));
                    flag_email_name = false;
                }



            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().length() < 6){
                    text_input_pass.setError(getString(R.string.str_enter_password));
                    flag_password_name = false;
                }
                else{
                    text_input_pass.setError(null);
                    flag_password_name = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password.getText().length() < 6){
                    text_input_first_name.setError(getString(R.string.str_enter_password));
                    flag_password_name = false;
                }
                else{
                    text_input_first_name.setError(null);
                    flag_password_name = true;
                }

            }
        });
        password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!password_confirm.getText().toString().equals(password.getText().toString())){
                    text_input_password_confirm.setError(getString(R.string.str_password_must_be_same));
                    flag_confirm_name = false;
                }
                else{
                    text_input_password_confirm.setError("");
                    flag_confirm_name = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!password_confirm.getText().toString().equals(password.getText().toString())){
                    text_input_password_confirm.setError(getString(R.string.str_password_must_be_same));
                    flag_confirm_name = false;
                }
                else{
                    text_input_first_name.setError("");
                    flag_confirm_name = true;
                }

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phone.getText().toString().length() < 10){
                    text_input_phone.setError(getString(R.string.str_invalid_phone_number));
                    flag_phone_name = false;
                }
                else{
                    text_input_phone.setError(null);
                    flag_phone_name = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phone.getText().toString().length() < 10){
                    text_input_phone.setError(getString(R.string.str_invalid_phone_number));
                    flag_phone_name = false;
                }
                else{
                    text_input_phone.setError(null);
                    flag_phone_name = true;
                }

            }
        });
    }
    private void showToast(String string) {
        Toast.makeText(getBaseContext(),string,Toast.LENGTH_LONG);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if ( id == R.id.button2){
            if (    flag_phone_name == true || flag_confirm_name == true ||
                    flag_password_name == true || flag_email_name == true ||
                    flag_first_name == true || flag_last_name == true ){
                String fname = firstname.getText().toString().trim();
                String lname = lastname.getText().toString().trim();
                String emal = email.getText().toString().trim();
                String phon = phone.getText().toString().trim();
                if ( nn.update_to_table(emal,fname,lname,phon) ) {
                    MainActivity.get_e = emal;
                    showToast(getString(R.string.str_update_success));
                }
                else
                    showToast(getString(R.string.str_update_notsuccess));
            }
            else
                showToast(getString(R.string.str_massge2));
        }
    }
}
