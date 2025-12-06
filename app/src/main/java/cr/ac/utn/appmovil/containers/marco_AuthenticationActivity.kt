package cr.ac.utn.appmovil.containers

import android.content.Intent
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
import kotlin.jvm.java

class marco_authenticationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LOGGED_USERNAME: String = "marco_EXTRA_LOGGED_USERNAME"
    }

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var progressLogin: ProgressBar
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marco_authentication)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        progressLogin = findViewById(R.id.progressLogin)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener { onLoginClicked() }
    }

    private fun onLoginClicked() {
        val username = edtUsername.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.marco_error_empty_fields),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        progressLogin.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        marco_AuthApiClient.service.getTechnicians()
            .enqueue(object : Callback<marco_TechniciansResponse> {
                override fun onResponse(
                    call: Call<marco_TechniciansResponse>,
                    response: Response<marco_TechniciansResponse>
                ) {
                    if (!response.isSuccessful) {
                        showErrorAndReset(
                            getString(R.string.marco_error_technicians) +
                                    " (HTTP ${response.code()})"
                        )
                        return
                    }

                    val body = response.body()
                    val list = body?.data

                    if (list == null) {
                        showErrorAndReset(getString(R.string.marco_error_technicians))
                        return
                    }

                    val technician = list.firstOrNull {
                        it.id == username && it.password == password && it.isActive
                    }

                    if (technician == null) {
                        showErrorAndReset(getString(R.string.marco_error_not_in_list))
                    } else {
                        authenticateUser(username, password)
                    }
                }

                override fun onFailure(
                    call: Call<marco_TechniciansResponse>,
                    t: Throwable
                ) {
                    showErrorAndReset(
                        t.localizedMessage ?: getString(R.string.marco_error_technicians)
                    )
                }
            })
    }

    private fun authenticateUser(username: String, password: String) {
        val request = marco_LoginRequest(
            username = username,
            password = password
        )

        marco_AuthApiClient.service.login(request)
            .enqueue(object : Callback<marco_LoginResponse> {
                override fun onResponse(
                    call: Call<marco_LoginResponse>,
                    response: Response<marco_LoginResponse>
                ) {
                    progressLogin.visibility = View.GONE
                    btnLogin.isEnabled = true

                    if (!response.isSuccessful) {
                        Toast.makeText(
                            this@marco_authenticationActivity,
                            getString(R.string.marco_error_login) +
                                    " (HTTP ${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val body = response.body()
                    if (body == null) {
                        Toast.makeText(
                            this@marco_authenticationActivity,
                            getString(R.string.marco_error_login),
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    Toast.makeText(
                        this@marco_authenticationActivity,
                        body.message,
                        Toast.LENGTH_LONG
                    ).show()

                    if (body.responseCode == "INFO_FOUND") {
                        val intent = Intent(
                            this@marco_authenticationActivity,
                            activity_list_container::class.java
                        )
                        intent.putExtra(EXTRA_LOGGED_USERNAME, username)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(
                    call: Call<marco_LoginResponse>,
                    t: Throwable
                ) {
                    progressLogin.visibility = View.GONE
                    btnLogin.isEnabled = true

                    Toast.makeText(
                        this@marco_authenticationActivity,
                        t.localizedMessage ?: getString(R.string.marco_error_login),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun showErrorAndReset(message: String) {
        progressLogin.visibility = View.GONE
        btnLogin.isEnabled = true
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

private fun ERROR.putExtra(extraLoggedUsername: String, username2: String) {}

annotation class ERROR
