package uk.ac.tees.mad.petcare.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.local.PetDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PetDatabase =
        Room.databaseBuilder(
            app,
            PetDatabase::class.java,
            "pet_database"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providePetDao(db: PetDatabase): PetDao = db.petDao()
}
