package com.grzegorzgajewski.githubsearch.data

import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GitHubService {
    @POST("graphql")
    suspend fun repoQuery(@Body repoQuery: RepoQuery): Response<RepoQueryResponse>
}

@JsonClass(generateAdapter = true)
data class RepoQuery(
    val query: String = """
        query {
          user(login:"octocat") {
            repositories(first:100) {
                  nodes {
                  id
                  url
                  name
                  openIssues: issues(states:OPEN) {
                    totalCount
                  }
                  closedIssues: issues(states:CLOSED) {
                    totalCount
                  }
                  openPullRequests: pullRequests(states:OPEN) {
                    totalCount
                  }
                  mergedPullRequests: pullRequests(states:MERGED) {
                    totalCount
                  }
                  closedPullRequests: pullRequests(states:CLOSED) {
                    totalCount
                  }
                }
            }
          }
        }
    """.trimIndent()
)

@JsonClass(generateAdapter = true)
data class RepoQueryResponse(val data: ResponseData)

data class ResponseData(val user: User)
data class User(val repositories: Repositories)
data class Repositories(val nodes: List<Repository>)
data class Repository(
    val id: String,
    val url: String,
    val name: String,
    val openIssues: TotalCount,
    val closedIssues: TotalCount,
    val openPullRequests: TotalCount,
    val mergedPullRequests: TotalCount,
    val closedPullRequests: TotalCount
)

data class TotalCount(val totalCount: Int)
