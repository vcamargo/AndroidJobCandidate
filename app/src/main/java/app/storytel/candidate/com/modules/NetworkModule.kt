package app.storytel.candidate.com.modules

import app.storytel.candidate.com.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @ApiService
    fun provideApiService(): Webservice {
        return Webservice.create()
    }
}

annotation class ApiService