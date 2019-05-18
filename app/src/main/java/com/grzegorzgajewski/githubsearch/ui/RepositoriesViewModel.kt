package com.grzegorzgajewski.githubsearch.ui

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.grzegorzgajewski.githubsearch.BuildConfig
import com.grzegorzgajewski.githubsearch.data.RemoteDataSource
import com.grzegorzgajewski.githubsearch.domain.Repository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

data class RepositoriesState(
    val asyncRepositories: Async<List<Repository>> = Uninitialized
) : MvRxState

class RepositoriesViewModel(
    private val remoteDataSource: RemoteDataSource,
    initialState: RepositoriesState = RepositoriesState()
) : BaseMvRxViewModel<RepositoriesState>(initialState, BuildConfig.DEBUG) {

    init {
        logStateChanges()
        loadData()
    }

    fun loadData() {
        setState { copy(asyncRepositories = Loading()) }
        viewModelScope.launch {
            try {
                val data = remoteDataSource.getData()
                setState { copy(asyncRepositories = Success(data)) }
            } catch (e: Exception) {
                setState { copy(asyncRepositories = Fail(e)) }
            }
        }
    }

    companion object : MvRxViewModelFactory<RepositoriesViewModel, RepositoriesState> {
        override fun create(viewModelContext: ViewModelContext, state: RepositoriesState): RepositoriesViewModel {
            val remoteDataSource: RemoteDataSource by viewModelContext.activity.inject()
            return RepositoriesViewModel(remoteDataSource)
        }
    }
}