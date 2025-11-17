package uk.ac.tees.mad.petcare.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.petcare.data.datasource.remote.AuthDataSource
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import uk.ac.tees.mad.petcare.data.datasource.remote.UserDataSource
import uk.ac.tees.mad.petcare.data.local.PetDao
import uk.ac.tees.mad.petcare.data.repository.AuthRepositoryImpl
import uk.ac.tees.mad.petcare.data.repository.PetRepositoryImpl
import uk.ac.tees.mad.petcare.data.repository.UserRepositoryImpl
import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
import uk.ac.tees.mad.petcare.domain.repository.PetRepository
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideRealtimeDb(): FirebaseDatabase =
        FirebaseDatabase.getInstance().apply {
            setPersistenceEnabled(true)
        }
    // ---------- DATASOURCES ----------
    @Provides @Singleton
    fun provideAuthDataSource(auth: FirebaseAuth): AuthDataSource =
        AuthDataSource(auth)

    @Provides @Singleton
    fun provideUserDataSource(firestore: FirebaseFirestore, auth: FirebaseAuth): UserDataSource =
        UserDataSource(firestore, auth)

    @Provides @Singleton
    fun providePetDataSource(auth: FirebaseAuth, realtime: FirebaseDatabase): PetDataSource =
        PetDataSource(auth, realtime)

    // ---------- REPOSITORIES ----------
    @Provides @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(auth, firestore)

    @Provides @Singleton
    fun provideUserRepository(
        dataSource: UserDataSource,
        firestore: FirebaseFirestore
    ): UserRepository = UserRepositoryImpl(dataSource, firestore)

    @Provides @Singleton
    fun providePetRepository(
        dao: PetDao,
        petDataSource: PetDataSource
    ): PetRepository = PetRepositoryImpl(dao, petDataSource)

}