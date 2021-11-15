package mx.edu.ittepic.jjro.ladm_practica2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD (context: Context?,
                   name: String?,
                   factory: SQLiteDatabase.CursorFactory?,
                   version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(p: SQLiteDatabase) {
        p.execSQL("CREATE TABLE NOTA(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITULO VARCHAR(200), DESCRIPCION VARCHAR(800), HORA VARCHAR(200), FECHA VARCHAR(200))")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}