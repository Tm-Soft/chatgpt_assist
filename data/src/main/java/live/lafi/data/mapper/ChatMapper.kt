package live.lafi.data.mapper

import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.domain.model.chat.ChatRoomInfo

object ChatMapper {
    fun mapperToChatRoomInfo(chatRoomEntity: List<ChatRoomEntity>) =
        chatRoomEntity.map {
            ChatRoomInfo(
                chatRoomSrl = it.chatRoomSrl,
                title = it.chatRoomTitle,
                profileUri = it.profileUri ?: "",
                lastReadTimestamp = it.lastReadTimestamp ?: 0L,
                lastUpdateTimestamp = it.lastUpdateTimestamp ?: 0L
            )
        }
}