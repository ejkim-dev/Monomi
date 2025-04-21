package com.example.monomi.core.network.di

import com.example.monomi.core.model.BuildConfig
import com.example.monomi.core.network.source.network.KakaoApi
import com.example.monomi.core.network.source.network.MonomiClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        // 1) 헤더용 Interceptor
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
                .build()
            chain.proceed(request)
        }

        // 2) 로깅 Interceptor (디버그 전용)
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)                         // 헤더 자동 추가
            .apply {
                if (BuildConfig.DEBUG) addNetworkInterceptor(logging)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoApi(
        json: Json,
        okHttpClient: OkHttpClient
    ): KakaoApi {
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(KakaoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMonomiClient(kakaoApi: KakaoApi): MonomiClient {
        return MonomiClient(kakaoApi)
    }
}