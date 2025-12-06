package cr.ac.utn.appmovil.containers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class JohelRoomListActivity : AppCompatActivity() {

    private lateinit var johel_btnCreate_roomList: Button
    private lateinit var johel_btnRefresh_roomList: Button
    private lateinit var johel_rvContainers_roomList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_johel_room_list)

        johel_btnCreate_roomList = findViewById(R.id.johel_btnCreate_roomList)
        johel_btnRefresh_roomList = findViewById(R.id.johel_btnRefresh_roomList)
        johel_rvContainers_roomList = findViewById(R.id.johel_rvContainers_roomList)

        johel_rvContainers_roomList.layoutManager = LinearLayoutManager(this)


        johel_btnCreate_roomList.setOnClickListener {
            val intent = Intent(this, JohelRoomActivity::class.java)
            startActivity(intent)
        }

        johel_btnRefresh_roomList.setOnClickListener {
            Toast.makeText(this, "Refresh containers (mock).", Toast.LENGTH_SHORT).show()
        }
    }
}
