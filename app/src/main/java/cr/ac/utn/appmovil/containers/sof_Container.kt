package cr.ac.utn.appmovil.containers

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cr.ac.utn.appmovil.containers.Service.sof_apiService
import cr.ac.utn.appmovil.containers.model.ContainerRequest
import cr.ac.utn.appmovil.containers.model.ApiResponse
import cr.ac.utn.appmovil.containers.model.Container
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class sof_Container : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sof_containeractivity)

        val txtID = findViewById<EditText>(R.id.sof_ID)
        val txtDesc = findViewById<EditText>(R.id.sof_containerProduct)
        val btnCreate = findViewById<Button>(R.id.sof_btn_create)
        val btnExit = findViewById<Button>(R.id.sof_btn_Exit)

        btnCreate.setOnClickListener {
            val id = txtID.text.toString().trim()
            val description = txtDesc.text.toString().trim()

            if (id.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = ContainerRequest(description)

            sof_apiService.sof_ApiService.createContainer(request)
                .enqueue(object : Callback<ApiResponse<Container>> {

                    override fun onResponse(
                        call: Call<ApiResponse<Container>>,
                        response: Response<ApiResponse<Container>>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null && body.responseCode == "INFO_SAVED") {
                                Toast.makeText(this@sof_Container, body.message, Toast.LENGTH_SHORT).show()

                                txtID.text.clear()
                                txtDesc.text.clear()
                            } else {
                                Toast.makeText(this@sof_Container, body?.message ?: "Error creating container", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this@sof_Container, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<Container>>, t: Throwable) {
                        Toast.makeText(
                            this@sof_Container, "Connection error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }

        btnExit.setOnClickListener {
            finish()
        }
    }
}