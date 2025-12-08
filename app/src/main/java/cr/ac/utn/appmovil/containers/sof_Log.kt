package cr.ac.utn.appmovil.containers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cr.ac.utn.appmovil.containers.Service.sof_apiService
import cr.ac.utn.appmovil.containers.model.LoginRequest
import cr.ac.utn.appmovil.containers.model.ApiResponse
import cr.ac.utn.appmovil.containers.model.User
import util.util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class sof_Log : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sof_logactivity)

        val txtUser = findViewById<EditText>(R.id.sof_user)
        val txtPass = findViewById<EditText>(R.id.sof_password)
        val btnLogin = findViewById<Button>(R.id.sof_btn_login)

        btnLogin.setOnClickListener {
            val user = txtUser.text.toString().trim()
            val pass = txtPass.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = LoginRequest(user, pass)

            sof_apiService.sof_ApiService.authenticate(request)
                .enqueue(object : Callback<ApiResponse<User>> {

                    override fun onResponse(
                        call: Call<ApiResponse<User>>,
                        response: Response<ApiResponse<User>>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null && body.responseCode == "INFO_FOUND" && body.data != null) {
                                val intent = Intent(this@sof_Log, sof_ListContainer::class.java)
                                intent.putExtra("USER_EMAIL", body.data.email)
                                intent.putExtra("USER_NAME", body.data.name)
                                startActivity(intent)
                                finish()
                            } else {
                                util.showDialogCondition(
                                    this@sof_Log,
                                    "Error", body?.message ?: "Invalid credentials", "OK", "Cancel", { }, { }
                                )
                            }
                        } else {
                            util.showDialogCondition(
                                this@sof_Log,
                                "Error", "Server error: ${response.code()}", "OK", "Cancel", { }, { }
                            )
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                        util.showDialogCondition(
                            this@sof_Log, "Connection Error", t.message ?: "Unknown error", "OK", "Cancel", {}, {}
                        )
                    }
                })
        }
    }
}