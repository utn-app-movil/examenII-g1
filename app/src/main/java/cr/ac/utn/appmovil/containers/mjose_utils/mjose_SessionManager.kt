package cr.ac.utn.appmovil.containers.util

import android.content.Context
import android.content.Intent

object util {

    fun openActivity(context: Context, activity: Class<*>) {
        val intent = Intent(context, activity)
        context.startActivity(intent)
    }
}
