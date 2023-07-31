package com.twitter.simulation.android.ui.tweets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.twitter.simulation.android.R
import com.twitter.simulation.android.data.response.Response
import com.twitter.simulation.android.data.response.TweetResponse
import com.twitter.simulation.android.data.response.TweetsResponse
import com.twitter.simulation.android.viewmodel.TweetViewModel
import kotlinx.coroutines.delay


@Composable
fun TweetsScreen(padding: PaddingValues, viewModel: TweetViewModel) {
    val tweetsResponse by viewModel.tweetsFlow.collectAsState(Response.Loading(true))

    LaunchedEffect(true) {
        delay(500)
        viewModel.getTweets()
    }

    when (tweetsResponse) {
        is Response.Success -> {
            LazyColumn(modifier = Modifier.padding(padding).testTag("Tweets List")) {
                items((tweetsResponse as Response.Success<TweetsResponse>).data.tweets) {
                    Tweet(it)
                }
            }
        }

        is Response.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        else -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Column {
                    Image(
                        painterResource(id = R.drawable.ic_error),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                    Text(
                        text = (tweetsResponse as Response.Error).errorMessage!!,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@Composable
fun Tweet(item: TweetResponse) {
    Column {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 8.dp)
        ) {
            val imageSize = 48.dp
            val model = ImageRequest.Builder(LocalContext.current)
                .data(item.imageUrl)
                .size(with(LocalDensity.current) { imageSize.toPx() }.toInt())
                .memoryCachePolicy(CachePolicy.ENABLED)
                .transformations(CircleCropTransformation())
                .crossfade(true)
                .build()
            val painter = rememberAsyncImagePainter(model)
            Image(
                modifier = Modifier.size(imageSize),
                painter = painter,
                contentDescription = null,
            )

            Column(modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)) {
                val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Image(
                        painterResource(id = R.drawable.ic_verified),
                        contentDescription = "",
                        Modifier.size(16.dp)
                    )
                    Text(
                        item.twitterId,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(4.dp, 0.dp)
                    )
                    Text(buildAnnotatedString {
                        withStyle(style = paragraphStyle) {
                            append("\u2022")
                            append("\t")
                            append(item.formattedDate)
                        }
                    }, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }
                Text(item.content, fontSize = 14.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_message),
                        contentDescription = "",
                        modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp)
                    )
                    Text(item.repliesCount.toString(), fontSize = 11.sp)
                    Image(
                        painterResource(id = R.drawable.ic_retweets),
                        contentDescription = "",
                        modifier = Modifier.padding(16.dp, 0.dp, 4.dp, 0.dp)
                    )
                    Text(item.retweetsCount.toString(), fontSize = 11.sp)
                    Image(
                        painterResource(id = R.drawable.ic_favourite),
                        contentDescription = "",
                        modifier = Modifier.padding(16.dp, 0.dp, 4.dp, 0.dp)
                    )
                    Text(item.likesCount.toString(), fontSize = 11.sp)
                    Image(
                        painterResource(id = R.drawable.ic_bar_chart),
                        contentDescription = "",
                        modifier = Modifier.padding(16.dp, 0.dp, 4.dp, 0.dp)
                    )
                    Text(item.viewCount, fontSize = 11.sp)
                    Image(
                        painterResource(id = R.drawable.ic_share),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp, 0.dp, 4.dp, 0.dp)
                            .size(24.dp)
                    )
                }
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = Color.Gray)
    }
}