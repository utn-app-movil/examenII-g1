package Caleb.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R

class menuCaleb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_caleb)

        val prefs = getSharedPreferences("caleb_prefs", MODE_PRIVATE)
        val user = prefs.getString("user", null)
        val name = prefs.getString("name", null)
        val lastName = prefs.getString("lastName", null)

        val tvUserInfo = findViewById<TextView>(R.id.tvUserInfo)
        val btnOpenCreate = findViewById<Button>(R.id.btnOpenCreateContainer)
        val btnOpenAll = findViewById<Button>(R.id.btnOpenAllContainers)

        if (user != null) {
            val text = getString(R.string.caleb_welcome_format, name ?: "", lastName ?: "", user)
            tvUserInfo.text = text
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            btnOpenCreate.setOnClickListener {
                startActivity(Intent(this, CreateContainerActivity::class.java))
            }
            btnOpenAll.setOnClickListener {
                startActivity(Intent(this, AllContainersActivity::class.java))
            }
        } else {
            tvUserInfo.text = getString(R.string.caleb_no_session)
            Toast.makeText(this, getString(R.string.caleb_no_session), Toast.LENGTH_LONG).show()
            btnOpenCreate.isEnabled = false
            btnOpenAll.isEnabled = false
        }
    }
}