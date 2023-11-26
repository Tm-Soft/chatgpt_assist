package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class GetChatRoomSystemRoleUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long
    ) = chatRepository.getChatRoomSystemRole(
            chatRoomSrl = chatRoomSrl
        )
}