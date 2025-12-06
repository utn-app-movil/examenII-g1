package cr.ac.utn.appmovil.containers.mjose_activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AuthRequest
import cr.ac.utn.appmovil.containers.mjose_models.mjose_AuthResponse
import cr.ac.utn.appmovil.containers.mjose_network.mjose_ApiService
import cr.ac.utn.appmovil.containers.mjose_network.mjose_RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class mjose_authenticationActivity : AppCompatActivity() {

    private lateinit var edtUser: EditText
    private lateinit var edtPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mjose_authentication)

        edtUser = findViewById(R.id.mjose_edtUser)
        edtPass = findViewById(R.id.mjose_edtPassword)
        btnLogin = findViewById(R.id.mjose_btnLogin)
        txtError = findViewById(R.id.mjose_txtError)

        btnLogin.setOnClickListener {
            val user = edtUser.text.toString().trim()
            val password = edtPass.text.toString().trim()

            if (user.isEmpty() || password.isEmpty()) {
                txtError.text = "Please enter both fields."
                return@setOnClickListener
            }

            authenticateUser(user, password)
        }
    }

    private fun authenticateUser(user: String, password: String) {


        val api = mjose_RetrofitClient.instance.create(mjose_ApiService::class.java)
        val request = mjose_AuthRequest(username = user, password = password)

        api.authenticate(request).enqueue(object : Callback<mjose_AuthResponse> {
            override fun onResponse(
                call: Call<mjose_AuthResponse>,
                response: Response<mjose_AuthResponse>
            ) {
                val body = response.body()
                Log.d("LOGIN", "Response: $body")

                if (response.isSuccessful && body?.responseCode == "INFO_FOUND") {

                    Toast.makeText(this@mjose_authenticationActivity,
                        "Welcome ${body.data?.name}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(
                        this@mjose_authenticationActivity,
                        mjose_roomListActivity::class.java
                    )
                    startActivity(intent)
                    finish()

                } else {
                    txtError.text = body?.message ?: "Invalid credentials"
                }
            }

            override fun onFailure(call: Call<mjose_AuthResponse>, t: Throwable) {
                txtError.text = "Network error: ${t.localizedMessage}"
            }
        })
    }
}
