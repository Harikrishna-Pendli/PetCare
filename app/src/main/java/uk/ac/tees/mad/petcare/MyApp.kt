package uk.ac.tees.mad.petcare

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application()
//    , Configuration.Provider {
//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory
//
//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun getWorkManagerConfiguration(): Configuration =
//        Configuration.Builder()
//            .setWorkerFactory(workerFactory)
//            .build()
//}


