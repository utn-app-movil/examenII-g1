package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cr.ac.utn.appmovil.containers.models.bran_CreateContainerRequest
import kotlinx.coroutines.launch

class bran_CreateContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bran_activity_create_container)

        val contenedorIdEditText = findViewById<EditText>(R.id.editTextContenedorId)
        val productoEditText = findViewById<EditText>(R.id.editTextProducto)
        val crearButton = findViewById<Button>(R.id.buttonCrear)

        crearButton.setOnClickListener {
            val contenedorId = contenedorIdEditText.text.toString()
            val producto = productoEditText.text.toString()

            if (contenedorId.isNotEmpty() && producto.isNotEmpty()) {
                val createRequest = bran_CreateContainerRequest(contenedorId, producto)
                crearContenedor(createRequest)
            } else {
                Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun crearContenedor(createRequest: bran_CreateContainerRequest) {
        lifecycleScope.launch {
            try {
                val response = bran_RetrofitClient.apiService.createContainer(createRequest)
                if (response.isSuccessful) {
                    Toast.makeText(this@bran_CreateContainerActivity, "Contenedor creado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@bran_CreateContainerActivity, "Error al crear el contenedor: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@bran_CreateContainerActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}