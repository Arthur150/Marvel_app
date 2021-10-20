package com.example.marvel_app.api

import android.content.res.Resources
import com.example.marvel_app.R
import com.example.marvel_app.usecase.GetHashMd5UseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class MarvelInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val ts = Date().toString()
        val publicKey = Resources.getSystem().getString(R.string.public_api_key)
        val privateKey = Resources.getSystem().getString(R.string.private_api_key)
        var hash : String? = null

        runBlocking {
            val result = GetHashMd5UseCase(ts + privateKey + publicKey).execute()
            hash = result.getOrThrow()
        }


        requestBuilder.url(
            chain.request().url.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", publicKey)
            .addQueryParameter("hash", hash)
            .toString()
        )

        return chain.proceed(requestBuilder.build())
    }
}