package com.evothings.data.storage.room.typeConverter

import androidx.room.TypeConverter
import com.evothings.data.utils.fromJson
import com.evothings.data.utils.toJson

class ListTypeConverter {

    @TypeConverter
    fun fromIntegerListToString(integerList: List<Int>): String {
        return integerList.toJson()
    }

    @TypeConverter
    fun fromStringToList(input: String): List<Int> {
        return input.fromJson()
    }

}