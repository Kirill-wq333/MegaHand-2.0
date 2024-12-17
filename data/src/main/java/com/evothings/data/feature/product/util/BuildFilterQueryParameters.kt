package com.evothings.data.feature.product.util

import com.evothings.domain.feature.product.model.SearchFilter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

@Suppress("UNCHECKED_CAST")
internal fun buildFilterQueryParameters(filters: SearchFilter): String {
    val builder = StringBuilder()
    val classRef = filters::class
    builder.append("?")
    val propertyNames = classRef.declaredMemberProperties
        .map { property -> property.name }

    for (property in propertyNames) {
        val propRef =
            classRef.declaredMemberProperties.find { it.name == property } ?: continue
        val castedProperty = propRef as KProperty1<SearchFilter, Any?>
        val value = castedProperty.get(filters) ?: continue

        if (propRef.name == "showExpensiveFirst" && value is Boolean) {
            val prefix = if (!value) "-" else ""
            val orderingValue = "${prefix}price_with_discount"
            builder.append("ordering=$orderingValue&")
            continue
        }

        if (value is Map<*, *>) {
            for ((k, v) in value.entries) {
                if (v is Collection<*>) {
                    v.forEach { entry ->
                        builder.append("$k=$entry&")
                    }
                }
            }
        } else {
            when (value) {
                is String -> {
                    if (value.isNotEmpty()) {
                        builder.append("$property=$value")
                    }
                }
                is Int -> builder.append("$property=$value")
                is Boolean -> {
                    val valueToAppend = if (value) 1 else 0
                    builder.append("$property=$valueToAppend")
                }
            }
            builder.append('&')
        }

    }

    if (builder.endsWith('&')) {
        builder.setLength(builder.length - 1)
    }

    return builder.toString()
}