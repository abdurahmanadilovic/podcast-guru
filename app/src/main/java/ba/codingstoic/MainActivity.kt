package ba.codingstoic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ba.codingstoic.user.UserSession
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val userSession: UserSession by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = nav_host_fragment as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.navigation_graph)
        graph.startDestination = if (userSession.isLoggedIn()) {
            R.id.mainFragment
        } else {
            R.id.loginFragment
        }
        navHostFragment.navController.setGraph(graph, intent.extras)
    }
}
