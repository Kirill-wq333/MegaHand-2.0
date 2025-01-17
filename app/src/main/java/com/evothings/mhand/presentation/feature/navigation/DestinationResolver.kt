package com.evothings.mhand.presentation.feature.navigation

import com.evothings.domain.util.memoize
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.graph.Screen
import kotlin.reflect.KClass

object DestinationResolver {

    private val getAllScreens = {
        getNestedClasses(NavGraph::class).filter {
            Screen::class.java.isAssignableFrom(it.java)
        }
    }.memoize()

    fun cacheAllScreens() = getAllScreens.invoke()

    fun resolveClassByRoute(route: String?): KClass<*> {
        if (route == null) return Nothing::class

        val routeClassSimpleName = route.split(".").last()

        return getAllScreens.invoke().find { screen ->
            screen.simpleName == routeClassSimpleName
        } ?: Nothing::class
    }

    private fun getNestedClasses(clazz: KClass<*>): List<KClass<*>> {
        val accumulator: ArrayList<KClass<*>> = arrayListOf()
        for (classObject in clazz.nestedClasses) {
            accumulator.addAll(classObject.nestedClasses)
        }
        return accumulator.toList()
    }

}