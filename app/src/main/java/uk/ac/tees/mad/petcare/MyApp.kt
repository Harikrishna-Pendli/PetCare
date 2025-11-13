package uk.ac.tees.mad.petcare

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

//    override fun onCreate() {
//        super.onCreate()
//
//        val periodicSync = PeriodicWorkRequestBuilder<PetSyncWorker>(
//            15, TimeUnit.MINUTES
//        )
//            .setConstraints(
//                Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//            )
//            .build()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "pet_sync_worker",
//            ExistingPeriodicWorkPolicy.KEEP,
//            periodicSync
//        )
//    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

