package live.lafi.data.mapper

import live.lafi.data.room.entity.ChatContentEntity
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.data.room.entity.ChatRoomSystemRoleEntity
import live.lafi.domain.model.chat.ChatContentInfo
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo

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

    fun mapperToChatRoomSystemRoleInfoList(chatRoomSystemRoleEntity: List<ChatRoomSystemRoleEntity>) =
        chatRoomSystemRoleEntity.map {
            ChatRoomSystemRoleInfo(
                chatRoomSrl = it.chatRoomSrl,
                chatRoomSystemRoleSrl = it.chatRoomSystemRoleSrl,
                roleContent = it.roleContent
            )
        }

    fun mapperToChatRoomSystemRoleEntityList(chatRoomSystemRoleInfo: List<ChatRoomSystemRoleInfo>) =
        chatRoomSystemRoleInfo.map {
            ChatRoomSystemRoleEntity(
                chatRoomSrl = it.chatRoomSrl,
                chatRoomSystemRoleSrl = it.chatRoomSystemRoleSrl,
                roleContent = it.roleContent
            )
        }

    fun mapperToChatRoomSystemRoleEntity(chatRoomSystemRoleInfo: ChatRoomSystemRoleInfo) =
        ChatRoomSystemRoleEntity(
            chatRoomSrl = chatRoomSystemRoleInfo.chatRoomSrl,
            chatRoomSystemRoleSrl = chatRoomSystemRoleInfo.chatRoomSystemRoleSrl,
            roleContent = chatRoomSystemRoleInfo.roleContent
        )

    fun mapperToChatContentInfoList(chatContentEntity: List<ChatContentEntity>) =
        chatContentEntity.map {
            ChatContentInfo(
                chatRoomSrl = it.chatRoomSrl,
                chatContentSrl = it.chatContentSrl,
                parentChatContentSrl = it.parentChatContentSrl ?: 0L,
                role = it.role,
                content = it.content,
                contentSummary = it.contentSummary ?: "",
                contentTranslate = it.contentTranslate ?: "",
                useToken = it.useToken ?: 0,
                status = it.status,
                updateDate = it.updateDate ?: 0L,
                createDate = it.createDate ?: 0L
            )
        }
}