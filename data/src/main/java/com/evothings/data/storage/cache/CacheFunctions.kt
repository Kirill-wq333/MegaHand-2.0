package com.evothings.data.storage.cache

import com.evothings.data.utils.fromJson
import com.evothings.data.utils.toJson

internal inline fun <REMOTE, reified DOMAIN> fetchCache(
    forceOnline: Boolean,
    cacheKey: String,
    fetchFromNetwork: () -> Result<REMOTE>,
    noinline mapper: REMOTE.() -> DOMAIN,
    cacheStore: CacheStore,
    noinline serializer: (DOMAIN) -> String = { it.toJson() },
    noinline deserializer: (String?) -> DOMAIN? = { it?.fromJson() },
): Result<DOMAIN> {

    val cachedDataRaw = cacheStore.get(cacheKey)
    val cacheSerialized = deserializer(cachedDataRaw)

    if (cacheSerialized == null || forceOnline) {
        val networkDataResult = fetchFromNetwork().map(mapper)
        networkDataResult.onSuccess {
            cacheStore.put(cacheKey, serializer(it))
        }
        return networkDataResult
    } else {
        return Result.success(cacheSerialized)
    }

}