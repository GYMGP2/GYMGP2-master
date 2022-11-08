package com.example.gymgp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.EGLExt;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gymgp2.Contextos.Api;
import com.example.gymgp2.Contextos.Pais;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PICK_IMAGE = 200;
    static final int peticion_captura_imagen =101;
    static final int peticion_acceso_cam=100;
    Uri imageUri;
    ImageView btnatras;
    ImageView foto;
    Button btntomarfoto, btngaleria;
    Bitmap imagen;
    TextView txtfecha, txtpeso;
    Spinner sppais;
    ArrayList<String> arrayPaises;
    Pais pais;
    int codigopais;
    private DatePickerDialog.OnDateSetListener fechalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        casteo();
txtfecha.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                Registro.this,
                android.R.style.Theme_Holo_Light,
                fechalist,
                anio,mes,dia);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
});
        fechalist = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                mes += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + dia  + "/" + mes + "/" + anio);

                String fecha =  dia  + "/" + mes + "/" + anio;
                txtfecha.setText(fecha);
            }
        };

        txtpeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NumberPicker pes = new NumberPicker(
                   Registro.this);
                pes.setMinValue(1);
                pes.setMaxValue(300);
                pes.setValue(160);
                pes.setBackgroundColor(Color.LTGRAY);
                txtpeso.setText(""+pes.getValue());
                NumberPicker.OnValueChangeListener changedvalue = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        txtpeso.setText(""+i1);
                    }
                };

                pes.setOnValueChangedListener(changedvalue);
                AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth).setView(pes);
                builder.setTitle("Peso");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            builder.show();
            }
        });

        SpinnerPaises();
        sppais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cadena = adapterView.getSelectedItem().toString();

                codigopais = Integer.valueOf(extrerCodigo(cadena).toString().replace("]", "").replace("[", ""));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),Login.class);
                startActivity(intencion);
            }
        });

        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            abrirGaleria();
            }
        });

        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            permisos();
            }
        });
        }

    public void casteo() {

        btntomarfoto = (Button) findViewById(R.id.btntomarfoto);
        btngaleria = (Button) findViewById(R.id.btngaleria);
        btnatras = (ImageView) findViewById(R.id.btnatras);
        foto = (ImageView) findViewById(R.id.foto);
        txtfecha = (TextView) findViewById(R.id.txtfecha);
        txtpeso = (TextView) findViewById(R.id.txtpeso);
        sppais = (Spinner) findViewById(R.id.spinnerpais);
    }

    public void abrirGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }
    private void permisos() {

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},peticion_acceso_cam);
        }else{
            tomarfoto();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == peticion_acceso_cam)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                tomarfoto();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Se necesitan permisos",Toast.LENGTH_LONG).show();
        }
    }
    private void tomarfoto(){
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takepic,peticion_captura_imagen);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //obtener la iamgen por el almacenamiento interno
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            imageUri = data.getData();
            foto.setImageURI(imageUri);
        }
        //obtener la iamgen por la camara
        if(requestCode == peticion_captura_imagen)
        {
            Bundle extras = data.getExtras();
            imagen = (Bitmap) extras.get("data");
            foto.setImageBitmap(imagen);
        }
    }
    private void SpinnerPaises(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.EndPointListarPaises,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray contactoArray = jsonObject.getJSONArray( "pais");

                            arrayPaises = new ArrayList<>();
//                            arrayPaises.clear();//limpiar la lista de usuario antes de comenzar a listar
                            for (int i=0; i<contactoArray.length(); i++)
                            {
                                JSONObject RowPais = contactoArray.getJSONObject(i);
                                pais = new Pais(  RowPais.getInt("codigo_pais"),
                                        RowPais.getString("nombre")
                                );

                                arrayPaises.add(pais.getNombre() + " ["+pais.getId()+"]");
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Registro.this, android.R.layout.simple_spinner_dropdown_item, arrayPaises);
                            sppais.setAdapter(adapter);

                        }catch (JSONException ex){
                            Toast.makeText(getApplicationContext(), "mensaje "+ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), "mensaje "+error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    List<Integer> extrerCodigo(String cadena) {
        List<Integer> todo = new ArrayList<Integer>();
        Matcher finder = Pattern.compile("\\d+").matcher(cadena);
        while (finder.find()) {
            todo.add(Integer.parseInt(finder.group()));
        }
        return todo;
    }
}