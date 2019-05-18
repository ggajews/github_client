package com.grzegorzgajewski.githubsearch.data

import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoQueryResponseParsingTest {

    private val moshi = Moshi.Builder().build()

    @Test
    fun `Moshi parse RepoResponse correctly`() {
        val response = """
        {
            "data": {
                "user": {
                    "repositories": {
                        "nodes": [
                            {
                                "id": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5"
                                "url": "https://github.com/octocat/Hello-World",
                                "name": "Hello-World",
                                "openIssues": {
                                    "totalCount": 152
                                },
                                "closedIssues": {
                                    "totalCount": 84
                                },
                                "openPullRequests": {
                                    "totalCount": 191
                                },
                                "mergedPullRequests": {
                                    "totalCount": 1
                                },
                                "closedPullRequests": {
                                    "totalCount": 53
                                }
                            },
                            {
                                "id": "MDEwOlJlcG9zaXRvcnkxMzAwMTky"
                                "url": "https://github.com/octocat/Spoon-Knife",
                                "name": "Spoon-Knife",
                                "openIssues": {
                                    "totalCount": 986
                                },
                                "closedIssues": {
                                    "totalCount": 303
                                },
                                "openPullRequests": {
                                    "totalCount": 12531
                                },
                                "mergedPullRequests": {
                                    "totalCount": 6
                                },
                                "closedPullRequests": {
                                    "totalCount": 4089
                                }
                            }
                        ]
                    }
                }
            }
        }
        """.trimIndent()
        val actual = moshi.adapter(RepoQueryResponse::class.java).fromJson(response)
        val expected = RepoQueryResponse(
            ResponseData(
                User(
                    Repositories(
                        listOf(
                            Repository(
                                id = "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                                url = "https://github.com/octocat/Hello-World",
                                name = "Hello-World",
                                openIssues = TotalCount(152),
                                closedIssues = TotalCount(84),
                                openPullRequests = TotalCount(191),
                                mergedPullRequests = TotalCount(1),
                                closedPullRequests = TotalCount(53)
                            ),
                            Repository(
                                id = "MDEwOlJlcG9zaXRvcnkxMzAwMTky",
                                url = "https://github.com/octocat/Spoon-Knife",
                                name = "Spoon-Knife",
                                openIssues = TotalCount(986),
                                closedIssues = TotalCount(303),
                                openPullRequests = TotalCount(12531),
                                mergedPullRequests = TotalCount(6),
                                closedPullRequests = TotalCount(4089)
                            )
                        )
                    )
                )
            )
        )
        assertEquals(expected, actual)
    }
}