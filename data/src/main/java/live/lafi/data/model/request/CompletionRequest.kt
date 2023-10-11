package live.lafi.data.model.request

import live.lafi.util.chat_gpt.model.ChatGptMessage

data class CompletionRequest(
    val model: String,
    val temperature: Double,
    val messages: List<ChatGptMessage>
)
