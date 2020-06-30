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
            lifecycleScope.launch{// Ahora realizaremos un ejemplo donde tengamos que ejecutar dos llamadas en paralelo
                //si lo dejamos con el withContext, hasta que no termine el primero, no seguirá con el segundo.
                //Y lo que queremos no es eso, para ello, lo cambiamos a **async** para que trabajen paralelamente
                val success1 = async(Dispatchers.IO) {
                    validateLogin1(username.text.toString(), password.text.toString())
                }

                val success2 = async(Dispatchers.IO) {
                    validateLogin2(username.text.toString(), password.text.toString())
                }
                //El metodo en suspension se realiza cuando le agregamos el **.await** donde realizara el codigo necesario
                // y sincronizara ambas tareas
                toast(if(success1.await() && success2.await()) "Success" else "Failure")
            }
        }
    }

    private fun validateLogin1(username: String, password: String): Boolean{
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun validateLogin2(username: String, password: String): Boolean{
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun Context.toast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
