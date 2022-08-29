package com.example.socialdolphin.features.timeline.presenter.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.databinding.RecyclerviewCellTimelinePostBinding
import com.example.socialdolphin.databinding.TimelineFragmentBinding
import com.example.socialdolphin.features.base.BaseFragment
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.presenter.timeline.cell.TimelinePostCell
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate

@AndroidEntryPoint
class TimelineFragment : BaseFragment<TimelineFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TimelineFragmentBinding
        get() = TimelineFragmentBinding::inflate

    private val viewModel by viewModels<TimelineViewModel>()
    private val adapter = GenericRecyclerAdapter()
    private var initialized = false

    override fun setupFragment() {
        setupButtons()
        setupRecyclerView()
        setupSwipeRefresh()
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        if (initialized)
            fetchData()
    }

    private fun setupButtons() {
        binding.imgProfile.setOnClickListener {
            val directions = TimelineFragmentDirections.actionTimelineFragmentToProfileFragment()
            navigate(directions)
        }

        binding.imgAdd.setOnClickListener {
            val directions = TimelineFragmentDirections.actionTimelineFragmentToNewPostFragment()
            navigate(directions)
        }
    }

    private fun setupRecyclerView() {
        adapter.delegate = recyclerViewDelegate
        binding.rcvTimeline.adapter = adapter
        adapter.snapshot?.snapshotList = emptyList()
    }

    private fun setupSwipeRefresh() {
        binding.swpFetch.setOnRefreshListener {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModel.getTimeline().observe { response ->
            when (response) {
                is ViewState.Loading -> onLoading()
                is ViewState.Success -> onSuccess()
                is ViewState.Error -> {}
            }
        }
    }

    private fun onLoading() {
        binding.swpFetch.isRefreshing = true
    }

    private fun onSuccess() {
        adapter.snapshot?.snapshotList = viewModel.timeline.toList()
        binding.swpFetch.isRefreshing = false
        initialized = true
    }

    private val recyclerViewDelegate = object : GenericRecylerAdapterDelegate {
        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
            return viewModel.timeline.size
        }

        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            val quoteAction = {
                val directions =
                    TimelineFragmentDirections.actionTimelineFragmentToNewPostFragment(viewModel.timeline[position])
                navigate(directions)
            }
            val repostAction = { createRepost(viewModel.timeline[position]) }
            (cell as? TimelinePostCell)?.set(viewModel.timeline[position], repostAction = repostAction, quoteAction = quoteAction)
        }

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType {
            return AdapterHolderType(
                RecyclerviewCellTimelinePostBinding::class.java,
                TimelinePostCell::class.java,
                0
            )
        }

        override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {

        }
    }

    private fun createRepost(entity: PostEntity) {
        viewModel.createRepost(entity).observe { response ->
            when (response) {
                is ViewState.Loading -> {}
                is ViewState.Success -> onSuccess(response.result)
                is ViewState.Error -> onError(response.messageRes ?: 0)
            }
        }
    }

    private fun onSuccess(stringRes: Int) {
        showSnack(message = stringRes, SnackBarType.Success) {
            fetchData()
            adapter.notifyDataSetChanged()
        }
    }

    private fun onError(stringRes: Int) {
        showSnack(message = stringRes, SnackBarType.Error)
    }

}