package com.twitter.simulation.android.data

import com.twitter.simulation.android.data.response.Response
import com.twitter.simulation.android.data.response.TweetResponse
import com.twitter.simulation.android.data.response.TweetsResponse
import com.twitter.simulation.android.data.service.TweetService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TweetsRepositoryTest {

    @Test
    fun testGetTweets() = runTest {
        // Success Response
        val tweetService: TweetService = mockk()
        val tweetRepository = TweetRepository(tweetService)
        val tweetsResponse = TweetsResponse(
            tweets = listOf(
                TweetResponse(
                    id = "123",
                    name = "Android",
                    twitterId = "@Android",
                    content = "Test Content",
                    imageUrl = "https://test.com/image",
                    formattedDate = "17 Jul",
                    repliesCount = 230,
                    retweetsCount = 203,
                    likesCount = 49,
                    viewCount = "5K",
                ), TweetResponse(
                    id = "123",
                    name = "Android",
                    twitterId = "@Android",
                    content = "Test Content 2",
                    imageUrl = "https://test.com/image",
                    formattedDate = "17 Jul",
                    repliesCount = 20,
                    retweetsCount = 23,
                    likesCount = 109,
                    viewCount = "2K",
                )
            )
        )
        coEvery {
            tweetService.getTweets()
        } returns tweetsResponse

        assertEquals(tweetRepository.getTweets(), Response.Success(tweetsResponse))

        // Error Response
        coEvery {
            tweetService.getTweets()
        } throws Exception("Something Went Wrong!")

        try {
            tweetRepository.getTweets()
        } catch (e: Exception) {
            assertEquals(e, Exception("Something Went Wrong!"))
        }
    }

}