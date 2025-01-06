package cl.hbrito.registerapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Colores para tema oscuro (Minimalista y opaco)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9E9E9E), // Gris suave
    onPrimary = Color.Black, // Texto negro sobre primary
    secondary = Color(0xFF80CBC4), // Verde menta oscuro
    onSecondary = Color.Black,
    background = Color(0xFF121212), // Fondo negro puro
    onBackground = Color(0xFFE0E0E0), // Texto blanco suave
    surface = Color(0xFF1E1E1E), // Gris oscuro
    onSurface = Color(0xFFD6D6D6), // Gris claro para texto
    error = Color(0xFFCF6679), // Rojo suave para errores
    onError = Color.Black,
    tertiary = Color(0xFF4CAF50) // Verde fuerte para íconos

)

// Colores para tema claro (Minimalista y opaco)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE0E0E0), // Gris muy claro
    onPrimary = Color.Black, // Texto negro sobre primary
    secondary = Color(0xFFB2DFDB), // Verde menta claro
    onSecondary = Color.Black,
    background = Color(0xFFFFFFFF), // Fondo blanco
    onBackground = Color(0xFF212121), // Texto negro suave
    surface = Color(0xFFF5F5F5), // Gris claro para superficies
    onSurface = Color(0xFF424242), // Gris oscuro para texto
    error = Color(0xFFB00020), // Rojo oscuro para errores
    onError = Color.White,
    tertiary = Color(0xFF00796B) // Verde oscuro para íconos
)

@Composable
fun RegisterAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta el tema del sistema
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S, // Soporte para Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Puedes personalizar la tipografía en Typography.kt
        content = content
    )
}
