package cl.hbrito.registerapp

import androidx.room.Entity
import androidx.room.PrimaryKey

// Anotación @Entity para ROOM
@Entity(tableName = "mediciones")
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID autogenerado
    val tipo: String, // Tipo de medición (Agua, Luz, Gas)
    val valor: Int,   // Valor del medidor
    val fecha: String, // Fecha del registro
    val timestamp: Long = System.currentTimeMillis() // Se asigna el tiempo actual por defecto
)
