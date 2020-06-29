package com.home.coroutines_example

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job //operaciones de ui + Job para tener control de la cancelacion de dicha corrutina en el OnDestroy

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = SupervisorJob()//Utilizamos el SupervisorJob en vez del Job es que con el "Job" básico tiene como problema
        // es que si tenemos varias corrutinas y una de ella falla, las demás se cancelarán y con el "SupervisorJob" si una falla,
        // las demás continúan su flujo

        submit.setOnClickListener {
            launch{//Ya no hay que elegir un dispatcher ya que desde el scope estamos manejando el "Dispatchers.Main "
                //Con esto ya no nos tenemos que preocupar por el ciclo de vida del activity, si muere, así mismo morirá lo que
                // esté en segundo plano
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

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
