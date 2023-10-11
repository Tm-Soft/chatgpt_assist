package live.lafi.domain.repository

interface LocalSettingRepository {
    suspend fun getChatGPtToken(): String
    suspend fun updateChatGptToken(token: String)
}