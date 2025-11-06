package uk.ac.tees.mad.petcare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.petcare.data.model.PetEntity

@Database(entities = [PetEntity::class], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}