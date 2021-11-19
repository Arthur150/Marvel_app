package com.example.marvel_app.usecase

import java.math.BigInteger
import java.security.MessageDigest

class GetHashMd5UseCase(private val str: String) : UseCase<String> {
    override suspend fun execute(): Result<String> {
        return try {
            val md = MessageDigest.getInstance("MD5")
            Result.success(
                (BigInteger(1, md.digest(str.toByteArray())).toString(16).padStart(32, '0'))
            )
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

}