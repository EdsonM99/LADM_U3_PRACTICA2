package mx.edu.ittepic.jjro.ladm_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var idNotas = ArrayList<Int>()
    var BDR = FirebaseFirestore.getInstance()
    var listaID = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mostrarNotas()
        BDR.collection("nota")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    error.message!!
                    return@addSnapshotListener
                }
                listaID.clear()
                for(document in querySnapshot!!){
                    listaID.add(document.id.toString())
                }
            }
        sincronizar.setOnClickListener {
            eliminarID()
            var resp = Nota(this).sincronizar()
            if(resp==true){
                Toast.makeText(this,"SINCRONIZACION EXITOSA",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO HAY DATA",Toast.LENGTH_LONG).show()
            }
        }
        nuevanota.setOnClickListener {
            val intento = Intent(this, ActivityNuevaNota::class.java)
            startActivity(intento)
        }
    }
    fun eliminarID(){
        (0..listaID.size-1).forEach {
            val i = listaID.get(it)
            eliminarids(i)
        }
    }
    fun eliminarids(id: String){
        BDR.collection("nota")
            .document(id)
            .delete()
    }
    fun mostrarNotas(){
        val arregloNotas = Nota(this).consultar()
        listanotas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloNotas)
        idNotas.clear()
        idNotas = Nota(this).obtenerIDs()
        activarEvento(listanotas)
    }
    private fun activarEvento(listanotas: ListView) {
        listanotas.setOnItemClickListener { adapterView, view, indiceSeleccionado, l ->
            val idSeleccionado = idNotas[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUE DESEA HACER CON LA NOTA?")
                .setPositiveButton("EDITAR"){d, i->actualizar(idSeleccionado)}
                .setNegativeButton("ELIMINAR"){d, i-> eliminar(idSeleccionado)}
                .setNeutralButton("CANCELAR"){d, i->}
                .show()
        }
    }
    private fun eliminar(idSeleccionado: Int) {
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR ID: ${idSeleccionado}?")
            .setPositiveButton("SI"){d,i->
                val resultado = Nota(this).eliminar(idSeleccionado)
                if(resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO", Toast.LENGTH_LONG).show()
                    mostrarNotas()
                }else{
                    Toast.makeText(this,"ERROR NO SE LOGRO ELIMINAR", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i->
                d.cancel()
            }
            .show()
    }
    private fun actualizar(idSeleccionado: Int) {
        val intento = Intent(this, ActivityEditarNota::class.java)
        intento.putExtra("idActualizar",idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(this).setMessage("DESEAS ACTUALIZAR LA LISTA?")
            .setPositiveButton("SI"){d,i->mostrarNotas()}
            .setNegativeButton("NO"){d,i->d.cancel()}
            .show()
    }
}