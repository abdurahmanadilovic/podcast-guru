package ba.codingstoic.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by Abdurahman Adilovic on 4/2/20.
 */

class LoginViewModel(val userRepository: UserRepository) : ViewModel() {
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    fun login(username: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(username, password)
        }
    }
}