package com.grzegorzgajewski.githubsearch.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.grzegorzgajewski.githubsearch.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RepositoryItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val itemView by lazy { findViewById<View>(R.id.item_view) }
    private val nameView by lazy { findViewById<TextView>(R.id.name) }
    private val urlView by lazy { findViewById<TextView>(R.id.url) }

    init {
        inflate(context, R.layout.repository_item, this)
    }

    @TextProp
    fun setName(name: CharSequence) {
        nameView.text = name
    }

    @TextProp
    fun setUrl(url: CharSequence) {
        urlView.text = url
    }

    @CallbackProp
    fun onClickListener(listener: OnClickListener?) {
        itemView.setOnClickListener(listener)
    }
}