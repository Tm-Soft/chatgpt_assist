package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class InsertChatRoomSystemRoleUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long,
        roleContent: String
    ) {
        chatRepository.insertChatRoomSystemRole(
            chatRoomSrl = chatRoomSrl,
            roleContent = roleContent
        )
    }
}