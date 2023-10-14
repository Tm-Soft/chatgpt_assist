package live.lafi.data.mapper

import live.lafi.data.model.response.CompletionResponse
import live.lafi.domain.model.chat_gpt.CompletionData

object ChatGptMapper {
    fun mapperToCompletionData(response: CompletionResponse) =
        CompletionData(
            id = response.id ?: "",
            tokenUsage = CompletionData.TokenUsage(
                promptTokens = response.tokenUsage?.prompt_tokens ?: 0,
                completionTokens = response.tokenUsage?.completion_tokens ?: 0,
                totalTokens = response.tokenUsage?.prompt_tokens ?: 0
            ),
            data = response.data?.map {
                CompletionData.ChatData(
                    message = CompletionData.ChatData.ChatGptMessage(
                        role = it.message?.role ?: "",
                        content = it.message?.content ?: ""
                    )
                )
            } ?: emptyList(),
            error = CompletionData.ErrorMessage(
                message = response.error?.message ?: "",
                type = response.error?.type ?: "",
                param = response.error?.param ?: "",
                code = response.error?.code ?: ""
            )
        )
}