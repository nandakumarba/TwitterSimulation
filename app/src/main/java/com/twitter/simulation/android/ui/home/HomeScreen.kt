package com.twitter.simulation.android.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.twitter.simulation.android.R
import com.twitter.simulation.android.ui.tweets.TweetsScreen
import com.twitter.simulation.android.viewmodel.TweetViewModel


@Composable
fun HomeScreen(paddingValues: PaddingValues) {
    val tweetViewModel = hiltViewModel<TweetViewModel>()
    Box(
        Modifier.fillMaxSize()
    ) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf(stringResource(R.string.for_you), stringResource(R.string.following))
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = tabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> TweetsScreen(paddingValues, tweetViewModel)
                1 -> TweetsScreen(paddingValues, tweetViewModel)
            }
        }
    }
}