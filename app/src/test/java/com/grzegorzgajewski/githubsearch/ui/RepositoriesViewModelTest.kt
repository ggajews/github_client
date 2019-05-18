package com.grzegorzgajewski.githubsearch.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.grzegorzgajewski.githubsearch.CoroutinesTestRule
import com.grzegorzgajewski.githubsearch.data.RemoteDataSource
import com.grzegorzgajewski.githubsearch.domain.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class RepositoriesViewModelTest {

    private val remoteDataSource: RemoteDataSource = mockk()
    private lateinit var viewModel: RepositoriesViewModel

    @get:Rule
    var mvRxTestRule = MvRxTestRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `loads data on init success`() {
        val testRepositories = listOf(
            Repository(
                id = "id",
                name = "test",
                url = "test",
                openIssues = 0,
                closedIssues = 2,
                openPullRequests = 3,
                mergedPullRequests = 4,
                closedPullRequests = 5
            )
        )
        coEvery { remoteDataSource.getData() } returns testRepositories

        viewModel = RepositoriesViewModel(remoteDataSource)

        coVerify { remoteDataSource.getData() }
        withState(viewModel) { state ->
            assertTrue(state.asyncRepositories is Success)
            assertEquals(testRepositories, state.asyncRepositories())
        }
    }

    @Test
    fun `loads data on init fail`() {
        val testException = IOException()
        coEvery { remoteDataSource.getData() } throws testException

        viewModel = RepositoriesViewModel(remoteDataSource)

        coVerify { remoteDataSource.getData() }
        withState(viewModel) { state ->
            assertTrue(state.asyncRepositories is Fail)
            assertEquals(testException, (state.asyncRepositories as Fail).error)
        }
    }

    @Test
    fun `loads data on retry`() {
        val testException = IOException()
        val testRepositories = listOf(
            Repository(
                id = "id",
                name = "test",
                url = "test",
                openIssues = 0,
                closedIssues = 2,
                openPullRequests = 3,
                mergedPullRequests = 4,
                closedPullRequests = 5
            )
        )
        coEvery { remoteDataSource.getData() } throws testException andThen testRepositories

        val lifecycleOwner: LifecycleOwner = mockk()
        val lifecycle = LifecycleRegistry(lifecycleOwner)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        val states = mutableListOf<RepositoriesState>()

        viewModel = RepositoriesViewModel(remoteDataSource)
        viewModel.subscribe(lifecycleOwner) {
            states += it
        }

        viewModel.loadData()

        val expectedStates = listOf(
            RepositoriesState(Fail(testException)),
            RepositoriesState(Loading()),
            RepositoriesState(Success(testRepositories))
        )
        assertEquals(expectedStates, states)
    }
}