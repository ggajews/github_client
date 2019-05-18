package com.grzegorzgajewski.githubsearch.data

import com.grzegorzgajewski.githubsearch.domain.Repository

fun ResponseData.toRepositoryList(): List<Repository> = this.user.repositories.nodes.map {
    Repository(
        it.id,
        it.name,
        it.url,
        it.openIssues.totalCount,
        it.closedIssues.totalCount,
        it.openPullRequests.totalCount,
        it.mergedPullRequests.totalCount,
        it.closedPullRequests.totalCount
    )
}