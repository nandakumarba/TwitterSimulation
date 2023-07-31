package com.twitter.simulation.android.ui

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun init() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(AnimatedComposeNavigator())
            AppNavHost(navController)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testBottomNavigation(): Unit = runBlocking {
        composeRule.onNodeWithContentDescription("Home").assertIsDisplayed()

        composeRule.onNodeWithText("For you").assertIsDisplayed()

        composeRule.onNodeWithText("Following").performClick().assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Search")
            .performClick()
        val searchRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(searchRoute, "Search")

        composeRule.onNodeWithContentDescription("Notifications")
            .performClick()
        val notificationsRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(notificationsRoute, "Notifications")

        composeRule.onNodeWithContentDescription("Settings")
            .performClick()
        val settingsRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(settingsRoute, "Settings")

        composeRule.waitUntilNodeCount( hasTestTag("Tweets List"), 0, 5000)

    }
}

