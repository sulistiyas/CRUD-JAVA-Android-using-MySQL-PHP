package com.example.testcrudmysql.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testcrudmysql.API.RegisterAPI;
import com.example.testcrudmysql.Class.Value;
import com.example.testcrudmysql.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    public static final String URL = "https://retrofit-api.inlingua.co.id/";
    private RadioButton radioSexButton;
    private ProgressDialog progress;

    public RadioGroup radioGroup;
    public RadioButton radioButtonPagi;
    public RadioButton radioButtonSiang;
    public EditText editTextNPM;
    public EditText editTextNama;
    public EditText editTextKelas;

    public Button buttonUbah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        radioGroup = findViewById(R.id.radioSesi);
        radioButtonPagi = findViewById(R.id.radioPagi);
        radioButtonSiang = findViewById(R.id.radioSiang);
        editTextNPM = findViewById(R.id.editTextNPM);
        editTextNama = findViewById(R.id.editTextNama);
        editTextKelas = findViewById(R.id.editTextKelas);
        buttonUbah = findViewById(R.id.buttonUbah);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        Intent intent = getIntent();
        String npm = intent.getStringExtra("npm");
        String nama = intent.getStringExtra("nama");
        String kelas = intent.getStringExtra("kelas");
        String sesi = intent.getStringExtra("sesi");

        editTextNPM.setText(npm);
        editTextNama.setText(nama);
        editTextKelas.setText(kelas);

        if (sesi.equals("Pagi (09.00-11.00 WIB)")) {
            radioButtonPagi.setChecked(true);
        } else {
            radioButtonSiang.setChecked(true);
        }



        buttonUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(UpdateActivity.this);
                progress.setCancelable(false);
                progress.setMessage("Loading ...");
                progress.show();

                //mengambil data dari edittext
                String npm = editTextNPM.getText().toString();
                String nama = editTextNama.getText().toString();
                String kelas = editTextKelas.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                // mencari id radio button
                radioSexButton = (RadioButton) findViewById(selectedId);
                String sesi = radioSexButton.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RegisterAPI api = retrofit.create(RegisterAPI.class);
                Call<Value> call = api.ubah(npm, nama, kelas, sesi);

                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        if (response != null && response.code() == 200){
                            String value = response.body().getValue();
                            String message = response.body().getMessage();
                            progress.dismiss();
                            if (value.equals("1")) {
                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else if (!response.isSuccessful()) {
                            try {
                                Toast.makeText(UpdateActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                Toast.makeText(UpdateActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                                throw new RuntimeException(e);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        t.printStackTrace();
                        progress.dismiss();
                        Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.action_delete) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Peringatan");
            alertDialogBuilder
                    .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                    .setCancelable(false)
                    .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String npm = editTextNPM.getText().toString();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            RegisterAPI api = retrofit.create(RegisterAPI.class);
                            Call<Value> call = api.hapus(npm);
                            call.enqueue(new Callback<Value>() {
                                @Override
                                public void onResponse(Call<Value> call, Response<Value> response) {
                                    String value = response.body().getValue();
                                    String message = response.body().getMessage();
                                    if (value.equals("1")) {
                                        Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Value> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }
}