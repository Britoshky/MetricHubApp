package cl.hbrito.registerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

class MedicionViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MedicionDatabase.getDatabase(application)
    private val medicionDao = database.medicionDao()

    // Flujo de datos de todas las mediciones
    val mediciones: Flow<List<Medicion>> = medicionDao.getAllMediciones()

    // Método para agregar una nueva medición
    fun agregarMedicion(tipo: String, valor: Int, fecha: String) {
        val tipoEstandarizado = when (tipo.lowercase(Locale.ROOT)) {
            "agua", "water" -> "water"
            "luz", "light" -> "light"
            "gas", "gas" -> "gas"
            else -> "unknown"
        }
        val nuevaMedicion = Medicion(
            tipo = tipoEstandarizado,
            valor = valor,
            fecha = fecha
        )
        viewModelScope.launch {
            medicionDao.insertMedicion(nuevaMedicion)
        }
    }

    // Método para eliminar una medición
    fun eliminarMedicion(medicionId: Int) {
        viewModelScope.launch {
            medicionDao.deleteMedicionById(medicionId)
        }
    }
}

