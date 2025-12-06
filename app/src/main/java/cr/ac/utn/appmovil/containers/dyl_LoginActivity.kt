package cr.ac.utn.appmovil.containers

import Controller.dyl_AuthController
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import model.dyl_auth
import util.util

class dyl_LoginActivity : AppCompatActivity() {
    private lateinit var controller: dyl_AuthController
    private lateinit var usertxt: EditText
    private lateinit var passtxt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dyl_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        controller = dyl_AuthController(this)
        usertxt=findViewById<EditText>(R.id.dyl_usrnametxt)
        passtxt=findViewById<EditText>(R.id.dyl_passwrdtxt)
        val btnlogin = findViewById<Button>(R.id.dyl_loginbtn)
        btnlogin.setOnClickListener (View.OnClickListener{view ->
                authentic()
        })
    }
    fun authentic(){
        lifecycleScope.launch {
            val request = dyl_auth(
                username = usertxt.text.toString(),
                password = passtxt.text.toString())
            try {
                controller.authenticate(this@dyl_LoginActivity,request)

            }catch (e: Exception){
                Toast.makeText(this@dyl_LoginActivity, e.message.toString()
                    , Toast.LENGTH_LONG).show()
            }
        }
    }
}