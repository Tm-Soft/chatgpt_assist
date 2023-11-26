package live.lafi.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import live.lafi.data.mapper.ChatMapper
import live.lafi.data.room.ChatDatabase
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDatabase: ChatDatabase
): ChatRepository {
    override suspend fun insertChatRoom(
        title: String,
        profileUri: String?
    ): Long {
        return chatDatabase.chatRoomDao().insert(
            ChatRoomEntity(
                chatRoomSrl = 0,
                chatRoomTitle = title,
                profileUri = profileUri,
                lastUpdateTimestamp = null,
                lastReadTimestamp = null,
            )
        )
    }

    override suspend fun getAllChatRoom(): Flow<List<ChatRoomInfo>> {
        return chatDatabase.chatRoomDao().getAll().map {
            ChatMapper.mapperToChatRoomInfoList(it)
        }
    }

    override suspend fun getChatRoom(chatRoomSrl: Long): ChatRoomInfo {
        return chatDatabase.chatRoomDao().getChatRoomEntity(chatRoomSrl).let {
            ChatMapper.mapperToChatRoomInfo(it)
        }
    }

    override suspend fun deleteChatRoom(chatRoomSrl: Long) {
        chatDatabase.chatRoomDao().deleteWithSrl(chatRoomSrl = chatRoomSrl)
    }
}