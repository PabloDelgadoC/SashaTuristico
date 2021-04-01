package com.sashatourist.sashaturistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar extends AppCompatActivity implements View.OnClickListener{

    EditText correo, contrasena, nombres, apellidos, direccion;
    Button registrar;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar);

        correo = findViewById(R.id.txtCorreo);
        contrasena = findViewById(R.id.txtContrasena);
        nombres = findViewById(R.id.txtNombres);
        apellidos = findViewById(R.id.txtApellidos);
        direccion = findViewById(R.id.txtDireccion);

        registrar = findViewById(R.id.btnInsertAction);

        registrar.setOnClickListener(this);

        dao = new daoUsuario(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInsertAction:
                Usuario user = new Usuario();
                user.setNombres(nombres.getText().toString());
                user.setApellidos(apellidos.getText().toString());
                user.setDireccion(direccion.getText().toString());
                user.setCorreo(correo.getText().toString());
                user.setContrasena(contrasena.getText().toString());

                if(!user.isNull()) {
                    Toast.makeText(this, "DEBE COMPLETAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
                    break;
                }
                else if(dao.insertarUsuario(user)) {
                    Toast.makeText(this, "USUARIO REGISTRADO", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "YA EXISTE EL USUARIO", Toast.LENGTH_LONG).show();
                    break;
                }

                Intent i = new Intent(Registrar.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}