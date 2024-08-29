package es.javiercarrasco.act0603

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import es.javiercarrasco.act0603.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var resultadoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data

            if (result.resultCode == Activity.RESULT_OK) {
                binding.tvHash.setText(data?.getStringExtra(HashActivity.EXTRA_TEXTO))
                binding.tvHash.visibility = View.VISIBLE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (binding.tietTexto.text!!.isNotEmpty()) {
                binding.tvHash.visibility = View.GONE

                resultadoActivity.launch(
                    HashActivity.navigateToHash(
                        this@MainActivity,
                        binding.tietTexto.text.toString()
                    ),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this)
                )
            } else binding.tietTexto.error = getString(R.string.error_empty)
        }
    }
}