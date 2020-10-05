package app.storytel.candidate.com.modules

import app.storytel.candidate.com.BuildConfig
import app.storytel.candidate.com.repository.IRepository
import app.storytel.candidate.com.repository.MockRepository
import app.storytel.candidate.com.repository.Repository
import app.storytel.candidate.com.webservice.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApiService webservice: Webservice
    ): IRepository {
        return if (BuildConfig.BUILD_TYPE == "mock") {
            MockRepository()
        } else {
            Repository(webservice)
        }
    }
}