package cr.ac.utn.appmovil.containers.pau_view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R
import Service.pau_Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.pau_CreateContainerRequest
import util.util

class pau_CreateContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pau_create_conatiner)

        val pau_code_edittext = findViewById<EditText>(R.id.pau_code_edittext_dialog)
        val pau_desc_edittext = findViewById<EditText>(R.id.pau_description_txt)
        val pau_create_button = findViewById<Button>(R.id.pau_create_btn)

        pau_create_button.setOnClickListener {
            val code = pau_code_edittext.text.toString().trim()
            val desc = pau_desc_edittext.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "El código es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pau_createContainer(code, desc)
        }
    }

    private fun pau_createContainer(code: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = pau_Repository.pau_createContainer(pau_CreateContainerRequest(code, description))
                withContext(Dispatchers.Main) {
                    if (response.pau_responseCode == "201") {
                        util.showDialogCondition(
                            this@pau_CreateContainerActivity,
                            "Éxito",
                            "Contenedor creado correctamente",
                            "OK",
                            "Cancelar",
                            { finish() },
                            {}
                        )
                    } else {
                        util.showDialogCondition(
                            this@pau_CreateContainerActivity,
                            "Error",
                            response.pau_message,
                            "OK", "Cancelar", {}, {}
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    util.showDialogCondition(
                        this@pau_CreateContainerActivity,
                        "Error",
                        e.message ?: "Error de conexión",
                        "OK", "Cancelar", {}, {}
                    )
                }
            }
        }
    }
}