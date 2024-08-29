package es.javiercarrasco.act0603

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.act0603.databinding.ActivityHashBinding
import java.math.BigInteger
import java.security.MessageDigest

class HashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHashBinding

    companion object {
        const val EXTRA_TEXTO = "EXTRA_TEXTO"

        fun navigateToHash(activity: AppCompatActivity, texto: String): Intent {
            return Intent(activity, HashActivity::class.java).apply {
                putExtra(EXTRA_TEXTO, texto.trim())
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                //ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            var hash = intent.getStringExtra(EXTRA_TEXTO)

            if (binding.rbMD5.isChecked) {
                val md = MessageDigest.getInstance("MD5")
                hash = BigInteger(
                    1,
                    md.digest(hash!!.toByteArray())
                ).toString(16).padStart(32, '0')
            } else {
                val md = MessageDigest.getInstance("SHA-1")
                hash = BigInteger(
                    1,
                    md.digest(hash!!.toByteArray())
                ).toString(16).padStart(32, '0')
            }

            // Se devuelve el resultado.
            setResult(Activity.RESULT_OK, Intent().apply {
                // Se añade el valor del rating.
                putExtra(EXTRA_TEXTO, hash)
            })
            finishAfterTransition()
        }
    }
}