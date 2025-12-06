package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class bran_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bran_activity_main)

        val btnCrearContenedor = findViewById<Button>(R.id.btn_crear_contenedor)
        btnCrearContenedor.setOnClickListener {
            val intent = Intent(this, bran_CreateContainerActivity::class.java)
            startActivity(intent)
        }

        val btnVerContenedores = findViewById<Button>(R.id.btn_ver_contenedores)
        btnVerContenedores.setOnClickListener {
            val intent = Intent(this, bran_ViewContainersActivity::class.java)
            startActivity(intent)
        }
    }
}