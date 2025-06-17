package br.com.fiap.biotrack


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.biotrack.Screens.DashboardScreen
import br.com.fiap.biotrack.Screens.RegistroAvistamentoScreen
import br.com.fiap.biotrack.Screens.PerfilScreen
import br.com.fiap.biotrack.ui.theme.NavegationTheme
import com.example.biotrack.screens.MapScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                   val navController = rememberNavController ()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {

                        composable(route = "configs") { PerfilScreen(navController) }
                        composable(route = "dashboard") { DashboardScreen(navController) }
                        composable(route = "maps") { MapScreen(navController) }
                        composable(route = "reg_avistamento") { RegistroAvistamentoScreen(navController) }
                        composable(route = "login") { Login(navController) }

                    }
                }

            }

        }

    }
}



