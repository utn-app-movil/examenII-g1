package cr.ac.utn.appmovil.containers

import Service.sam_APIService
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.sam_CreateContainerRequest

class sam_AddContainerActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtProduct: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sam_add_container)

        txtId = findViewById(R.id.txtContainerId)
        txtProduct = findViewById(R.id.txtProduct)
        btnSave = findViewById(R.id.btnSaveContainer)

        btnSave.setOnClickListener {
            createContainer()
        }
    }

    // Validates fields and creates a new container
    private fun createContainer() {
        val id = txtId.text.toString().trim()
        val product = txtProduct.text.toString().trim()

        // Validaciones
        if (id.isEmpty()) {
            txtId.error = "Ingrese el ID del contenedor"
            return
        }

        if (product.isEmpty()) {
            txtProduct.error = "Ingrese el producto"
            return
        }

        val request = sam_CreateContainerRequest(
            id = id,
            product = product
        )

        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    sam_APIService.api.createContainer(request)
                }

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.responseCode == "SUCCESSFUL") {
                        Toast.makeText(
                            this@sam_AddContainerActivity,
                            body.message,
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    } else {
                        Toast.makeText(
                            this@sam_AddContainerActivity,
                            body?.message ?: "No se pudo crear el contenedor",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@sam_AddContainerActivity,
                        "Error HTTP: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@sam_AddContainerActivity,
                    "Error: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
