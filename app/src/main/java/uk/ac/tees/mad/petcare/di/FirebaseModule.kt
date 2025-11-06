package uk.ac.tees.mad.petcare.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.petcare.data.datasource.remote.AuthDataSource
import uk.ac.tees.mad.petcare.data.datasource.remote.UserDataSource
import uk.ac.tees.mad.petcare.data.repository.AuthRepositoryImpl
import uk.ac.tees.mad.petcare.data.repository.UserRepositoryImpl
import uk.ac.tees.mad.petcare.domain.repository.AuthRepository
import uk.ac.tees.mad.petcare.domain.repository.UserRepository
import uk.ac.tees.mad.petcare.domain.use_case.SignInUseCase
import uk.ac.tees.mad.petcare.domain.use_case.SignUpUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(firebaseAuth: FirebaseAuth): AuthDataSource =
        AuthDataSource(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserDataSource(firestore: FirebaseFirestore, auth:FirebaseAuth): UserDataSource =
        UserDataSource(firestore,auth)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository =
        AuthRepositoryImpl(firebaseAuth, firestore)

    @Provides
    @Singleton
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase =
        SignUpUseCase(repository)

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository, userRepository: UserRepository): SignInUseCase =
        SignInUseCase(repository, userRepository = userRepository)

    @Provides
    @Singleton
    fun provideUserRpository(dataSource: UserDataSource, firestore: FirebaseFirestore): UserRepository=
        UserRepositoryImpl(dataSource,firestore)
}