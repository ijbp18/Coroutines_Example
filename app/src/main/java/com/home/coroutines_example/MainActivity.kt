package com.home.coroutines_example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit.setOnClickListener {
            lifecycleScope.launch{// Al utilizar lifecycleScope ya no tenemos que implementar niguna interfaz de Scope,
                // tampoco debemos preocuparnos por llamar al OnDestroy, ni nada de eso ya que el lifeCycle hará lo necesario por debajo.
                val success = withContext(Dispatchers.IO) {
                    validateLogin(username.text.toString(), password.text.toString())
                }
                toast(if(success) "Success" else "Failure")
            }
        }
    }

    private fun validateLogin(username: String, password: String): Boolean{
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun Context.toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
