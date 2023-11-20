package com.example.testcrudmysql.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testcrudmysql.API.RegisterAPI;
//import com.example.testcrudmysql.Class.Value;
import com.example.testcrudmysql.Class.Value;
import com.example.testcrudmysql.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://retrofit-api.inlingua.co.id/";
//    public RadioButton radioSexButton;
    public ProgressDialog progress;
//    public RadioGroup radioGroup;
//    public EditText editTextNPM;
//    public EditText editTextNama;
//    public EditText editTextKelas;
//    public Button buttonDaftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioSesi);
        EditText editTextNPM = (EditText) findViewById(R.id.editTextNPM);
        EditText editTextNama = (EditText) findViewById(R.id.editTextNama);
        EditText editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        Button buttonDaftar = (Button) findViewById(R.id.buttonDaftar);
//        ProgressDialog progress;


        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String URL = "http://192.168.20.25/Java_Dev/Retrofit_CRUD/";
                //membuat progress dialog
                progress = new ProgressDialog(MainActivity.this);
                progress.setCancelable(false);
                progress.setMessage("Loading ...");
//                progress.show();

                //mengambil data dari edittext
                String npm = editTextNPM.getText().toString();
                String nama = editTextNama.getText().toString();
                String kelas = editTextKelas.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                // mencari id radio button
                RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                String sesi = radioSexButton.getText().toString();
//                String sesi = "19.30";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RegisterAPI api = retrofit.create(RegisterAPI.class);
                Call<Value> call = api.daftar(npm, nama, kelas, sesi);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response != null && response.code() == 200){
                            String value = response.body().getMessage();
                            String message = response.body().getMessage();
                            progress.dismiss();
                            if (value.equals("1")) {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else if (!response.isSuccessful()) {
                            try {
                                Toast.makeText(MainActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button buttonLihat = (Button) findViewById(R.id.buttonLihat);
        buttonLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(intent);
            }
        });




    }
}