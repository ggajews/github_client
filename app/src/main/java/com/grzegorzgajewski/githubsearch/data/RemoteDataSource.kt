package com.grzegorzgajewski.githubsearch.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.github.GithubRepositoriesQuery
import com.grzegorzgajewski.githubsearch.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(private val client: ApolloClient) {
    suspend fun getData(): List<Repository> =
        withContext(Dispatchers.IO) {
            val response = client.query(GithubRepositoriesQuery()).toDeferred().await()
            response
                .takeIf { !it.hasErrors() }
                ?.data()?.toRepositoryList()
                ?: throw IllegalStateException(response.toString())
        }
}

