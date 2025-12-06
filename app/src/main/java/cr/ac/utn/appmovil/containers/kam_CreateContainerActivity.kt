package cr.ac.utn.appmovil.containers

import Service.kam_RetrofitClient
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class kam_CreateContainerActivity : AppCompatActivity() {

    private lateinit var etContainerId: TextInputEditText
    private lateinit var etProduct: TextInputEditText
    private lateinit var btnCreate: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kam_create_container)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etContainerId = findViewById(R.id.kam_et_container_id)
        etProduct = findViewById(R.id.kam_et_product)
        btnCreate = findViewById(R.id.kam_btn_create)
        progressBar = findViewById(R.id.kam_progress_bar)
    }

    private fun setupListeners() {
        btnCreate.setOnClickListener {
            val containerId = etContainerId.text.toString().trim()
            val product = etProduct.text.toString().trim()

            if (containerId.isEmpty() || product.isEmpty()) {
                Toast.makeText(this, R.string.kam_fill_all_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createContainer(containerId, product)
        }
    }

    private fun createContainer(containerId: String, product: String) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val containerData = mapOf(
                    "id" to containerId,
                    "product" to product
                )

                val response = kam_RetrofitClient.apiService.createContainer(containerData)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        if (apiResponse != null && apiResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@kam_CreateContainerActivity,
                                R.string.kam_create_success,
                                Toast.LENGTH_SHORT
                            ).show()

                            // Clear fields
                            etContainerId.text?.clear()
                            etProduct.text?.clear()

                            // Go back
                            finish()
                        } else {
                            Toast.makeText(
                                this@kam_CreateContainerActivity,
                                apiResponse?.message ?: getString(R.string.kam_create_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@kam_CreateContainerActivity,
                            R.string.kam_create_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@kam_CreateContainerActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        btnCreate.isEnabled = !show
    }
}