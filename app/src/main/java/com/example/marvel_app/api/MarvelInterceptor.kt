package com.example.marvel_app.api

import com.example.marvel_app.manager.PropertiesManager
import com.example.marvel_app.usecase.GetHashMd5UseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class MarvelInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val ts = Date().toString()
        val publicKey = PropertiesManager.getProperties("marvel_api_key.properties", "public")
        val privateKey = PropertiesManager.getProperties("marvel_api_key.properties", "private")
        var hash : String? = null

        runBlocking {
            val result = GetHashMd5UseCase(ts + privateKey + publicKey).execute()
            hash = result.getOrThrow()
        }


        requestBuilder.url(chain.request().url().newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .toString()
        )

        return chain.proceed(requestBuilder.build())
    }
}