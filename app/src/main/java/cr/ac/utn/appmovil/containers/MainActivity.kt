package cr.ac.utn.appmovil.containers

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import util.util


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn1_main = findViewById<Button>(R.id.btn1_main)
        btn1_main.setOnClickListener(View.OnClickListener{ view->
            //Marco
            util.openActivity(this, MainActivity::class.java)
        })

        val btn2_main = findViewById<Button>(R.id.btn2_main)
        btn2_main.setOnClickListener(View.OnClickListener{ view->
            //Yadrick
            util.openActivity(this, MainActivity::class.java)
        })

        val btn3_main = findViewById<Button>(R.id.btn3_main)
        btn3_main.setOnClickListener(View.OnClickListener{ view->
            //Alfredo
            util.openActivity(this, MainActivity::class.java)
        })

        val btn4_main = findViewById<Button>(R.id.btn4_main)
        btn4_main.setOnClickListener(View.OnClickListener{ view->
            //Brandon
            util.openActivity(this, MainActivity::class.java)
        })

        val btn5_main = findViewById<Button>(R.id.btn5_main)
        btn5_main.setOnClickListener(View.OnClickListener{ view->
            //Nidia
            util.openActivity(this, MainActivity::class.java)
        })

        val btn6_main = findViewById<Button>(R.id.btn6_main)
        btn6_main.setOnClickListener(View.OnClickListener{ view->
            //KevinV
            util.openActivity(this, MainActivity::class.java)
        })

        val btn7_main = findViewById<Button>(R.id.btn7_main)
        btn7_main.setOnClickListener(View.OnClickListener{ view->
            //Luis
            util.openActivity(this, MainActivity::class.java)
        })

        val btn8_main = findViewById<Button>(R.id.btn8_main)
        btn8_main.setOnClickListener(View.OnClickListener{ view->
            //Reychell
            util.openActivity(this, MainActivity::class.java)
        })

        val btn9_main = findViewById<Button>(R.id.btn9_main)
        btn9_main.setOnClickListener(View.OnClickListener{ view->
            //Deyfran
            util.openActivity(this, MainActivity::class.java)
        })

        val btn10_main = findViewById<Button>(R.id.btn10_main)
        btn10_main.setOnClickListener(View.OnClickListener{ view->
            //Johel
            util.openActivity(this, MainActivity::class.java)
        })

        val btn11_main = findViewById<Button>(R.id.btn11_main)
        btn11_main.setOnClickListener(View.OnClickListener{ view->
            //Douglas
            util.openActivity(this, MainActivity::class.java)
        })

        val btn12_main = findViewById<Button>(R.id.btn12_main)
        btn12_main.setOnClickListener(View.OnClickListener{ view->
            //Dylan
            util.openActivity(this, MainActivity::class.java)
        })

        val btn13_main = findViewById<Button>(R.id.btn13_main)
        btn13_main.setOnClickListener(View.OnClickListener{ view->
            //Caleb
            util.openActivity(this, MainActivity::class.java)
        })

        val btn14_main = findViewById<Button>(R.id.btn14_main)
        btn14_main.setOnClickListener(View.OnClickListener{ view->
            //Sofia
            util.openActivity(this, MainActivity::class.java)
        })

        val btn15_main = findViewById<Button>(R.id.btn15_main)
        btn15_main.setOnClickListener(View.OnClickListener{ view->
            //Samiel
            util.openActivity(this, MainActivity::class.java)
        })

        val btn16_main = findViewById<Button>(R.id.btn16_main)
        btn16_main.setOnClickListener(View.OnClickListener{ view->
            //Laura
            util.openActivity(this, MainActivity::class.java)
        })

        val btn17_main = findViewById<Button>(R.id.btn17_main)
        btn17_main.setOnClickListener(View.OnClickListener{ view->
            //Ansley
            util.openActivity(this, MainActivity::class.java)
        })

        val btn18_main = findViewById<Button>(R.id.btn18_main)
        btn18_main.setOnClickListener(View.OnClickListener{ view->
            //Pablo
            util.openActivity(this, MainActivity::class.java)
        })

        val btn19_main = findViewById<Button>(R.id.btn19_main)
        btn19_main.setOnClickListener(View.OnClickListener{ view->
            //Braylin
            util.openActivity(this, MainActivity::class.java)
        })

        val btn20_main = findViewById<Button>(R.id.btn20_main)
        btn20_main.setOnClickListener(View.OnClickListener{ view->
            //Paula
            util.openActivity(this, MainActivity::class.java)
        })

        val btn21_main = findViewById<Button>(R.id.btn21_main)
        btn21_main.setOnClickListener(View.OnClickListener{ view->
            //Ricardo
            util.openActivity(this, MainActivity::class.java)
        })

        val btn22_main = findViewById<Button>(R.id.btn22_main)
        btn22_main.setOnClickListener(View.OnClickListener{ view->
            //Maria Jose
            util.openActivity(this, MainActivity::class.java)
        })

        val btn23_main = findViewById<Button>(R.id.btn23_main)
        btn23_main.setOnClickListener(View.OnClickListener{ view->
            //Kamila
            util.openActivity(this, kam_LoginActivity::class.java)
        })
    }
}