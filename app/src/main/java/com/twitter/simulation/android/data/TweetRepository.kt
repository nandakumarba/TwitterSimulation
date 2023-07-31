package com.twitter.simulation.android.data

import com.twitter.simulation.android.data.response.Response
import com.twitter.simulation.android.data.response.TweetsResponse
import com.twitter.simulation.android.data.service.TweetService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetRepository @Inject constructor(private val tweetService: TweetService) {

    suspend fun getTweets(): Response<TweetsResponse> {
        return try {
            val response = tweetService.getTweets()
            Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e, "Unable to get your tweets, please try again")
        }
    }

}