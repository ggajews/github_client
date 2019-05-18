package com.grzegorzgajewski.githubsearch.di

import com.grzegorzgajewski.githubsearch.data.GitHubService
import com.grzegorzgajewski.githubsearch.data.RemoteDataSource
import com.grzegorzgajewski.githubsearch.ui.RepositoriesViewModel
import com.grzegorzgajewski.githubsearch.utils.BearerAuthorizationInterceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteDataSourceModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createWebService<GitHubService>(get()) }
    single { RemoteDataSource(get()) }
}

val repositoriesModule = module {
    viewModel { RepositoriesViewModel(get()) }
}

fun createRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(BearerAuthorizationInterceptor())
        .build()
}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

