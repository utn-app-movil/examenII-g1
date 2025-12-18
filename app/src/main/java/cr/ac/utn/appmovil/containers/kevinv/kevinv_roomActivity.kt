package cr.ac.utn.appmovil.containers.kevinv

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R
import kotlinx.coroutines.*

class kevinv_roomActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtProduct: EditText
    private lateinit var btnSave: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kevinv_room)

        txtId = findViewById(R.id.kevinv_txtContainerId)
        txtProduct = findViewById(R.id.kevinv_txtProduct)
        btnSave = findViewById(R.id.kevinv_btnSaveContainer)
        progress = findViewById(R.id.kevinv_progressCreate)

        btnSave.setOnClickListener {
            kevinv_saveContainer()
        }
    }

    private fun kevinv_saveContainer() {
        val id = txtId.text.toString().trim()
        val product = txtProduct.text.toString().trim()

        if (id.isEmpty() || product.isEmpty()) {
            Toast.makeText(this, "Container id and product are required", Toast.LENGTH_LONG).show()
            return
        }

        progress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = kevinv_ApiClient.api.kevinv_createContainer(
                    kevinv_CreateContainerRequest(id, product)
                )

                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE

                    if (response.responseCode == "SUCESSFUL") {
                        Toast.makeText(
                            this@kevinv_roomActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                        txtId.setText("")
                        txtProduct.setText("")
                    } else {
                        Toast.makeText(
                            this@kevinv_roomActivity,
                            response.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@kevinv_roomActivity,
                        ex.message ?: "Unexpected error",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
