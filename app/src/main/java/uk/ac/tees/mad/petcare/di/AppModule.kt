package uk.ac.tees.mad.petcare.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.petcare.data.datasource.remote.AuthDataSource
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.datasource.remote.UserDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.local.PetDatabase
import uk.ac.tees.mad.petcare.data.repository.AuthRepositoryImpl
import uk.ac.tees.mad.petcare.data.repository.PetRepositoryImpl
import uk.ac.tees.mad.petcare.data.repository.UserRepositoryImpl
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import javax.inject.Singleton
import kotlin.jvm.java


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePetDataSource(
        firebaseFirestore: FirebaseFirestore
    ): PetDataSource = PetDataSource(firebaseFirestore)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): PetDatabase =
        Room.databaseBuilder(
            context,
            PetDatabase::class.java,
            "pet_database"
        ).build()

    @Provides
    @Singleton
    fun providePetDao(appDatabase: PetDatabase): PetDao = appDatabase.petDao()

    @Provides
    @Singleton
    fun providePetRepository(
        dao: PetDao,
        firebaseService: PetDataSource
    ): PetRepository = PetRepositoryImpl(dao, firebaseService)
}
