package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import Controller.luis_ContainerController
import kotlinx.coroutines.launch

class luis_createContainerActivity : AppCompatActivity() {

    private lateinit var edtContainerId: EditText
    private lateinit var edtContainerProduct: EditText
    private lateinit var btnCreateContainer: Button
    private lateinit var containerController: luis_ContainerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.luis_activity_createcontainer)

        edtContainerId = findViewById(R.id.edtContainerId)
        edtContainerProduct = findViewById(R.id.edtContainerProduct)
        btnCreateContainer = findViewById(R.id.btnCreateContainer)

        containerController = luis_ContainerController(this)

        btnCreateContainer.setOnClickListener {
            handleCreateContainer()
        }
    }

    private fun handleCreateContainer() {
        val containerId = edtContainerId.text.toString().trim()
        val product = edtContainerProduct.text.toString().trim()

        // basic validation
        if (containerId.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.error_container_id_required),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (product.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.error_product_required),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        btnCreateContainer.isEnabled = false

        lifecycleScope.launch {
            try {
                Toast.makeText(
                    this@luis_createContainerActivity,
                    getString(R.string.message_creating_container),
                    Toast.LENGTH_SHORT
                ).show()

                val container = containerController.createContainer(containerId, product)

                Toast.makeText(
                    this@luis_createContainerActivity,
                    getString(R.string.message_container_created),
                    Toast.LENGTH_LONG
                ).show()

                edtContainerId.setText("")
                edtContainerProduct.setText("")

            } catch (e: Exception) {
                Toast.makeText(
                    this@luis_createContainerActivity,
                    getString(
                        R.string.error_create_container_toast,
                        e.message ?: getString(R.string.error_create_container_fallback)
                    ),
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnCreateContainer.isEnabled = true
                btnCreateContainer.text = getString(R.string.luis_create_container_button)
            }
        }
    }
}
