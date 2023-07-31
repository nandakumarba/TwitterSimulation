package com.twitter.simulation.android.di

import com.twitter.simulation.android.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @AppHttpLoggingInterceptor
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @HttpClient
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptorOkHttpClient(
        @AppHttpLoggingInterceptor loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @AppConverter
    @Provides
    @Singleton
    fun provideConverterFactory(
    ): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @RetrofitClient
    @Provides
    @Singleton
    fun provideRetrofit(
        @HttpClient okHttpClient: OkHttpClient,
        @AppConverter converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

}