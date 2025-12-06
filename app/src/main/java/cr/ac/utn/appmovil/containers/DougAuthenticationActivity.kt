package cr.ac.utn.appmovil.containers

import Service.DougAPIClient
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.DougAuthRequest
import util.util

class DougAuthenticationActivity : AppCompatActivity() {

    private lateinit var dougUserEditText: EditText
    private lateinit var dougPasswordEditText: EditText
    private lateinit var dougLoginButton: Button
    private lateinit var dougProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doug_authentication)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        dougUserEditText = findViewById(R.id.doug_user_edit_text)
        dougPasswordEditText = findViewById(R.id.doug_password_edit_text)
        dougLoginButton = findViewById(R.id.doug_login_button)
        dougProgressBar = findViewById(R.id.doug_progress_bar)
    }

    private fun setupClickListeners() {
        dougLoginButton.setOnClickListener {
            val username = dougUserEditText.text.toString().trim()
            val password = dougPasswordEditText.text.toString().trim()

            if (validateInput(username, password)) {
                authenticateUser(username, password)
            }
        }
    }

    private fun validateInput(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(this, getString(R.string.doug_error_empty_username), Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, getString(R.string.doug_error_empty_password), Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun authenticateUser(username: String, password: String) {
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = DougAuthRequest(username, password)
                val response = DougAPIClient.apiService.authenticate(request)

                withContext(Dispatchers.Main) {
                    showLoading(false)

                    if (response.isSuccessful) {
                        val authResponse = response.body()

                        if (authResponse != null && (authResponse.responseCode == "INFO_FOUND" || authResponse.responseCode == "SUCESSFUL")) {
                            saveUserEmail(authResponse.data?.email ?: "")

                            Toast.makeText(
                                this@DougAuthenticationActivity,
                                getString(R.string.doug_login_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            util.openActivity(
                                this@DougAuthenticationActivity,
                                DougContainerListActivity::class.java
                            )
                            finish()
                        } else {
                            Toast.makeText(
                                this@DougAuthenticationActivity,
                                authResponse?.message ?: getString(R.string.doug_login_failed),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DougAuthenticationActivity,
                            getString(R.string.doug_error_network),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    Toast.makeText(
                        this@DougAuthenticationActivity,
                        "${getString(R.string.doug_error_exception)}: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun saveUserEmail(email: String) {
        val sharedPref = getSharedPreferences("DougPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("doug_user_email", email)
            apply()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        dougProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        dougLoginButton.isEnabled = !isLoading
        dougUserEditText.isEnabled = !isLoading
        dougPasswordEditText.isEnabled = !isLoading
    }
}