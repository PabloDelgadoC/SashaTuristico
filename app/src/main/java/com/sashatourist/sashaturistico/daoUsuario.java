package com.sashatourist.sashaturistico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.UrlQuerySanitizer;

import java.util.ArrayList;

public class daoUsuario {

    Context context;
    Usuario usuario;
    SQLiteDatabase sql;
    ArrayList<Usuario> listaUsuarios;
    String db = "dbSashaTuristico.sqlite";
    String tblUsuario = "CREATE TABLE IF NOT EXISTS usuario(id integer primary key autoincrement, correo text, nombres text, apellidos text, direccion text, contrasena text)";

    public daoUsuario(Context context) {
        this.context = context;
        sql = context.openOrCreateDatabase(db, context.MODE_PRIVATE, null);
        sql.execSQL(tblUsuario);
        this.usuario = new Usuario();
    }

    public boolean insertarUsuario(Usuario usuario) {
        if( buscar(usuario.getCorreo()) == 0 ) {
            ContentValues cv = new ContentValues();
            cv.put("correo", usuario.getCorreo());
            cv.put("nombres", usuario.getNombres());
            cv.put("apellidos", usuario.getApellidos());
            cv.put("direccion", usuario.getDireccion());
            cv.put("contrasena", usuario.getContrasena());
            return (sql.insert("usuario", null, cv) > 0);
        }
        else {
            return false;
        }
    }

    //Verificador de correo para que no haya doble registro en la base de datos
    public int buscar(String usuario) {
        int res = 0;
        this.listaUsuarios = selectUsuarios();
        for (Usuario u: this.listaUsuarios) {
            if(u.getCorreo().equals(usuario)) {
                res++;
            }
        }
        return res;
    }

    public ArrayList<Usuario> selectUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.clear();
        Cursor cr = sql.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(cr.getInt(0));
                usuario.setCorreo(cr.getString(1));
                usuario.setNombres(cr.getString(2));
                usuario.setApellidos(cr.getString(3));
                usuario.setDireccion(cr.getString(4));
                usuario.setContrasena(cr.getString(5));
                usuarios.add(usuario);
            } while(cr.moveToNext());
        }
        return usuarios;
    }

    public int login(String user, String contrasena) {
        int res = 0;
        Cursor cr = sql.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if(cr.getString(1).equals(user) && cr.getString(5).equals(contrasena)) {
                    res++;
                }
            } while(cr.moveToNext());
        }
        return res;
    }

    //
    public Usuario getUsuario(String u, String p) {
        this.listaUsuarios = selectUsuarios();
        for (Usuario us: this.listaUsuarios) {
            if(us.getCorreo().equals(u) && us.getContrasena().equals(p)) {
                return us;
            }
        }
        return null;
    }

    public Usuario getUsuarioById(int id) {
        this.listaUsuarios = selectUsuarios();
        for(Usuario us: this.listaUsuarios){
            if(us.getId() == id){
                return us;
            }
        }
        return null;
    }
}
