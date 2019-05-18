package com.grzegorzgajewski.githubsearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.activityViewModel
import com.google.android.material.snackbar.Snackbar
import com.grzegorzgajewski.githubsearch.R
import com.grzegorzgajewski.githubsearch.utils.EpoxyMvRxStateController
import com.grzegorzgajewski.githubsearch.views.loadingView
import com.grzegorzgajewski.githubsearch.views.repositoryItemView
import kotlinx.android.synthetic.main.repositories_list_fragment.*

class RepositoriesListFragment : BaseMvRxFragment() {

    private val viewModel by activityViewModel(RepositoriesViewModel::class)
    private val epoxyController by lazy { EpoxyMvRxStateController(viewModel) { buildModels(it) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.repositories_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.setController(epoxyController)
        viewModel.asyncSubscribe(RepositoriesState::asyncRepositories, onFail = {
            Snackbar
                .make(coordinatorLayout, R.string.repositories_fetching_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { viewModel.loadData() }
                .show()
        })
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    private fun EpoxyController.buildModels(state: RepositoriesState) {
        loadingView {
            id("loading")
            loading(!state.asyncRepositories.complete)
        }
        val repositories = state.asyncRepositories().orEmpty()
        repositories.forEach { repository ->
            repositoryItemView {
                id(repository.id)
                name(repository.name)
                url(repository.url)
                onClickListener { _ -> navigate(repository.id) }
            }
        }
    }

    private fun navigate(id: String) {
        findNavController().navigate(
            R.id.action_repositoriesListFragment_to_repositoryDetailsFragment,
            Bundle().apply { putParcelable(MvRx.KEY_ARG, RepositoryDetailArgs(id)) }
        )
    }
}
