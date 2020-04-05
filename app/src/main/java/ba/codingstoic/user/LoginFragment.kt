package ba.codingstoic.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ba.codingstoic.R
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Abdurahman Adilovic on 4/2/20.
 */

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.errors.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.navigateHome.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        })

        login_fragment_login_button.setOnClickListener {
            loginViewModel.login(
                login_fragment_username_input.text?.toString(),
                login_fragment_password_input.text?.toString()
            )
        }
    }

}