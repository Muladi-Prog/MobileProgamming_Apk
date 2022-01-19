package com.book.rebo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

public class SignInActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText DateOfBirth,phone_number_et,username_et,password_et,confirm_password_et,address_et;
    String DOB,phone_number,username,password,confirm_password,address;
    Intent intent;
    Button sign,backtoHome;
    Vector<User> userList = new Vector<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        DateOfBirth = (EditText) findViewById(R.id.DOB);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                DOB = month+"/"+day+"/"+year;
                updateLabel();
            }
        };
        DateOfBirth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignInActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        backtoHome = (Button)findViewById(R.id.backLogin);
        backtoHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),QuickStartActivity.class);
                startActivity(back);
            }
        });

        sign = (Button) findViewById(R.id.create_account);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone_number_et = (EditText) findViewById(R.id.phoneNumber);
                phone_number = phone_number_et.getText().toString();
                username_et = (EditText) findViewById(R.id.userName);
                username = username_et.getText().toString();
                password_et = (EditText) findViewById(R.id.password);
                password = password_et.getText().toString();
                confirm_password_et =(EditText) findViewById(R.id.confirm_password);
                confirm_password = confirm_password_et.getText().toString();
                address_et = (EditText) findViewById(R.id.address);
                address = address_et.getText().toString();
                RadioGroup gender = (RadioGroup) findViewById(R.id.gender);
                int id = gender.getCheckedRadioButtonId();
                String gender_info = "Male";
                switch (id){
                    case R.id.gender_male:
                        gender_info =((RadioButton)findViewById(id)).getText().toString();
                        break;
                    case R.id.gender_female:
                        gender_info =((RadioButton)findViewById(id)).getText().toString();
                        break;
                }
                System.out.println("Password: "+password + "Confirm_pasword: "+confirm_password);
                if(phone_number.length() >= 12 &&phone_number.startsWith("08")&& username.length() >=5 && password.equals(confirm_password) && address.length()>=5 ){
//            pass
                    System.out.println("pass");
                    Intent intent = new Intent(getApplicationContext(),QuickStartActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    intent.putExtra("phoneNumber",phone_number);
                    intent.putExtra("address",address);
                    intent.putExtra("DOB",DOB);
                    intent.putExtra("gender",gender_info);
                    userList.add(new User(username,phone_number,password,gender_info,DOB,address));
                    SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(userList);
                    editor.putString("Data SignIn",json);
                    editor.apply();
                    Toast.makeText(SignInActivity.this,"Sign In Success",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }else{
                    if(phone_number.length() <12||!phone_number.startsWith("08")){
                        Toast.makeText(SignInActivity.this,"Phone Number must be have 12 number and start with 08",Toast.LENGTH_SHORT).show();
                    }else if(username.length() <5){
                        Toast.makeText(SignInActivity.this,"Username must have min 5 char",Toast.LENGTH_SHORT).show();
                    }else if(address.length()<5){
                        Toast.makeText(SignInActivity.this,"Address at least have 5 char",Toast.LENGTH_SHORT).show();
                    }else if(!password.equals(confirm_password)){
                        Toast.makeText(SignInActivity.this,"Password and confirm password should be same",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }
    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        DateOfBirth.setText(dateFormat.format(myCalendar.getTime()));
    }

}
