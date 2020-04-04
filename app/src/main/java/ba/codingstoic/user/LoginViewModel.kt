package ba.codingstoic.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Created by Abdurahman Adilovic on 4/2/20.
 */

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    fun login(usernameInput: String?, passwordInput: String?) {
        if (usernameInput.isNullOrBlank() || passwordInput.isNullOrBlank()) {
            _errors.postValue("Username or password empty!")
            return
        }

        usernameInput.let { username ->
            passwordInput.let { password ->
                viewModelScope.launch(SupervisorJob()) {
                    try {
                        userRepository.loginUser(username, password)
                    } catch (ex: Exception) {
                        _errors.postValue(ex.message)
                    }
                }
            }
        }
    }
}