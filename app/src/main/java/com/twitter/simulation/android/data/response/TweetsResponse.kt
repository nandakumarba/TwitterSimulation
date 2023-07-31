package com.twitter.simulation.android.data.response

import com.squareup.moshi.Json

data class TweetsResponse(
    @field:Json(name = "tweets") val tweets: List<TweetResponse>,
    val error: String? = null
)

data class TweetResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "twitterId") val twitterId: String,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "imageUrl") val imageUrl: String,
    @field:Json(name = "formattedDate") val formattedDate: String,
    @field:Json(name = "repliesCount") val repliesCount: Long,
    @field:Json(name = "retweetsCount") val retweetsCount: Long,
    @field:Json(name = "likesCount") val likesCount: Long,
    @field:Json(name = "viewCount") val viewCount: String,
)