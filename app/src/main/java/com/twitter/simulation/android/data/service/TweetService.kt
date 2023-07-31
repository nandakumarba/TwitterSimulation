package com.twitter.simulation.android.data.service

import com.twitter.simulation.android.data.response.TweetsResponse
import retrofit2.http.GET

interface TweetService {

    @GET("81b25a87-0c73-4948-809d-16b1785f4206")
    suspend fun getTweets(): TweetsResponse

}