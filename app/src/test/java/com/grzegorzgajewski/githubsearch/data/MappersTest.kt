package com.grzegorzgajewski.githubsearch.data

import com.apollographql.apollo.github.GithubRepositoriesQuery
import com.grzegorzgajewski.githubsearch.domain.Repository
import org.junit.Assert.assertEquals
import org.junit.Test


class MappersTest {

    @Test
    fun `GithubRepositoriesQuery nodes maps to Repository list`() {
        val responseData = GithubRepositoriesQuery.Data(
            GithubRepositoriesQuery.RepositoryOwner(
                "", GithubRepositoriesQuery.Repository(
                    "", listOf(
                        GithubRepositoriesQuery.Node(
                            __typename = "", id = "ID",
                            url = "URL",
                            name = "Name",
                            openIssues = GithubRepositoriesQuery.OpenIssue("", 0),
                            closedIssues = GithubRepositoriesQuery.ClosedIssue("", 1),
                            openPullRequests = GithubRepositoriesQuery.OpenPullRequest("", 2),
                            mergedPullRequests = GithubRepositoriesQuery.MergedPullRequest("", 3),
                            closedPullRequests = GithubRepositoriesQuery.ClosedPullRequest("", 4)
                        )
                    )
                )
            )
        )
        val expected = listOf(
            Repository(
                id = "ID",
                name = "Name",
                url = "URL",
                openIssues = 0,
                closedIssues = 1,
                openPullRequests = 2,
                mergedPullRequests = 3,
                closedPullRequests = 4
            )
        )
        assertEquals(expected, responseData.toRepositoryList())
    }
}