package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class marco_activityContainer : AppCompatActivity() {

    private lateinit var edtContainerId: EditText
    private lateinit var edtProduct: EditText
    private lateinit var btnCreateContainer: Button
    private lateinit var progressCreateContainer: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_marco_container)

        edtContainerId = findViewById(R.id.edtContainerId)
        edtProduct = findViewById(R.id.edtProduct)
        btnCreateContainer = findViewById(R.id.btnCreateContainer)
        progressCreateContainer = findViewById(R.id.progressCreateContainer)

        btnCreateContainer.setOnClickListener { onCreateClicked() }
    }

    private fun onCreateClicked() {
        val id = edtContainerId.text.toString().trim()
        val product = edtProduct.text.toString().trim()

        if (id.isEmpty() || product.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.marco_error_empty_fields),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        progressCreateContainer.visibility = View.VISIBLE
        btnCreateContainer.isEnabled = false

        val request = marco_CreateContainerRequest(id = id, product = product)

        marco_ContainerApiClient.service.createContainer(request)
            .enqueue(object : Callback<marco_ContainerResponse> {
                override fun onResponse(
                    call: Call<marco_ContainerResponse>,
                    response: Response<marco_ContainerResponse>
                ) {
                    progressCreateContainer.visibility = View.GONE
                    btnCreateContainer.isEnabled = true

                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@marco_activityContainer,
                            "Error creating container (HTTP ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    if (body == null) {
                        Toast.makeText(
                            this@marco_activityContainer,
                            "Error creating container",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    Toast.makeText(
                        this@marco_activityContainer,
                        body.message,
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }

                override fun onFailure(
                    call: Call<marco_ContainerResponse>,
                    t: Throwable
                ) {
                    progressCreateContainer.visibility = View.GONE
                    btnCreateContainer.isEnabled = true

                    Toast.makeText(
                        this@marco_activityContainer,
                        t.localizedMessage ?: "Error creating container",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}