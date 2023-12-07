package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class GetChatContentListWithChatRoomSrlUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(chatRoomSrl: Long) =
        chatRepository.getChatContentListWithChatRoomSrl(chatRoomSrl = chatRoomSrl)
}