package com.sashatourist.sashaturistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText correo, contrasena;
    Button btnIniciar, btnRegistrar;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correoLogin);
        contrasena = findViewById(R.id.contrasenaLogin);

        btnIniciar = findViewById(R.id.btnIniciaSesion);
        btnRegistrar = findViewById(R.id.btnRegistrate);

        btnIniciar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIniciaSesion:
                String user = correo.getText().toString();
                String pass = contrasena.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(this, "DEBE LLENAR LOS CAMPOS", Toast.LENGTH_LONG).show();
                    break;
                }
                else if(dao.login(user, pass) == 1) {//Lo encuentra en la base de datos
                    Usuario ux = dao.getUsuario(user, pass);
                    Toast.makeText(this, "INICIO DE SESION EXITOSO", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(MainActivity.this, Principal.class);
                    i2.putExtra("Id", ux.getId());
                    startActivity(i2);
                    finish();
                }
                else {
                    Toast.makeText(this, "ERROR EN CORREO O CONTRASEÑA", Toast.LENGTH_LONG).show();
                    break;
                }
                break;
            case R.id.btnRegistrate:
                Intent i = new Intent(MainActivity.this, Registrar.class);
                startActivity(i);
                finish();
                break;
        }
    }
}