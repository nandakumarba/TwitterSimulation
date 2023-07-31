package com.twitter.simulation.android.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.twitter.simulation.android.ui.home.HomeScreen
import com.twitter.simulation.android.ui.theme.DarkBlue
import com.twitter.simulation.android.ui.theme.LightGreen
import com.twitter.simulation.android.ui.workInProgress.WorkInProgressScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    AppNavHost(navController = navController)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Notifications,
        Screen.Settings,
    )

    Scaffold(bottomBar = {
        BottomNavigation(backgroundColor = LightGreen) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.route,
                        tint = if (isSelected) DarkBlue else Color.DarkGray
                    )
                }, selected = isSelected, onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    }) { padding ->
        AnimatedNavHost(navController, startDestination = Screen.Home.route) {
            /**
             * Home Screen
             */
            composable(Screen.Home.route, enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )

            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                )

            }) { HomeScreen(padding) }

            /**
             * Search Screen
             */

            composable(Screen.Search.route, enterTransition = {
                when (initialState.destination.route) {
                    Screen.Home.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )

                    else -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )
                }
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )
            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                )
            }, popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Home.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )

                    else -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )
                }
            }) { WorkInProgressScreen() }

            /**
             * Notifications Screen
             */

            composable(Screen.Notifications.route, enterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )

                    else -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )

                    else -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )
                }
            }, popEnterTransition = {
                when (initialState.destination.route) {
                    Screen.Settings.route -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )

                    else -> slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )
                }
            }, popExitTransition = {
                when (targetState.destination.route) {
                    Screen.Settings.route -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                    )

                    else -> slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                    )
                }
            }) { WorkInProgressScreen() }
            /**
             * Settings Screen
             */
            composable(Screen.Settings.route, enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                )

            }, popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)
                )

            }, popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)
                )
            }) { WorkInProgressScreen() }

        }
    }
}

sealed class Screen(val route: String, val icon: ImageVector) {
    object Home : Screen("Home", Icons.Filled.Home)
    object Search : Screen("Search", Icons.Filled.Search)
    object Notifications : Screen("Notifications", Icons.Filled.Notifications)
    object Settings : Screen("Settings", Icons.Filled.Settings)
}