package com.home.coroutines_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(){

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun onSubmitClicked(username: String, password: String){
        viewModelScope.launch {
            _loginResult.value = withContext(Dispatchers.IO){validateLogin(username, password)}
        }
    }

    private fun validateLogin(user: String, pass: String): Boolean{
        Thread.sleep(2000)
        return user.isNotEmpty() && pass.isNotEmpty()
    }
}
