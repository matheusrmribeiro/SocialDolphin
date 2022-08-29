package com.example.socialdolphin.features.timeline.presenter.timeline.cell

import android.view.View
import com.example.socialdolphin.databinding.RecyclerviewCellTimelinePostBinding
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.domain.entities.PostTypeEnum
import io.github.enicolas.genericadapter.adapter.BaseCell

class TimelinePostCell(var viewBinding: RecyclerviewCellTimelinePostBinding) :
    BaseCell(viewBinding.root) {

    fun set(post: PostEntity, repostAction: () -> Unit, quoteAction: () -> Unit) {
        setupActions(repostAction, quoteAction)
        when (post.type) {
            PostTypeEnum.POST -> setupPost(post)
            PostTypeEnum.REPOST -> setupRepost(post)
            PostTypeEnum.QUOTE -> setQuote(post)
        }
    }

    private fun setupActions(repostAction: () -> Unit, quoteAction: () -> Unit) {
        viewBinding.lntRepost.setOnClickListener {
            repostAction.invoke()
        }
        viewBinding.lntQuote.setOnClickListener {
            quoteAction.invoke()
        }
    }

    private fun setupPost(post: PostEntity) {
        viewBinding.txtCreator.text = post.creator?.name ?: ""
        viewBinding.txtContent.text = post.content
        viewBinding.viewQuote.ctlQuote.visibility = View.GONE
        viewBinding.imgRepost.visibility = View.GONE
        viewBinding.txtRepost.visibility = View.GONE
    }

    private fun setupRepost(post: PostEntity) {
        viewBinding.txtCreator.text = post.creator?.name ?: ""
        viewBinding.txtContent.text = post.content
        viewBinding.viewQuote.ctlQuote.visibility = View.GONE
        viewBinding.imgRepost.visibility = View.VISIBLE
        viewBinding.txtRepost.visibility = View.VISIBLE
    }

    private fun setQuote(post: PostEntity) {
        viewBinding.txtCreator.text = post.creator?.name ?: ""
        viewBinding.txtContent.text = post.content
        post.origin?.let {
            viewBinding.viewQuote.txtQuoteCreator.text = it.creator?.name ?: ""
            viewBinding.viewQuote.txtQuoteContent.text = it.content
            viewBinding.viewQuote.ctlQuote.visibility = View.VISIBLE
        }
        viewBinding.imgRepost.visibility = View.GONE
        viewBinding.txtRepost.visibility = View.GONE
    }

}