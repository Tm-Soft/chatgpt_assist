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
    fun provideHeaderInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("Authorization", "Bearer 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")
                    .addHeader("Content-Type", "application/json")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val connectTimeOut = (1000 * 60).toLong()
        val readTimeOut = (1000 * 5).toLong()

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
            .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
                        val newRequest = request().newBuilder()
                            .addHeader(
                                "Authorization",
                                "Bearer ${GptToken.token}"
                            )
                            .addHeader("Content-Type", "application/json")
                            .build()
                        proceed(newRequest)
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