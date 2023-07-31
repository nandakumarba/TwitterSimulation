package com.twitter.simulation.android.viewmodel

import android.app.Application
import com.twitter.simulation.android.MainDispatcherRule
import com.twitter.simulation.android.data.TweetRepository
import com.twitter.simulation.android.data.response.Response
import com.twitter.simulation.android.data.response.TweetResponse
import com.twitter.simulation.android.data.response.TweetsResponse
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TweetViewModelTest {

    private lateinit var tweetRepository: TweetRepository

    private lateinit var tweetViewModel: TweetViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        tweetRepository = mockk()
        val application = mockk<Application>(relaxed = true)
        tweetViewModel = spyk(TweetViewModel(application, tweetRepository))
    }

    @Test
    fun testGetTweets() = runTest {
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
            tweetRepository.getTweets()
        } returns Response.Success(tweetsResponse)

        tweetViewModel.getTweets()
        assertEquals(tweetViewModel.tweetsFlow.first(), Response.Success(tweetsResponse))

    }

}