package live.lafi.chatgpt_assist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import live.lafi.domain.repository.ChatGptRepository
import live.lafi.domain.repository.ChatRepository
import live.lafi.domain.repository.LocalSettingRepository
import live.lafi.domain.usecase.chat.DeleteChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.DeleteChatRoomSystemRoleWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.DeleteChatRoomWithSrlUseCase
import live.lafi.domain.usecase.chat.GetAllChatContentWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.GetAllChatRoomUseCase
import live.lafi.domain.usecase.chat.GetAllChatRoomWithChatRoomTypeUseCase
import live.lafi.domain.usecase.chat.GetChatRoomInfoWithSrlUseCase
import live.lafi.domain.usecase.chat.GetChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.InsertChatContentUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomSystemRoleListUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomUseCase
import live.lafi.domain.usecase.chat.UpdateChatRoomSystemRoleListUseCase
import live.lafi.domain.usecase.chat.UpdateChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.UpdateChatRoomTitleUseCase
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.LoadMaxUseTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveMaxUseTokenUseCase
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
    fun provideSaveMaxUseTokenUseCase(localSettingRepository: LocalSettingRepository) = SaveMaxUseTokenUseCase(localSettingRepository)

    @Provides
    @Singleton
    fun provideLoadMaxUseTokenUseCase(localSettingRepository: LocalSettingRepository) = LoadMaxUseTokenUseCase(localSettingRepository)

    @Provides
    @Singleton
    fun providePostChatCompletionsUseCase(chatGptRepository: ChatGptRepository) = PostChatCompletionsUseCase(chatGptRepository)

    @Provides
    @Singleton
    fun provideInsertChatRoomUseCase(chatRepository: ChatRepository) = InsertChatRoomUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideUpdateChatRoomTitleUseCase(chatRepository: ChatRepository) = UpdateChatRoomTitleUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetAllChatRoomUseCase(chatRepository: ChatRepository) = GetAllChatRoomUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetAllChatRoomWithChatRoomTypeUseCase(chatRepository: ChatRepository) = GetAllChatRoomWithChatRoomTypeUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetChatRoomInfoWithSrlUseCase(chatRepository: ChatRepository) = GetChatRoomInfoWithSrlUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideDeleteChatRoomWithSrlUseCase(chatRepository: ChatRepository) = DeleteChatRoomWithSrlUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideGetChatRoomSystemRoleUseCase(chatRepository: ChatRepository) = GetChatRoomSystemRoleUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideInsertChatRoomSystemRoleUseCase(chatRepository: ChatRepository) = InsertChatRoomSystemRoleUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideInsertChatRoomSystemRoleListUseCase(chatRepository: ChatRepository) = InsertChatRoomSystemRoleListUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideUpdateChatRoomSystemRoleUseCase(chatRepository: ChatRepository) = UpdateChatRoomSystemRoleUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideUpdateChatRoomSystemRoleListUseCase(chatRepository: ChatRepository) = UpdateChatRoomSystemRoleListUseCase(chatRepository)


    @Provides
    @Singleton
    fun provideDeleteChatRoomSystemRoleUseCase(chatRepository: ChatRepository) = DeleteChatRoomSystemRoleUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideDeleteChatRoomSystemRoleWithChatRoomSrlUseCase(chatRepository: ChatRepository) = DeleteChatRoomSystemRoleWithChatRoomSrlUseCase(chatRepository)

    /**
     * Chat Content 관련 UseCase
     */
    @Provides
    @Singleton
    fun provideGetAllChatContentWithChatRoomSrlUseCase(chatRepository: ChatRepository) = GetAllChatContentWithChatRoomSrlUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideInsertChatContentUseCase(chatRepository: ChatRepository) = InsertChatContentUseCase(chatRepository)
}