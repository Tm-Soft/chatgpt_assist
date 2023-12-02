package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class DeleteChatRoomSystemRoleUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSystemRoleSrl: Long
    ) {
        chatRepository.deleteChatRoomSystemRole(
            chatRoomSystemRoleSrl = chatRoomSystemRoleSrl
        )
    }
}