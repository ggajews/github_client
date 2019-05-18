package com.grzegorzgajewski.githubsearch.utils

import com.grzegorzgajewski.githubsearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BearerAuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizedRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
            .build()
        return chain.proceed(authorizedRequest)
    }

}