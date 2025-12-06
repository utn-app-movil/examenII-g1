package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import util.util

class lau_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lau_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lau_buttonCreateC = findViewById<Button>(R.id.lau_buttonCreateC)
        lau_buttonCreateC.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, lau_createContainer::class.java)
        })

        val lau_buttonListC = findViewById<Button>(R.id.lau_buttonListC)
        lau_buttonListC.setOnClickListener(View.OnClickListener{ view->
            util.openActivity(this, lau_listContainers::class.java)
        })
    }
}