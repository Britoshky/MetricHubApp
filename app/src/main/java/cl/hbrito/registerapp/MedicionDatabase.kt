package cl.hbrito.registerapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Anotación @Database para ROOM, especificando las entidades y versión
@Database(entities = [Medicion::class], version = 1, exportSchema = false)
abstract class MedicionDatabase : RoomDatabase() {

    // Proporciona acceso a MedicionDao
    abstract fun medicionDao(): MedicionDao

    companion object {
        @Volatile
        private var INSTANCE: MedicionDatabase? = null

        // Método para obtener la instancia única de la base de datos
        fun getDatabase(context: Context): MedicionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicionDatabase::class.java,
                    "medicion_database"
                )
                    .fallbackToDestructiveMigration() // Opción para borrar datos existentes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
