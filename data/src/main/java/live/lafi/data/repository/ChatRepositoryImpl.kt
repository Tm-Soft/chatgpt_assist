package live.lafi.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import live.lafi.data.mapper.ChatMapper
import live.lafi.data.room.ChatDatabase
import live.lafi.data.room.entity.ChatContentEntity
import live.lafi.data.room.entity.ChatRoomEntity
import live.lafi.data.room.entity.ChatRoomSystemRoleEntity
import live.lafi.domain.model.chat.ChatContentInfo
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDatabase: ChatDatabase
): ChatRepository {
    override suspend fun insertChatRoom(
        chatRoomType: Int,
        title: String,
        profileUri: String?
    ): Long {
        return chatDatabase.chatRoomDao().insert(
            ChatRoomEntity(
                chatRoomSrl = 0,
                chatRoomType = chatRoomType,
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

    // 오버 로딩으로 chatRoomType(Int) 파라미터가 있으면 ChatRoomType을 비교하여 찾는다.
    override suspend fun getAllChatRoom(chatRoomType: Int): Flow<List<ChatRoomInfo>> {
        return chatDatabase.chatRoomDao().getAll(chatRoomType = chatRoomType).map {
            ChatMapper.mapperToChatRoomInfoList(it)
        }
    }

    override suspend fun getChatRoom(chatRoomSrl: Long): ChatRoomInfo {
        return chatDatabase.chatRoomDao().getChatRoomEntity(chatRoomSrl).let {
            ChatMapper.mapperToChatRoomInfo(it)
        }
    }

    override suspend fun updateChatRoomTitle(chatRoomSrl: Long, title: String) {
        chatDatabase.chatRoomDao().updateChatRoomTitle(
            chatRoomSrl = chatRoomSrl,
            title = title
        )
    }

    override suspend fun deleteChatRoom(chatRoomSrl: Long) {
        chatDatabase.chatRoomDao().deleteWithSrl(chatRoomSrl = chatRoomSrl)
    }

    override suspend fun getChatRoomSystemRole(chatRoomSrl: Long): Flow<List<ChatRoomSystemRoleInfo>> {
        return chatDatabase.chatRoomSystemRoleDao().getChatRoomSystemRoleList(chatRoomSrl).map {
            ChatMapper.mapperToChatRoomSystemRoleInfoList(it)
        }
    }

    override suspend fun insertChatRoomSystemRole(chatRoomSrl: Long, roleContent: String) {
        chatDatabase.chatRoomSystemRoleDao().insert(
            ChatRoomSystemRoleEntity(
                chatRoomSystemRoleSrl = 0L,
                chatRoomSrl = chatRoomSrl,
                roleContent = roleContent
            )
        )
    }

    override suspend fun insertChatRoomSystemRoleList(list: List<ChatRoomSystemRoleInfo>) {
        chatDatabase.chatRoomSystemRoleDao().insertList(
            ChatMapper.mapperToChatRoomSystemRoleEntityList(list)
        )
    }

    override suspend fun updateChatRoomSystemRole(chatRoomSystemRoleInfo: ChatRoomSystemRoleInfo) {
        chatDatabase.chatRoomSystemRoleDao().update(
            ChatMapper.mapperToChatRoomSystemRoleEntity(chatRoomSystemRoleInfo)
        )
    }

    override suspend fun updateChatRoomSystemRoleList(chatRoomSystemRoleInfoList: List<ChatRoomSystemRoleInfo>) {
        chatDatabase.chatRoomSystemRoleDao().updateList(
            ChatMapper.mapperToChatRoomSystemRoleEntityList(chatRoomSystemRoleInfoList)
        )
    }

    override suspend fun deleteChatRoomSystemRole(chatRoomSystemRoleSrl: Long) {
        chatDatabase.chatRoomSystemRoleDao().deleteWithSystemRoleSrl(
            chatRoomSystemRoleSrl = chatRoomSystemRoleSrl
        )
    }

    override suspend fun deleteChatRoomSystemRoleWithChatRoomSrl(chatRoomSrl: Long) {
        chatDatabase.chatRoomSystemRoleDao().deleteWithSystemRoleSrlWithChatRoomSrl(
            chatRoomSrl = chatRoomSrl
        )
    }

    override suspend fun getAllChatContentWithChatRoomSrl(chatRoomSrl: Long): Flow<List<ChatContentInfo>> {
        return chatDatabase.chatContentDao().getAllWithChatRoomSrl(chatRoomSrl = chatRoomSrl).map {
            ChatMapper.mapperToChatContentInfoList(it)
        }
    }

    override suspend fun insertChatContent(
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
    ) {
        chatDatabase.chatContentDao().insert(
            ChatContentEntity(
                chatContentSrl = 0L,
                chatRoomSrl = chatRoomSrl,
                parentChatContentSrl = parentChatContentSrl,
                role = role,
                content = content,
                contentSummary = contentSummary,
                contentTranslate = contentTranslate,
                useToken = useToken,
                status = status,
                updateDate = updateDate,
                createDate = createDate,
            )
        )
    }
}