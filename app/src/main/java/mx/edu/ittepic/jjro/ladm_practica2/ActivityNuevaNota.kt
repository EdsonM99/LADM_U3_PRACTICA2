package mx.edu.ittepic.jjro.ladm_practica2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nueva_nota.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityNuevaNota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_nota)

        Crear.setOnClickListener {
            var sdf = SimpleDateFormat("hh:mm a")
            var hora = sdf.format(Date()).toString()
            var sdf2 = SimpleDateFormat("dd/M/yyyy")
            var fecha = sdf2.format(Date()).toString()

            val nota = Nota(this)
            nota.titulo = campoTitulo.text.toString()
            nota.descripcion = campoDescripcion.text.toString()
            nota.hora = hora
            nota. fecha = fecha

            val resultado = nota.insertar()
            if(resultado){
                Toast.makeText(this,"NOTA GUARDADA", Toast.LENGTH_LONG).show()
                campoTitulo.text.clear()
                campoDescripcion.text.clear()
            }else{
                Toast.makeText(this,"ERROR! NO SE GUARDO LA NOTA", Toast.LENGTH_LONG).show()
            }
        }
        Regresar.setOnClickListener {
            val intento = Intent(this, MainActivity::class.java)
            startActivity(intento)
        }
    }
}