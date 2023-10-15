package live.lafi.chatgpt_assist.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import live.lafi.chatgpt_assist.BuildConfig
import live.lafi.data.network.OpenaiApi
import live.lafi.util.public_model.GptToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context
    @Provides
    fun provideOpenAIUrl() = "https://api.openai.com"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val connectTimeOutSec = (60).toLong()
        val readTimeOutSec = (120).toLong()

        val loggingInterceptor = HttpLoggingInterceptor()

        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith("{") && !message.startsWith("[")) {
                    Timber.tag("OkHttp").d(message)
                    return
                }
                try {
                    // Timber 와 Gson setPrettyPrinting 를 이용해 json 을 보기 편하게 표시해준다.
                    Timber.tag("OkHttp").d(
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(
                                JsonParser().parse(message)
                            )
                    )
                } catch (m: JsonSyntaxException) {
                    Timber.tag("OkHttp").d(message)
                }
            }
        })

        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .readTimeout(connectTimeOutSec, TimeUnit.SECONDS)
            .connectTimeout(readTimeOutSec, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
                        try {
                            val newRequest =request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader(
                                    "Authorization",
                                    "Bearer ${GptToken.token}"
                                )
                                .build()
                            proceed(newRequest)
                        } catch (e: Exception) {
                            proceed(request())
                        }
                    }
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePostsService(retrofit: Retrofit): OpenaiApi {
        return retrofit.create(OpenaiApi::class.java)
    }
}