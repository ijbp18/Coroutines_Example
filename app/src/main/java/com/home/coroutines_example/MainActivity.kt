package com.home.coroutines_example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                //Al elegir el .Main, se decide que esa rutina se realizara en el hilo principal
                //Ahora necesitamos que la siguiente línea donde valida el Login a un servidor, etc.
                // Este se realice en un segundo hilo, para esto
                //utilizaremos un **Suspend / withContext**
                //**Suspend / withContext** = define que la funcion bloqueará el codigo en el punto donde es llamado, ejecuta la operación que tenga que realizar
                //y cuando vuelva al hilo principal, devolvera el resultado del codigo u operacion esperado en la variable asignada.
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
