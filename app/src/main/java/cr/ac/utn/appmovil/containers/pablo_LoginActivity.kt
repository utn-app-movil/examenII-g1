package cr.ac.utn.appmovil.containers

import Service.pablo_RetrofitClient
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import model.pablo_ApiDataResponse
import model.pablo_LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class pablo_LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pablo_activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pablo_login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = findViewById<EditText>(R.id.pablo_txtUsername).toString()
        val password = findViewById<EditText>(R.id.pablo_txtPassword).toString()
        val btnLogin = findViewById<Button>(R.id.pablo_btnAuth)

        val authRequest= pablo_LoginRequest(username, password)

        btnLogin.setOnClickListener {
            lifecycleScope.launch {
                val response = pablo_RetrofitClient.instance.login(authRequest)

                print(response)
            }
        }


    }
}