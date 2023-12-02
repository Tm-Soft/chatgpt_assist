package live.lafi.domain.usecase.chat

import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.repository.ChatRepository

class UpdateChatRoomSystemRoleUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSystemRoleInfo: ChatRoomSystemRoleInfo
    ) {
        chatRepository.updateChatRoomSystemRole(
            chatRoomSystemRoleInfo = chatRoomSystemRoleInfo
        )
    }
}