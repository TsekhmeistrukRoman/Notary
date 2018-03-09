package com.tsekhmeistruk.notary

import android.app.Application
import com.tsekhmeistruk.notary.di.ApplicationComponent
import com.tsekhmeistruk.notary.di.ApplicationModule
import com.tsekhmeistruk.notary.di.DaggerApplicationComponent
import com.tsekhmeistruk.notary.di.RoomModule

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
class NotaryApp : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .roomModule(RoomModule(this))
                .build()
    }

    fun applicationComponent(): ApplicationComponent = applicationComponent
}
