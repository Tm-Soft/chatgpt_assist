package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalSettingRepository {
    suspend fun getChatGptToken(): String

    suspend fun getChatGptTokenFlow(): Flow<String>
    suspend fun updateChatGptToken(token: String)
}