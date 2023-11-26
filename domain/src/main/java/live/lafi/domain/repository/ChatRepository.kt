package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow
import live.lafi.domain.model.chat.ChatRoomInfo

interface ChatRepository {
    suspend fun insertChatRoom(
        title: String,
        profileUri: String?,
    ): Long

    suspend fun getAllChatRoom(): Flow<List<ChatRoomInfo>>

    suspend fun getChatRoom(chatRoomSrl: Long): ChatRoomInfo

    suspend fun deleteChatRoom(chatRoomSrl: Long)
}