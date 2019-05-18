package com.grzegorzgajewski.githubsearch.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.args
import com.airbnb.mvrx.withState
import com.grzegorzgajewski.githubsearch.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.repository_details_fragment.*

@Parcelize
data class RepositoryDetailArgs(val id: String) : Parcelable

class RepositoryDetailsFragment : BaseMvRxFragment() {

    private val args: RepositoryDetailArgs by args()
    private val viewModel by activityViewModel(RepositoriesViewModel::class)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.repository_details_fragment, container, false)
    }

    override fun invalidate() {
        withState(viewModel) { repositoriesState ->
            val repository = repositoriesState.asyncRepositories()?.firstOrNull { it.id == args.id }
            if (repository != null) {
                name.text = repository.name
                openIssues.text = getString(R.string.open_issues, repository.openIssues)
                closedIssues.text = getString(R.string.closed_issues, repository.closedIssues)
                openPullRequests.text = getString(R.string.open_pull_requests, repository.openPullRequests)
                mergedPullRequests.text = getString(R.string.merged_pull_requests, repository.mergedPullRequests)
                closedPullRequests.text = getString(R.string.closed_pull_requests, repository.closedPullRequests)
            }
        }
    }
}


