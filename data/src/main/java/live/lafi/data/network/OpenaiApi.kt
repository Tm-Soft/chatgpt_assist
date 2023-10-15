package live.lafi.data.network

import live.lafi.data.model.request.CompletionRequest
import live.lafi.data.model.response.CompletionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenaiApi {
    @POST("/v1/chat/completions")
    suspend fun getCompletion(
        @Body requestBody: CompletionRequest
    ): CompletionResponse
}