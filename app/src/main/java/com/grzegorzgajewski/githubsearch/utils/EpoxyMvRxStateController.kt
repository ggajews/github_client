package com.grzegorzgajewski.githubsearch.utils

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState

class EpoxyMvRxStateController<T : MvRxState>(
    private val viewModel: BaseMvRxViewModel<T>,
    private val buildModelsCallback: EpoxyController.(state: T) -> Unit
) : AsyncEpoxyController() {
    override fun buildModels() {
        withState(viewModel) { state ->
            buildModelsCallback(state)
        }
    }
}