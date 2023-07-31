package com.twitter.simulation.android.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.twitter.simulation.android.data.TweetRepository
import com.twitter.simulation.android.data.response.Response
import com.twitter.simulation.android.data.response.TweetsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val tweetRepository: TweetRepository
) : AndroidViewModel(context as Application) {

    val tweetsFlow = MutableSharedFlow<Response<TweetsResponse>>()

    fun getTweets() {
        viewModelScope.launch {
            val response = tweetRepository.getTweets()
            tweetsFlow.emit(response)
        }
    }

}