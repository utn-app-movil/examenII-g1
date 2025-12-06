package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JohelRoomActivity : AppCompatActivity() {

    private lateinit var johel_etCode_room: EditText
    private lateinit var johel_etDescription_room: EditText
    private lateinit var johel_btnSave_room: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_johel_room)

        johel_etCode_room = findViewById(R.id.johel_etCode_room)
        johel_etDescription_room = findViewById(R.id.johel_etDescription_room)
        johel_btnSave_room = findViewById(R.id.johel_btnSave_room)

        johel_btnSave_room.setOnClickListener {
            val code = johel_etCode_room.text.toString().trim()
            val description = johel_etDescription_room.text.toString().trim()

            if (code.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Container created (mock).", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
