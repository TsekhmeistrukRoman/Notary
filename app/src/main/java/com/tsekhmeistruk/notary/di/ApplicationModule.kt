package com.tsekhmeistruk.notary.di

import android.app.Application
import com.tsekhmeistruk.notary.NotaryApp
import dagger.Module
import dagger.Provides

/**
 * Created by Roman Tsekhmeistruk on 09.03.2018.
 */
@Module
class ApplicationModule(private val notaryApp: NotaryApp) {

    @Provides
    fun provideApplication(): Application {
        return notaryApp
    }
}
