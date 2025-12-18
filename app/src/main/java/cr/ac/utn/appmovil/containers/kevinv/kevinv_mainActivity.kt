package cr.ac.utn.appmovil.containers.kevinv

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.appmovil.containers.R
import util.util

class kevinv_mainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kevinv_main)

        val btnCreate = findViewById<Button>(R.id.kevinv_btnGoToCreate)
        val btnList = findViewById<Button>(R.id.kevinv_btnGoToList)

        btnCreate.setOnClickListener {
            util.openActivity(this, kevinv_roomActivity::class.java)
        }

        btnList.setOnClickListener {
            util.openActivity(this, kevinv_roomListActivity::class.java)
        }
    }
}
