package cl.hbrito.registerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Water
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.hbrito.registerapp.MedicionViewModel
import cl.hbrito.registerapp.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FormularioScreen(
    viewModel: MedicionViewModel = viewModel(), // ViewModel para manejar el estado de las mediciones
    onRegistroExitoso: () -> Unit, // Callback para manejar un registro exitoso
    onBackPressed: () -> Unit // Callback para manejar el botón de retroceso
) {
    val typeWater = stringResource(id = R.string.type_water) // Texto para "Agua"
    val typeLight = stringResource(id = R.string.type_light) // Texto para "Luz"
    val typeGas = stringResource(id = R.string.type_gas) // Texto para "Gas"

    var tipo by remember { mutableStateOf("water") } // Tipo seleccionado, por defecto "Agua"
    var valor by remember { mutableStateOf(TextFieldValue("")) } // Valor ingresado en el campo
    var showError by remember { mutableStateOf(false) } // Estado para mostrar errores
    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Fecha actual formateada

    val keyboardController = LocalSoftwareKeyboardController.current // Controlador del teclado para cerrarlo al finalizar

    Column(
        modifier = Modifier
            .fillMaxSize() // Configura que el contenido ocupe todo el tamaño de la pantalla
            .padding(16.dp) // Padding alrededor del contenido
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), // Configura que el encabezado ocupe todo el ancho
            verticalAlignment = Alignment.CenterVertically // Alinea verticalmente los elementos en el centro
        ) {
            IconButton(onClick = { onBackPressed() }) { // Botón para manejar la navegación "Atrás"
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.previous_page), // Descripción para accesibilidad
                    tint = MaterialTheme.colorScheme.tertiary // Color del ícono
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre el ícono y el título

            Text(
                text = stringResource(id = R.string.title_register_meter), // Título de la pantalla
                style = MaterialTheme.typography.titleLarge, // Estilo de texto para el título
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre el encabezado y el campo de texto

        // Campo de texto para ingresar el precio
        TextField(
            value = valor, // Valor del campo
            onValueChange = {
                val input = it.text

                showError = when {
                    input.isEmpty() -> true // Campo vacío, se activa el error
                    input.contains(",") || input.contains(".") -> true // Contiene coma o punto decimal
                    input.toIntOrNull() == null || input.toIntOrNull()!! <= 0 -> true // No es un número entero mayor a 0
                    else -> false // Todo correcto, no hay error
                }

                valor = it // Actualiza el valor ingresado
            },
            label = { Text(stringResource(id = R.string.label_value)) }, // Etiqueta del campo
            modifier = Modifier.fillMaxWidth(), // Configura que el campo ocupe todo el ancho
            isError = showError, // Muestra el estado de error si corresponde
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, // Teclado para solo números
                imeAction = ImeAction.Done // Acción "Listo" en el teclado
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() } // Oculta el teclado al presionar "Done"
            )
        )

        // Muestra un mensaje de error si corresponde
        if (showError) {
            Text(
                text = when {
                    valor.text.isEmpty() -> stringResource(id = R.string.error_empty_field) // Mensaje si el campo está vacío
                    valor.text.contains(",") || valor.text.contains(".") -> stringResource(id = R.string.error_only_integers) // Mensaje si contiene comas o puntos
                    else -> stringResource(id = R.string.error_invalid_number) // Mensaje si el valor es inválido
                },
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }


        Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre el campo de texto y la fecha

        // Campo de texto para mostrar la fecha actual (solo lectura)
        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.label_date)) },
            readOnly = true, // Campo de solo lectura
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre la fecha y las opciones de tipo

        // Opciones de tipo de medición con íconos
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipo == "water", // Selecciona "Agua"
                    onClick = { tipo = "water" }
                )
                Icon(
                    imageVector = Icons.Default.Water,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(text = typeWater, modifier = Modifier.padding(start = 8.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipo == "light", // Selecciona "Luz"
                    onClick = { tipo = "light" }
                )
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(text = typeLight, modifier = Modifier.padding(start = 8.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipo == "gas", // Selecciona "Gas"
                    onClick = { tipo = "gas" }
                )
                Icon(
                    imageVector = Icons.Default.LocalGasStation,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(text = typeGas, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre las opciones y el botón

        // Botón para registrar la medición
        Button(
            onClick = {
                val number = valor.text.toIntOrNull()
                if (number == null || number <= 0) {
                    showError = true
                } else {
                    viewModel.agregarMedicion(tipo, number, fecha) // Agrega la medición
                    onRegistroExitoso() // Llama al callback para manejar un registro exitoso
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.register_button)) // Texto del botón
        }
    }
}
