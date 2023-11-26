package live.lafi.data.network

import live.lafi.data.model.request.CompletionRequest
import live.lafi.data.model.response.CompletionResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming

interface OpenaiApi {
    @Streaming
    @POST("/v1/chat/completions")
    suspend fun getCompletion(
        @Body requestBody: CompletionRequest
    ): CompletionResponse

    @Streaming
    @POST("/v1/chat/completions")
    suspend fun getCompletionStream(
        @Body requestBody: CompletionRequest
    ): ResponseBody
}