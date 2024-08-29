package es.javiercarrasco.a0608

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat

class PermissionHandler(val activity: Activity) {
    fun checkPermission(permission: String): Boolean {
        return when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                true
            }

            // Solo se entrará cuando se haya denegado el permiso una vez.
            activity.shouldShowRequestPermissionRationale(permission) -> {
                // TO-DO mostrar mensaje racional.
                Toast.makeText(activity, "Explicación racional", Toast.LENGTH_SHORT).show()
                false
            }

            else -> {
                false
            }
        }
    }
}