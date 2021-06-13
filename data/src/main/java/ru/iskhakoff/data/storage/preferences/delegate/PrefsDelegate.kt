package ru.iskhakoff.data.storage.preferences.delegate

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.string(
    defaultValue : String = "",
    key : (KProperty<*>) -> String = KProperty<*>::name
) : ReadWriteProperty<Any, String> =
    object : ReadWriteProperty<Any, String> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getString(key(property), defaultValue).toString()

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: String
        ) = edit().putString(key(property), value).apply()
    }


fun SharedPreferences.long(
    defaultValue: Long = 0,
    key : (KProperty<*>) -> String = KProperty<*>::name
) : ReadWriteProperty<Any, Long> =
    object : ReadWriteProperty<Any, Long> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getLong(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Long
        ) = edit().putLong(key(property), value).apply()
    }

fun SharedPreferences.int(
    defaultValue: Int = -1,
    key : (KProperty<*>) -> String = KProperty<*>::name
) : ReadWriteProperty<Any, Int> =
    object : ReadWriteProperty<Any, Int> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int)  = edit().putInt(key(property), value).apply()

        override fun getValue(thisRef: Any, property: KProperty<*>) = getInt(key(property), defaultValue)
    }