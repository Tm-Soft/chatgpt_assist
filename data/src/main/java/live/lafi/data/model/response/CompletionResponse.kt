package live.lafi.data.model.response

import com.google.gson.annotations.SerializedName

data class CompletionResponse (
    @SerializedName("id") val id: String?,
    @SerializedName("usage") val tokenUsage: TokenUsage?,
    @SerializedName("choices") val data: List<ChatData>?,
    @SerializedName("error") val error: ErrorMessage?
) {
    data class ErrorMessage(
        val message: String?,
        val type: String?,
        val param: String?,
        val code: String?
    )

    data class TokenUsage(
        val prompt_tokens: Int?,
        val completion_tokens: Int?,
        val total_tokens: Int?
    )

    data class ChatData(
        val message: ChatGptMessage?
    ) {
        data class ChatGptMessage(
            val role: String?,
            val content: String?
        )
    }
}