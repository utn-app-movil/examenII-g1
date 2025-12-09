package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.appmovil.containers.R.id.containersSc

class luis_containersOptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.luis_activity_maincontainers)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(containersSc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnCreateContainer = findViewById<Button>(R.id.btnCreateContainer)
        btnCreateContainer.setOnClickListener {
            val intent = android.content.Intent(this, luis_createContainerActivity::class.java)
            startActivity(intent)
        }

        val btnListContainers = findViewById<Button>(R.id.btnListContainers)
        btnListContainers.setOnClickListener {
            val intent = android.content.Intent(this, luis_listContainersActivity::class.java)
            startActivity(intent)
        }
    }
}