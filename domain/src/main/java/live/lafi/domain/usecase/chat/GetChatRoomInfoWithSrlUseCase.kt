package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class GetChatRoomInfoWithSrlUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomSrl: Long) = chatRepository.getChatRoom(chatRoomSrl)
}