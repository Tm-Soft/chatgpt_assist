package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class DeleteChatRoomWithSrlUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long
    ) {
        chatRepository.deleteChatRoom(chatRoomSrl)
    }
}