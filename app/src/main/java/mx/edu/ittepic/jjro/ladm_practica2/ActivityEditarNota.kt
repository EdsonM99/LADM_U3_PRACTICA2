package mx.edu.ittepic.jjro.ladm_practica2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_editar_nota.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityEditarNota : AppCompatActivity() {
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_nota)
        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!

        val nota = Nota(this).consultar(id)
        campoEditarTitulo.setText(nota.titulo)
        campoEditarDescripcion.setText(nota.descripcion)

        Actualizar.setOnClickListener {
            val actualizarNota = Nota(this)
            var sdf = SimpleDateFormat("hh:mm a")
            var hora = sdf.format(Date()).toString()
            var sdf2 = SimpleDateFormat("dd/M/yyyy")
            var fecha = sdf2.format(Date()).toString()
            actualizarNota.titulo = campoEditarTitulo.text.toString()
            actualizarNota.descripcion = campoEditarDescripcion.text.toString()
            actualizarNota.hora = hora
            actualizarNota.fecha = fecha

            val resultado = actualizarNota.actualizar(id)
            if(resultado){
                Toast.makeText(this,"EXITO SE ACTUALIZO", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR! NO SE LOGRO ACTUALIZAR",Toast.LENGTH_LONG).show()
            }
        }
        volver.setOnClickListener {
            finish()
        }
    }
}