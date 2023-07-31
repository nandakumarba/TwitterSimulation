package com.twitter.simulation.android.di

import com.twitter.simulation.android.data.service.TweetService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideTweetService(
        @RetrofitClient retrofit: Retrofit
    ): TweetService {
        return retrofit.create(TweetService::class.java)
    }

}