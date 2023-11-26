package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalSettingRepository {
    suspend fun loadChatGptToken(): Flow<String>
    suspend fun saveChatGptToken(token: String)

    suspend fun saveMaxUseToken(number: Int)

    suspend fun loadMaxUseToken(): Flow<Int>
}