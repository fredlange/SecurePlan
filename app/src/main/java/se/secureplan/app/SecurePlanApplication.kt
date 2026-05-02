package se.secureplan.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import se.secureplan.app.core.data.seed.SeedDataProvider
import javax.inject.Inject

@HiltAndroidApp
class SecurePlanApplication : Application() {

    @Inject
    lateinit var seedDataProvider: SeedDataProvider

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            seedDataProvider.seedIfNeeded()
        }
    }
}
