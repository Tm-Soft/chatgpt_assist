package live.lafi.domain.model.chat_gpt

data class CompletionData(
    val id: String,
    val tokenUsage: TokenUsage,
    val data: List<ChatData>,
    val error: ErrorMessage
) {
    data class ErrorMessage(
        val message: String,
        val type: String,
        val param: String,
        val code: String
    )

    data class TokenUsage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
    )

    data class ChatData(
        val message: ChatGptMessage
    ) {
        data class ChatGptMessage(
            val role: String,
            val content: String
        )
    }
}
