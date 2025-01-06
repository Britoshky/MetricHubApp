package cl.hbrito.registerapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {
    // Obtener todas las mediciones ordenadas por timestamp descendente
    @Query("SELECT * FROM mediciones ORDER BY timestamp DESC")
    fun getAllMediciones(): Flow<List<Medicion>>

    // Insertar una nueva medición
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicion(medicion: Medicion)

    // Eliminar una medición específica
    @Query("DELETE FROM mediciones WHERE id = :medicionId")
    suspend fun deleteMedicionById(medicionId: Int)
}


