package com.dado.quicknews.data.database.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.dado.quicknews.data.model.Source

class SourceTypeConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(source: String): Source {
        val type = object: TypeToken<Source>() {}.type
        return Gson().fromJson(source, type)
    }
}