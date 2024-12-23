package com.evothings.mhand.presentation.feature.navigation.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry

object NavAnimation {

    val slideIn: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
        {
            this.slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
        }

    val slideOut: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
        {
            this.slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
        }

}