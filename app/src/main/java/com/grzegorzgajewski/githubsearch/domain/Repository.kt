package com.grzegorzgajewski.githubsearch.domain

data class Repository(
    val id: String,
    val name: String,
    val url: String,
    val openIssues: Int,
    val closedIssues: Int,
    val openPullRequests: Int,
    val mergedPullRequests: Int,
    val closedPullRequests: Int
)