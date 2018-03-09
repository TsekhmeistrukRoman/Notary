package com.tsekhmeistruk.notary.di

import android.app.Application
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, RoomModule::class))
interface ApplicationComponent {

    fun application(): Application
}
