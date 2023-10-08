package live.lafi.chatgpt_assist.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.lafi.data.repository.LocalSettingRepositoryImpl
import live.lafi.domain.repository.LocalSettingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalSettingRepository(context: Context): LocalSettingRepository = LocalSettingRepositoryImpl(context)
}