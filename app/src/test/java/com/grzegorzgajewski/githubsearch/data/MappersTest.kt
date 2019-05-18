package com.grzegorzgajewski.githubsearch.data

import com.grzegorzgajewski.githubsearch.domain.Repository
import org.junit.Assert.assertEquals
import org.junit.Test
import com.grzegorzgajewski.githubsearch.data.Repository as ApiRepository


class MappersTest {

    @Test
    fun `empty list maps to empty list`() {
        val responseData = ResponseData(
            User(
                Repositories(
                    listOf(
                        ApiRepository(
                            id = "ID",
                            url = "URL",
                            name = "Name",
                            openIssues = TotalCount(0),
                            closedIssues = TotalCount(1),
                            openPullRequests = TotalCount(2),
                            mergedPullRequests = TotalCount(3),
                            closedPullRequests = TotalCount(4)
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