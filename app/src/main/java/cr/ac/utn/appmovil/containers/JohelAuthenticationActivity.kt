package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import Service.JohelAPIService
import model.JohelAuthRequest
import model.JohelBaseResponse
import model.JohelTechnician
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import util.JohelSessionManager
import util.util

class JohelAuthenticationActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_johel_authentication)

        etEmail = findViewById(R.id.johel_etEmail_auth)
        etPassword = findViewById(R.id.johel_etPassword_auth)
        btnLogin = findViewById(R.id.johel_btnLogin_auth)

        btnLogin.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {

        val technicianId = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (technicianId.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Technician id and password are required.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        setLoading(true)


        val body = JohelAuthRequest(
            id = technicianId,
            password = password
        )

        JohelAPIService.api.authUser(body)
            .enqueue(object : Callback<JohelBaseResponse<JohelTechnician>> {
                override fun onResponse(
                    call: Call<JohelBaseResponse<JohelTechnician>>,
                    response: Response<JohelBaseResponse<JohelTechnician>>
                ) {
                    setLoading(false)

                    if (!response.isSuccessful || response.body() == null) {
                        Toast.makeText(
                            this@JohelAuthenticationActivity,
                            "Server error. Try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()!!

                    Toast.makeText(
                        this@JohelAuthenticationActivity,
                        body.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    // README: usa "SUCCESSFUL" como responseCode
                    if (body.responseCode == "SUCCESSFUL" && body.data != null) {
                        val technician = body.data

                        // Guardar email del técnico (o id si prefieres)
                        JohelSessionManager.saveEmail(
                            this@JohelAuthenticationActivity,
                            technician.email
                        )

                        // Ir a la lista de contenedores
                        util.openActivity(
                            this@JohelAuthenticationActivity,
                            JohelRoomListActivity::class.java
                        )
                        finish()
                    }
                }

                override fun onFailure(
                    call: Call<JohelBaseResponse<JohelTechnician>>,
                    t: Throwable
                ) {
                    setLoading(false)
                    Toast.makeText(
                        this@JohelAuthenticationActivity,
                        "Network error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun setLoading(isLoading: Boolean) {
        btnLogin.isEnabled = !isLoading
    }
}
