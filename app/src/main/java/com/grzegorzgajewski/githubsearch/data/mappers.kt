package com.grzegorzgajewski.githubsearch.data

import com.apollographql.apollo.github.GithubRepositoriesQuery
import com.grzegorzgajewski.githubsearch.domain.Repository

fun GithubRepositoriesQuery.Data.toRepositoryList(): List<Repository> =
    this.repositoryOwner?.repositories?.nodes?.mapNotNull {
        it?.let { repository ->
            Repository(
                repository.id,
                repository.name,
                repository.url,
                repository.openIssues.totalCount,
                repository.closedIssues.totalCount,
                repository.openPullRequests.totalCount,
                repository.mergedPullRequests.totalCount,
                repository.closedPullRequests.totalCount
            )
        }

    } ?: emptyList()