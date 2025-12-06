package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class nid_LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nid_login)

        val btnLogin = findViewById<Button>(R.id.nid_btnLogin)

        btnLogin.setOnClickListener {
            val intent = Intent(this, nid_ContenedoresActivity::class.java)
            startActivity(intent)
        }
    }
}

