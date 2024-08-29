package es.javiercarrasco.a0608

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.a0608.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var resultCaptura =
        registerForActivityResult(StartActivityForResult()) { result ->
            val data: Intent? = result.data

            if (result.resultCode == RESULT_OK) {
                val thumbnail: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    data?.getParcelableExtra("data", Bitmap::class.java)
                else data?.getParcelableExtra("data")

                binding.imageView.setImageBitmap(thumbnail)
            }
        }

    private val requestPermissionCamera =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                takePicture()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (PermissionHandler(this).checkPermission(Manifest.permission.CAMERA)) {
                takePicture()
            } else {
                requestPermissionCamera.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            resultCaptura.launch(it)
        }
    }
}