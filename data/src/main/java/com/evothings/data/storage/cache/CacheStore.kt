package com.evothings.data.storage.cache

class CacheStore {

    private val cache: HashMap<String, String> = hashMapOf()

    fun get(key: String): String? = cache[key]

    fun put(key: String, value: String) = cache.put(key, value)

}