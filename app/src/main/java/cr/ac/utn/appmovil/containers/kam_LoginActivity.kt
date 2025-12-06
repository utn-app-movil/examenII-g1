package cr.ac.utn.appmovil.containers

import Service.kam_RetrofitClient
import android.content.Intent
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

class kam_LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kam_login)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etUsername = findViewById(R.id.kam_et_username)
        etPassword = findViewById(R.id.kam_et_password)
        btnLogin = findViewById(R.id.kam_btn_login)
        progressBar = findViewById(R.id.kam_progress_bar)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.kam_fill_all_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authenticateUser(username, password)
        }
    }

    private fun authenticateUser(username: String, password: String) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val credentials = mapOf(
                    "username" to username,
                    "password" to password
                )

                val response = kam_RetrofitClient.apiService.authenticateUser(credentials)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()

                        if (apiResponse != null && apiResponse.responseCode == "INFO_FOUND") {
                            // Save user email for later use
                            val userEmail = apiResponse.data?.email ?: ""
                            saveUserEmail(userEmail)

                            Toast.makeText(
                                this@kam_LoginActivity,
                                R.string.kam_login_success,
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navigate to container list
                            val intent = Intent(this@kam_LoginActivity, kam_ContainerListActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@kam_LoginActivity,
                                apiResponse?.message ?: getString(R.string.kam_login_error),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@kam_LoginActivity,
                            R.string.kam_login_error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@kam_LoginActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun saveUserEmail(email: String) {
        val sharedPref = getSharedPreferences("kam_user_prefs", MODE_PRIVATE)
        sharedPref.edit().putString("user_email", email).apply()
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        btnLogin.isEnabled = !show
    }
}