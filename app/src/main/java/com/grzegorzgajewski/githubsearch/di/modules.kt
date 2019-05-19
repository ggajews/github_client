package com.grzegorzgajewski.githubsearch.di

import com.apollographql.apollo.ApolloClient
import com.grzegorzgajewski.githubsearch.data.RemoteDataSource
import com.grzegorzgajewski.githubsearch.ui.RepositoriesViewModel
import com.grzegorzgajewski.githubsearch.utils.BearerAuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { createOkHttpClient() }
    single { RemoteDataSource(get()) }
    single { createApollo(get()) }
}

val repositoriesModule = module {
    viewModel { RepositoriesViewModel(get()) }
}

fun createApollo(client: OkHttpClient): ApolloClient {
    return ApolloClient.builder()
        .serverUrl("https://api.github.com/graphql")
        .okHttpClient(client)
        .build()
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(BearerAuthorizationInterceptor())
        .build()
}
