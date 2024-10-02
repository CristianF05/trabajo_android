package com.example.myapplication4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Calificaciones";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CALIFICACIONES = "calificaciones";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ALUMNO = "alumno";
    public static final String COLUMN_CALIFICACION1 = "calificacion1";
    public static final String COLUMN_CALIFICACION2 = "calificacion2";
    public static final String COLUMN_CALIFICACION3 = "calificacion3";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CALIFICACIONES + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ALUMNO + " TEXT, "
                + COLUMN_CALIFICACION1 + " REAL, "
                + COLUMN_CALIFICACION2 + " REAL, "
                + COLUMN_CALIFICACION3 + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALIFICACIONES);
        onCreate(db);
    }

    // Método para agregar calificaciones
    public void agregarCalificacion(String alumno, float calificacion1, float calificacion2, float calificacion3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ALUMNO, alumno);
        values.put(COLUMN_CALIFICACION1, calificacion1);
        values.put(COLUMN_CALIFICACION2, calificacion2);
        values.put(COLUMN_CALIFICACION3, calificacion3);
        db.insert(TABLE_CALIFICACIONES, null, values);
        db.close();
    }

    // Método para obtener todas las calificaciones
    public Cursor obtenerCalificaciones() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CALIFICACIONES, null);
    }
}

