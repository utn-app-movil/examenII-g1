package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class nid_ContenedoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nid_contenedores)

        // BOTÓN PARA PASAR A LISTA DE CONTENEDORES
        val btnCreate = findViewById<Button>(R.id.nid_btnCreateContainer)

        btnCreate.setOnClickListener {
            val intent = Intent(this, nid_ListContActivity::class.java)
            startActivity(intent)
        }
    }
}
