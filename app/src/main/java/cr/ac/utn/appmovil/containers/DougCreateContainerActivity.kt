package cr.ac.utn.appmovil.containers

import Service.DougAPIClient
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DougContainerRequest

class DougCreateContainerActivity : AppCompatActivity() {

    private lateinit var dougNumberEditText: EditText
    private lateinit var dougSizeSpinner: Spinner
    private lateinit var dougTypeSpinner: Spinner
    private lateinit var dougCreateButton: Button
    private lateinit var dougProgressBar: ProgressBar

    private val dougSizes = arrayOf("20", "40")
    private val dougTypes = arrayOf("Refrigerated", "Dry")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doug_create_container)

        initializeViews()
        setupSpinners()
        setupClickListeners()
    }

    private fun initializeViews() {
        dougNumberEditText = findViewById(R.id.doug_number_edit_text)
        dougSizeSpinner = findViewById(R.id.doug_size_spinner)
        dougTypeSpinner = findViewById(R.id.doug_type_spinner)
        dougCreateButton = findViewById(R.id.doug_create_button)
        dougProgressBar = findViewById(R.id.doug_create_progress_bar)
    }

    private fun setupSpinners() {
        val sizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dougSizes)
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dougSizeSpinner.adapter = sizeAdapter

        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dougTypes)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dougTypeSpinner.adapter = typeAdapter
    }

    private fun setupClickListeners() {
        dougCreateButton.setOnClickListener {
            val number = dougNumberEditText.text.toString().trim()
            val size = dougSizeSpinner.selectedItem.toString()
            val type = dougTypeSpinner.selectedItem.toString()

            if (validateInput(number)) {
                createContainer(number, size, type)
            }
        }
    }

    private fun validateInput(number: String): Boolean {
        if (number.isEmpty()) {
            Toast.makeText(this, getString(R.string.doug_error_empty_number), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createContainer(number: String, size: String, type: String) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val product = "$type - Size: $size"
                val request = DougContainerRequest(number, product)
                val response = DougAPIClient.apiService.createContainer(request)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val containerResponse = response.body()

                        if (containerResponse != null && containerResponse.responseCode == "SUCESSFUL") {
                            Toast.makeText(
                                this@DougCreateContainerActivity,
                                getString(R.string.doug_container_created),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DougCreateContainerActivity,
                                containerResponse?.message ?: getString(R.string.doug_create_failed),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DougCreateContainerActivity,
                            getString(R.string.doug_error_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@DougCreateContainerActivity,
                        "${getString(R.string.doug_error_exception)}: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        dougProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        dougCreateButton.isEnabled = !isLoading
        dougNumberEditText.isEnabled = !isLoading
        dougSizeSpinner.isEnabled = !isLoading
        dougTypeSpinner.isEnabled = !isLoading
    }
}