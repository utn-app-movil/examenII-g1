package cr.ac.utn.appmovil.containers

import Controller.lau_ContainerController
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.lau_DTOContainers

class lau_createContainer : AppCompatActivity() {
    private lateinit var controller: lau_ContainerController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lau_create_container)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controller = lau_ContainerController(this)

        val txtId = findViewById<EditText>(R.id.lau_container_id)
        val txtProduct = findViewById<EditText>(R.id.lau_product)
        val btnCreate = findViewById<Button>(R.id.lau_btn_save_container)

        btnCreate.setOnClickListener {
            val id = txtId.text.toString()
            val product = txtProduct.text.toString()

            if (id.isEmpty() || product.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createContainer(id, product)
        }
    }
    private fun createContainer(id: String, product: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val dto = lau_DTOContainers(
                    ID = id,
                    Product = product,
                    Technician = "",
                    Date = ""
                )

                val response = controller.createContainer(dto)

                Toast.makeText(this@lau_createContainer, "Container creado", Toast.LENGTH_SHORT).show()

                finish()  // regresar a la lista o pantalla anterior

            } catch (e: Exception) {
                Toast.makeText(this@lau_createContainer, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}