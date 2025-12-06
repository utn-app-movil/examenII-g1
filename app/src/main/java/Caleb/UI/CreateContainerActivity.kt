package Caleb.UI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Caleb.ServiceC.ApiServiceCaleb
import Caleb.modelC.ContainerCreateRequest
import Caleb.modelC.ContainerCreateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateContainerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cr.ac.utn.appmovil.containers.R.layout.activity_create_container_caleb)

        val etId = findViewById<EditText>(cr.ac.utn.appmovil.containers.R.id.etContainerId)
        val etProduct = findViewById<EditText>(cr.ac.utn.appmovil.containers.R.id.etContainerProduct)
        val btnCreate = findViewById<Button>(cr.ac.utn.appmovil.containers.R.id.btnCreateContainer)

        btnCreate.setOnClickListener {
            val id = etId.text?.toString()?.trim().orEmpty()
            val product = etProduct.text?.toString()?.trim().orEmpty()

            if (id.isEmpty() || product.isEmpty()) {
                Toast.makeText(this, "Ingrese id y product", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createContainer(id, product)
        }
    }

    private fun createContainer(id: String, product: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiServiceCaleb.api.createContainer(ContainerCreateRequest(id, product))
                }
                if (response.isSuccessful) {
                    val body: ContainerCreateResponse? = response.body()
                    if (body?.data != null) {
                        Toast.makeText(this@CreateContainerActivity, body.message ?: "Contenedor creado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@CreateContainerActivity, body?.message ?: "No se pudo crear el contenedor", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@CreateContainerActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CreateContainerActivity, "Fallo de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

