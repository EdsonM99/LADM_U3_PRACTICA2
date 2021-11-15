package mx.edu.ittepic.jjro.ladm_practica2

import android.content.ContentValues
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class Nota(p: Context) {
    var BDR = FirebaseFirestore.getInstance()
    var titulo = ""
    var descripcion = ""
    var hora = ""
    var fecha = ""
    val pnt = p

    fun insertar() : Boolean{
        val tablaNota = BD(pnt, "NOTAS", null,1).writableDatabase

        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("descripcion",descripcion)
        datos.put("hora",hora)
        datos.put("fecha",fecha)

        val resultado = tablaNota.insert("NOTA", null, datos)
        if(resultado == -1L){
            return false
        }
        return true
    }
    fun sincronizar() : Boolean{
        val tablaNota = BD(pnt, "NOTAS", null,1).readableDatabase
        val cursor = tablaNota.query("NOTA", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var datos = hashMapOf(
                    "titulo" to cursor.getString(1).toString(),
                    "descripcion" to cursor.getString(2).toString(),
                    "hora" to cursor.getString(3).toString(),
                    "fecha" to cursor.getString(4).toString()
                )
                BDR.collection("nota").add(datos)
            }while(cursor.moveToNext())
        }else{
            return false
        }
        return true
    }
    fun consultar() : ArrayList<String>{
        val tablaNota = BD(pnt, "NOTAS", null,1).readableDatabase
        val resultado = ArrayList<String>()
        val cursor = tablaNota.query("NOTA", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                var dato = "${cursor.getString(1).uppercase()}:"+"\n"+cursor.getString(2)
                resultado.add(dato)
            }while(cursor.moveToNext())
        }else{
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }
    fun obtenerIDs() : ArrayList<Int>{
        val tablaNota = BD(pnt, "NOTAS", null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaNota.query("NOTA", arrayOf("*"),null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                resultado.add(cursor.getInt(0))
            }while(cursor.moveToNext())
        }
        return resultado
    }
    fun eliminar(idEliminar:Int) : Boolean {
        val tablaNota = BD(pnt, "NOTAS", null,1).writableDatabase
        val resultado = tablaNota.delete("NOTA","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }
    fun consultar(idABuscar:String) : Nota{
        val tablaNota = BD(pnt, "NOTAS", null,1).writableDatabase
        val cursor = tablaNota.query("NOTA", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val nota = Nota(MainActivity())
        if(cursor.moveToFirst()){
            nota.titulo = cursor.getString(1)
            nota.descripcion = cursor.getString(2)
            nota.hora = cursor.getString(3)
            nota.fecha = cursor.getString(4)
        }
        return nota
    }
    fun actualizar(idActualizar:String): Boolean{
        val tablaNota = BD(pnt, "NOTAS", null,1).readableDatabase
        val datos = ContentValues()
        datos.put("titulo",titulo)
        datos.put("descripcion",descripcion)
        datos.put("hora",hora)
        datos.put("fecha",fecha)

        val resultado = tablaNota.update("NOTA",datos, "ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }
}