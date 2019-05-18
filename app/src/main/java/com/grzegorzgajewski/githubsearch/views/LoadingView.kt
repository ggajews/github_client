package com.grzegorzgajewski.githubsearch.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.grzegorzgajewski.githubsearch.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val progressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    init {
        View.inflate(context, R.layout.loader, this)
    }

    @ModelProp
    fun setLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }
}