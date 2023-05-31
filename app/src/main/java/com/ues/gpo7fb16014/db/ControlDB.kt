package com.ues.gpo7fb16014.db

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ControlDB(ctx: Context){
    private var context: Context? = null
    private var dbHelper: DatabaseHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        context = ctx
        dbHelper = DatabaseHelper(context)
    }

    private class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, DATABASE, null, VERSION) {

        companion object {
            private const val DATABASE = "revision_db.sqlite3"
            private const val VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
            try {
                db.execSQL("CREATE TABLE alumno (carnet TEXT PRIMARY KEY, nombre TEXT NOT NULL, carrera TEXT NOT NULL);")
                db.execSQL("CREATE TABLE local (cod_local TEXT NOT NULL PRIMARY KEY);")
                db.execSQL("CREATE TABLE motivos_cambio (cod_motivo TEXT PRIMARY KEY, nombre TEXT NOT NULL);")
                db.execSQL("CREATE TABLE materia (cod_materia TEXT PRIMARY KEY, nombre TEXT NOT NULL, area TEXT NOT NULL);")
                db.execSQL("CREATE TABLE ciclo (cod_ciclo TEXT PRIMARY KEY, anio TEXT NOT NULL);")
                db.execSQL("CREATE TABLE docente (cod_docente TEXT PRIMARY KEY, nombre TEXT NOT NULL);")
                db.execSQL("CREATE TABLE evaluacion (" +
                        "num INTEGER PRIMARY KEY, " +
                        "tipo TEXT NOT NULL, " +
                        "cod_materia TEXT NOT NULL, " +
                        "cod_docente TEXT NOT NULL, " +
                        "cod_ciclo TEXT NOT NULL, " +
                        "fecha_realizacion TEXT NOT NULL," +
                        "fecha_publicacion TEXT NOT NULL, " +
                        "cant_alumnos_eva INTEGER DEFAULT 0);")
                db.execSQL("CREATE TABLE revision (" +
                        "numrev1 INTEGER PRIMARY KEY, " +
                        "num_evaluacion TEXT NOT NULL, " +
                        "carnet TEXT NOT NULL, " +
                        "cod_local TEXT NOT NULL, " +
                        "cod_motivo TEXT NOT NULL, " +
                        "nota_nueva REAL NOT NULL, " +
                        "nota_original REAL NOT NULL, " +
                        "observ TEXT NOT NULL, " +
                        "asistencia TINYINT NOT NULL);")
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        }
    }

    @Throws(SQLException::class)
    fun abrir() {
        db = dbHelper!!.writableDatabase
        return
    }

    fun cerrar() {
//        dbHelper!!.close()
    }

    fun llenarBD(): String{
        abrir()
        db?.let { db ->
            db.execSQL("DELETE FROM alumno")
            db.execSQL("DELETE FROM local")
            db.execSQL("DELETE FROM motivos_cambio")
            db.execSQL("DELETE FROM materia")
            db.execSQL("DELETE FROM ciclo")
            db.execSQL("DELETE FROM docente")
            db.execSQL("DELETE FROM evaluacion")
            db.execSQL("DELETE FROM revision")

            db.execSQL("INSERT INTO alumno (carnet, nombre, carrera) VALUES ('123456', 'Juan Pérez', 'Ingeniería Informática');")
            db.execSQL("INSERT INTO alumno (carnet, nombre, carrera) VALUES ('789012', 'María García', 'Medicina');")
            db.execSQL("INSERT INTO alumno (carnet, nombre, carrera) VALUES ('345678', 'Carlos Rodríguez', 'Administración de Empresas');")
        }
        cerrar()
        return "Guardo Correctamente"
    }

//    private fun insertar(cliente: Cliente) {}
}