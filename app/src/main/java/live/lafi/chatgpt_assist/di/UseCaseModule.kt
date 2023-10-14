package live.lafi.chatgpt_assist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.lafi.domain.repository.ChatGptRepository
import live.lafi.domain.repository.LocalSettingRepository
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideSaveChatGptTokenUseCase(localSettingRepository: LocalSettingRepository) = SaveChatGptTokenUseCase(localSettingRepository)

    @Provides
    @Singleton
    fun provideLoadChatGptTokenUseCase(localSettingRepository: LocalSettingRepository) = LoadChatGptTokenUseCase(localSettingRepository)

    @Provides
    @Singleton
    fun providePostChatCompletionsUseCase(chatGptRepository: ChatGptRepository) = PostChatCompletionsUseCase(chatGptRepository)
}