package live.lafi.domain.usecase.chat

import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.repository.ChatRepository

class UpdateChatRoomSystemRoleListUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSystemRoleInfoList: List<ChatRoomSystemRoleInfo>
    ) {
        chatRepository.updateChatRoomSystemRoleList(
            chatRoomSystemRoleInfoList = chatRoomSystemRoleInfoList
        )
    }
}