package com.grzegorzgajewski.githubsearch.data

import com.grzegorzgajewski.githubsearch.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(private val service: GitHubService) {
    suspend fun getData(): List<Repository> =
        withContext(Dispatchers.IO) {
            val response = service.repoQuery(RepoQuery())
            response
                .takeIf { it.isSuccessful }
                ?.body()?.data?.toRepositoryList()
                ?: emptyList()
        }
}

