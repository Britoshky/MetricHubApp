package cl.hbrito.registerapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.hbrito.registerapp.Medicion
import cl.hbrito.registerapp.MedicionViewModel
import cl.hbrito.registerapp.R
import java.text.NumberFormat
import java.util.*

@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel = viewModel(), // ViewModel para gestionar las mediciones
    onNavigateToFormulario: () -> Unit // Callback para navegar al formulario
) {
    val allMediciones = viewModel.mediciones.collectAsState(initial = emptyList()).value // Obtiene las mediciones observables
    val itemsPerPage = 10 // Número de ítems por página
    var currentPage by remember { mutableStateOf(0) } // Estado actual de la página
    val totalPages = (allMediciones.size + itemsPerPage - 1) / itemsPerPage // Calcula el total de páginas
    val paginatedMediciones = allMediciones.drop(currentPage * itemsPerPage).take(itemsPerPage) // Obtiene los ítems para la página actual

    Scaffold(
        floatingActionButton = {
            // Botón flotante para agregar una nueva medición
            FloatingActionButton(
                onClick = { onNavigateToFormulario() },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_measurement)
                )
            }
        },
        bottomBar = {
            // Barra inferior para navegar entre páginas
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón para retroceder una página
                    Button(
                        onClick = { if (currentPage > 0) currentPage-- },
                        enabled = currentPage > 0, // Deshabilita si estás en la primera página
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(stringResource(id = R.string.previous_page))
                    }
                    // Botón para avanzar una página
                    Button(
                        onClick = { if (currentPage < totalPages - 1) currentPage++ },
                        enabled = currentPage < totalPages - 1, // Deshabilita si estás en la última página
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(stringResource(id = R.string.next_page))
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Título del historial de mediciones
            Text(
                text = stringResource(id = R.string.measurements_history),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (allMediciones.isEmpty()) {
                // Mensaje si no hay mediciones registradas
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_measurements),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                // Lista de mediciones con paginación
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(paginatedMediciones) { medicion ->
                        // Renderiza cada ítem de medición
                        MedicionItem(
                            medicion = medicion,
                            onDelete = { viewModel.eliminarMedicion(medicion.id) } // Callback para eliminar una medición
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(72.dp)) // Espaciado para el final de la lista
                    }
                }
            }
        }
    }
}

@Composable
fun MedicionItem(medicion: Medicion, onDelete: () -> Unit) {
    val formattedValue = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0 // Formato de moneda sin decimales
    }.format(medicion.valor)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surface), // Fondo del ítem
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Determina el ícono y texto según el tipo de medición
        val (icon, tipoTexto) = when (medicion.tipo.lowercase(Locale.ROOT)) {
            "water" -> Pair(Icons.Default.Water, stringResource(id = R.string.type_water))
            "light" -> Pair(Icons.Default.Lightbulb, stringResource(id = R.string.type_light))
            "gas" -> Pair(Icons.Default.LocalGasStation, stringResource(id = R.string.type_gas))
            else -> Pair(Icons.Default.Help, stringResource(id = R.string.unknown_type))
        }

        Icon(
            imageVector = icon,
            contentDescription = tipoTexto,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f) // El texto ocupa el espacio restante
        ) {
            // Tipo de medición
            Text(
                text = tipoTexto,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            // Valor de la medición en formato moneda
            Text(
                text = "${stringResource(id = R.string.label_value)}: $formattedValue",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Fecha de la medición
        Text(
            text = medicion.fecha,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Botón para eliminar una medición
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_measurement),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}
