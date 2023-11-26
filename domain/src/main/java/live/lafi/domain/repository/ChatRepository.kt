package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow
import live.lafi.domain.model.chat.ChatRoomInfo

interface ChatRepository {
    fun insertChatRoom(
        title: String,
        profileUri: String?,
    )

    suspend fun getAllChatRoom(): Flow<List<ChatRoomInfo>>
}