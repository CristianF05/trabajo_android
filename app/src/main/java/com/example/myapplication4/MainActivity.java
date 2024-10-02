package com.example.myapplication4;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText editTextNombre, editTextCalificacion1, editTextCalificacion2, editTextCalificacion3;
    private Button buttonGuardar, buttonVerNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajustar márgenes para el sistema de ventanas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);
        editTextNombre = findViewById(R.id.Nombre);
        editTextCalificacion1 = findViewById(R.id.Primera_Nota);
        editTextCalificacion2 = findViewById(R.id.Segunda_Nota);
        editTextCalificacion3 = findViewById(R.id.Tercera_Nota);
        buttonGuardar = findViewById(R.id.Guardar);
        buttonVerNotas = findViewById(R.id.button2);

        buttonGuardar.setOnClickListener(v -> guardarCalificacion());
        buttonVerNotas.setOnClickListener(v -> mostrarCalificaciones());
    }

    private void guardarCalificacion() {
        String nombre = editTextNombre.getText().toString().trim();
        String cal1 = editTextCalificacion1.getText().toString().trim();
        String cal2 = editTextCalificacion2.getText().toString().trim();
        String cal3 = editTextCalificacion3.getText().toString().trim();

        if (!nombre.isEmpty() && !cal1.isEmpty() && !cal2.isEmpty() && !cal3.isEmpty()) {
            try {
                float calificacion1 = Float.parseFloat(cal1);
                float calificacion2 = Float.parseFloat(cal2);
                float calificacion3 = Float.parseFloat(cal3);
                dbHelper.agregarCalificacion(nombre, calificacion1, calificacion2, calificacion3);
                Toast.makeText(MainActivity.this, "Calificación guardada", Toast.LENGTH_SHORT).show();
                clearFields();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Ingrese calificaciones válidas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextNombre.setText("");
        editTextCalificacion1.setText("");
        editTextCalificacion2.setText("");
        editTextCalificacion3.setText("");
    }

    private void mostrarCalificaciones() {
        Cursor cursor = dbHelper.obtenerCalificaciones();
        ArrayList<String> calificacionesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String alumno = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ALUMNO));
                String calificacion1 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION1));
                String calificacion2 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION2));
                String calificacion3 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION3));
                calificacionesList.add("Alumno: " + alumno + ", Calificaciones: " + calificacion1 + ", " + calificacion2 + ", " + calificacion3);
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (!calificacionesList.isEmpty()) {
            mostrarDialogoCalificaciones(calificacionesList);
        } else {
            Toast.makeText(this, "No hay calificaciones disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogoCalificaciones(ArrayList<String> calificaciones) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Calificaciones");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calificaciones);
        builder.setAdapter(adapter, null);
        builder.setNegativeButton("Cerrar", null);
        builder.show();
    }
}
