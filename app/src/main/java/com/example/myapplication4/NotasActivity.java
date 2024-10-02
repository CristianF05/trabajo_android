package com.example.myapplication4;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotasActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private TableLayout tableLayout;
    private Button buttonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        dbHelper = new DBHelper(this);
        tableLayout = findViewById(R.id.tableLayout);
        buttonVolver = findViewById(R.id.buttonVolver);

        mostrarCalificaciones();

        buttonVolver.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y vuelve a MainActivity
        });
    }

    private void mostrarCalificaciones() {
        Cursor cursor = dbHelper.obtenerCalificaciones();

        if (cursor.moveToFirst()) {
            do {
                String alumno = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ALUMNO));
                String calificacion1 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION1));
                String calificacion2 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION2));
                String calificacion3 = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CALIFICACION3));

                TableRow row = new TableRow(this);
                TextView tvAlumno = new TextView(this);
                TextView tvCalificaciones = new TextView(this);

                tvAlumno.setText(alumno);
                tvCalificaciones.setText(calificacion1 + ", " + calificacion2 + ", " + calificacion3);

                row.addView(tvAlumno);
                row.addView(tvCalificaciones);

                tableLayout.addView(row);
            } while (cursor.moveToNext());
        } else {
            // Si no hay calificaciones, puedes mostrar un mensaje o dejar la tabla vacía
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("No hay calificaciones disponibles");
            tableLayout.addView(tvEmpty);
        }
        cursor.close();
    }
}


