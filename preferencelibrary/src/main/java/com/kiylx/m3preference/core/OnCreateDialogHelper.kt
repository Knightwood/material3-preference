package com.kiylx.m3preference.core

import java.lang.reflect.Field

class ReflectHelper {
    companion object {
        inline fun <reified T> getField(clazz: Class<T>, fieldName: String):Field{
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            return field
        }

    }
}

