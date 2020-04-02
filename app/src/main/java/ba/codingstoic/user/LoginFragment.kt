package ba.codingstoic.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ba.codingstoic.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abdurahman Adilovic on 4/2/20.
 */

class LoginFragment : Fragment() {
    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

}