package cl.hbrito.registerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.hbrito.registerapp.ui.screens.FormularioScreen
import cl.hbrito.registerapp.ui.screens.ListaMedicionesScreen
import cl.hbrito.registerapp.ui.theme.RegisterAppTheme

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura el contenido de la pantalla con Jetpack Compose
        setContent {
            // Aplica el tema personalizado de la aplicación
            RegisterAppTheme {
                MainApp() // Llama a la función principal de la aplicación
            }
        }
    }
}

// Composable principal que configura la navegación y vista general de la app
@Composable
fun MainApp() {
    val navController = rememberNavController() // Controlador de navegación para gestionar las rutas
    val viewModel: MedicionViewModel = viewModel() // Obtiene el ViewModel para manejar el estado de las mediciones

    Scaffold(
        modifier = Modifier.fillMaxSize(), // Configura que la pantalla ocupe todo el tamaño disponible
        content = { innerPadding ->
            // Configura la navegación dentro de la aplicación
            AppNavigation(
                navController = navController,
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding) // Asegura el correcto manejo del padding de la pantalla
            )
        }
    )
}

// Función de navegación que define las rutas entre las pantallas
@Composable
fun AppNavigation(
    navController: NavHostController, // Controlador de navegación
    viewModel: MedicionViewModel, // ViewModel para compartir datos entre pantallas
    modifier: Modifier = Modifier // Modificador opcional para ajustar el diseño
) {
    NavHost(
        navController = navController, // Asocia el controlador de navegación
        startDestination = "lista_mediciones", // Ruta inicial al abrir la aplicación
        modifier = modifier
    ) {
        // Define la pantalla de listado de mediciones
        composable("lista_mediciones") {
            ListaMedicionesScreen(
                viewModel = viewModel, // Pasa el ViewModel a la pantalla
                onNavigateToFormulario = { navController.navigate("formulario") } // Acción al navegar al formulario
            )
        }

        // Define la pantalla del formulario para registrar nuevas mediciones
        composable("formulario") {
            FormularioScreen(
                viewModel = viewModel, // Pasa el ViewModel a la pantalla
                onRegistroExitoso = { navController.popBackStack() }, // Acción al completar el registro
                onBackPressed = { navController.popBackStack() } // Acción al presionar "Atrás"
            )
        }
    }
}
