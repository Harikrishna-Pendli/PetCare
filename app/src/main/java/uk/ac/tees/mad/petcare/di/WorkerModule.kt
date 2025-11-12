package uk.ac.tees.mad.petcare.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.petcare.data.datasource.remote.PetDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {

    @Binds
    @Singleton
    abstract fun bindPetDataSource(
        impl: PetDataSource
    ): PetDataSource
}
