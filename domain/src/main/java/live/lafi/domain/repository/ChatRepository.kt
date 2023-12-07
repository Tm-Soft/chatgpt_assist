package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow
import live.lafi.domain.model.chat.ChatContentInfo
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo

interface ChatRepository {
    suspend fun insertChatRoom(
        title: String,
        profileUri: String?,
    ): Long

    suspend fun getAllChatRoom(): Flow<List<ChatRoomInfo>>

    suspend fun getChatRoom(chatRoomSrl: Long): ChatRoomInfo

    suspend fun updateChatRoomTitle(chatRoomSrl: Long, title: String)

    suspend fun deleteChatRoom(chatRoomSrl: Long)

    suspend fun getChatRoomSystemRole(chatRoomSrl: Long): Flow<List<ChatRoomSystemRoleInfo>>

    suspend fun insertChatRoomSystemRole(
        chatRoomSrl: Long,
        roleContent: String
    )

    suspend fun insertChatRoomSystemRoleList(
        list: List<ChatRoomSystemRoleInfo>
    )

    suspend fun updateChatRoomSystemRole(
        chatRoomSystemRoleInfo: ChatRoomSystemRoleInfo
    )

    suspend fun updateChatRoomSystemRoleList(
        chatRoomSystemRoleInfoList: List<ChatRoomSystemRoleInfo>
    )

    suspend fun deleteChatRoomSystemRole(
        chatRoomSystemRoleSrl: Long
    )

    suspend fun deleteChatRoomSystemRoleWithChatRoomSrl(
        chatRoomSrl: Long
    )

    /**
     * chat Content 관련 함수
     */
    suspend fun getAllChatContent(): Flow<List<ChatContentInfo>>

    suspend fun getAllChatContentWithChatRoomSrl(
        chatRoomSrl: Long
    ): Flow<List<ChatContentInfo>>

    suspend fun getChatContentWaitMyMessage(): Flow<ChatContentInfo>

    suspend fun getChatContentListWithChatRoomSrl(chatRoomSrl: Long): List<ChatContentInfo>

    suspend fun updateChatContentStatus(
        chatContentSrl: Long,
        status: String,
        updateDate: Long
    )

    suspend fun insertChatContent(
        chatRoomSrl: Long,
        parentChatContentSrl: Long?,
        role: String,
        content: String,
        contentSummary: String?,
        contentTranslate: String?,
        useToken: Int?,
        status: String,
        updateDate: Long,
        createDate: Long
    )

    suspend fun deleteChatContentWithChatRoomSrl(
        chatRoomSrl: Long
    )
}