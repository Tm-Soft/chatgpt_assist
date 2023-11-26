package live.lafi.chatgpt_assist.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.lafi.data.network.OpenaiApi
import live.lafi.data.repository.ChatGptRepositoryImpl
import live.lafi.data.repository.ChatRepositoryImpl
import live.lafi.data.repository.LocalSettingRepositoryImpl
import live.lafi.data.room.ChatDatabase
import live.lafi.domain.repository.ChatGptRepository
import live.lafi.domain.repository.ChatRepository
import live.lafi.domain.repository.LocalSettingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalSettingRepository(context: Context): LocalSettingRepository = LocalSettingRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideChatGptRepository(openaiApi: OpenaiApi): ChatGptRepository = ChatGptRepositoryImpl(openaiApi)

    @Provides
    @Singleton
    fun provideChatRepository(chatDatabase: ChatDatabase): ChatRepository = ChatRepositoryImpl(chatDatabase)
}