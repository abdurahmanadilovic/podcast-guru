package ba.codingstoic.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.codingstoic.utils.Event
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Created by Abdurahman Adilovic on 4/2/20.
 */

class LoginViewModel(private val userSession: UserSession) : ViewModel() {
    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors
    private val _navigateHome = MutableLiveData<Event<Boolean>>()
    val navigateHome: LiveData<Event<Boolean>> = _navigateHome

    fun login(usernameInput: String?, passwordInput: String?) {
        if (usernameInput.isNullOrBlank() || passwordInput.isNullOrBlank()) {
            _errors.postValue("Username or password empty!")
            return
        }

        usernameInput.let { username ->
            passwordInput.let { password ->
                viewModelScope.launch(SupervisorJob()) {
                    try {
                        userSession.loginUser(username, password)
                        _navigateHome.postValue(Event(true))
                    } catch (ex: Exception) {
                        _errors.postValue(ex.message)
                    }
                }
            }
        }
    }
}