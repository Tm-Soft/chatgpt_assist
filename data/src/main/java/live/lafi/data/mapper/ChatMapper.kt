package live.lafi.data.mapper

import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.domain.model.chat.ChatRoomInfo

object ChatMapper {
    fun mapperToChatRoomInfoList(chatRoomEntity: List<ChatRoomEntity>) =
        chatRoomEntity.map {
            ChatRoomInfo(
                chatRoomSrl = it.chatRoomSrl,
                title = it.chatRoomTitle,
                profileUri = it.profileUri ?: "",
                lastReadTimestamp = it.lastReadTimestamp ?: 0L,
                lastUpdateTimestamp = it.lastUpdateTimestamp ?: 0L
            )
        }

    fun mapperToChatRoomInfo(chatRoomEntity: ChatRoomEntity) =
        ChatRoomInfo(
            chatRoomSrl = chatRoomEntity.chatRoomSrl,
            title = chatRoomEntity.chatRoomTitle,
            profileUri = chatRoomEntity.profileUri ?: "",
            lastReadTimestamp = chatRoomEntity.lastReadTimestamp ?: 0L,
            lastUpdateTimestamp = chatRoomEntity.lastUpdateTimestamp ?: 0L
        )
}