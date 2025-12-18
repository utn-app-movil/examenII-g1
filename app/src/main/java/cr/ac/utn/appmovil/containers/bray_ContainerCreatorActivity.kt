package cr.ac.utn.appmovil.containers

import Service.bray_APIService
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class bray_ContainerCreatorActivity : AppCompatActivity() {

    private lateinit var etContainerId: EditText
    private lateinit var etProduct: EditText
    private lateinit var btnCreateContainer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bray_container_creator)

        etContainerId = findViewById(R.id.etContainerId)
        etProduct = findViewById(R.id.etProduct)
        btnCreateContainer = findViewById(R.id.btnCreateContainer)

        btnCreateContainer.setOnClickListener {
            createContainer()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun createContainer() {
        val id = etContainerId.text.toString().trim()
        val product = etProduct.text.toString().trim()

        if (id.isEmpty() || product.isEmpty()) {
            Toast.makeText(this@bray_ContainerCreatorActivity, getString(R.string.error_complete_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = bray_ContainerRequest(id, product)
                val response = bray_APIService.api.createContainer(request)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val containerResponse = response.body()!!
                        // Checking response code
                        if (containerResponse.responseCode == "SUCESSFUL" || containerResponse.responseCode == "200" || containerResponse.responseCode == "201") {
                            Toast.makeText(
                                this@bray_ContainerCreatorActivity,
                                getString(R.string.container_created_success, containerResponse.message),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                             Toast.makeText(this@bray_ContainerCreatorActivity, containerResponse.message, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(
                            this@bray_ContainerCreatorActivity,
                            getString(R.string.error_http_code_message, response.code(), response.message()),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@bray_ContainerCreatorActivity,
                        getString(R.string.error_connection_general, e.message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}