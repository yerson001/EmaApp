package com.yrsn.emaraninventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class agregar extends AppCompatActivity {

    private EditText t_nombre,t_edad;
    private Button b_insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        t_nombre = findViewById(R.id.ednombre);
        t_edad = findViewById(R.id.eddad);
        b_insertar = findViewById(R.id.b_button);
        b_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar_datos();
            }
        });

    }

    private void insertar_datos(){
        final String nombre = t_nombre.getText().toString().trim();
        final String edad = t_edad.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando");

        if(nombre.isEmpty()){
            Toast.makeText(this,"Ingrese nombre",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(edad.isEmpty()){
            Toast.makeText(this,"Ingrese edad",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/android/init.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(agregar.this, "datos insertados", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(agregar.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(agregar.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String,String> getParams() throws AuthFailureError{

                    Map<String,String> params = new HashMap<>();

                    params.put("nombre",nombre);
                    params.put("edad",edad);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(agregar.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}