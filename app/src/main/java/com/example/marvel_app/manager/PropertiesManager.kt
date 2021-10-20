package com.example.marvel_app.manager

import java.io.FileInputStream
import java.util.*

const val PROPERTIES_PATH = "app/src/main/java/com/example/lp_android3/resources/properties/"

object PropertiesManager {

    fun getProperties(propertiesPath: String, key: String): String {
        val properties = Properties()
        properties.load(FileInputStream(PROPERTIES_PATH + propertiesPath))

        return properties.getProperty(key)
    }
}