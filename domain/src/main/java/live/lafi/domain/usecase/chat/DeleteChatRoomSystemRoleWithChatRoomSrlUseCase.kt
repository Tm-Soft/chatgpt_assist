package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class DeleteChatRoomSystemRoleWithChatRoomSrlUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long
    ) {
        chatRepository.deleteChatRoomSystemRoleWithChatRoomSrl(
            chatRoomSrl = chatRoomSrl
        )
    }
}