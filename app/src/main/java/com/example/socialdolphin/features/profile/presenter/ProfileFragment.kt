package com.example.socialdolphin.features.profile.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.socialdolphin.R
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.databinding.ProfileFragmentBinding
import com.example.socialdolphin.databinding.RecyclerviewCellTimelinePostBinding
import com.example.socialdolphin.features.base.BaseFragment
import com.example.socialdolphin.features.timeline.domain.entities.PostEntity
import com.example.socialdolphin.features.timeline.presenter.timeline.TimelineFragmentDirections
import com.example.socialdolphin.features.timeline.presenter.timeline.cell.TimelinePostCell
import dagger.hilt.android.AndroidEntryPoint
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ProfileFragmentBinding
        get() = ProfileFragmentBinding::inflate

    private val viewModel by viewModels<ProfileViewModel>()
    private val adapter = GenericRecyclerAdapter()

    override fun setupFragment() {
        setupRecyclerView()
        setupButtons()
        setupSwipeRefresh()
        fetchData()
    }

    private fun setupButtons() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgAdd.setOnClickListener {
            val directions = ProfileFragmentDirections.actionProfileFragmentToNewPostFragment()
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

    private fun setupHeader() {
        viewModel.user?.let {
            val formatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            val formattedDate = formatter.format(SimpleDateFormat("dd/MM/yyyy").parse(it.createdAt)!!)

            binding.txtName.text = it.name
            binding.txtSocialName.text = it.socialName
            binding.txtJoined.text = resources.getString(R.string.profile_joined, formattedDate)
        }
    }

    private fun setupStatistics() {
        viewModel.user?.let {
            binding.txtPostCount.text = resources.getString(
                R.string.profile_posts_count,
                viewModel.statistics.posts.toString()
            )
            binding.txtRepostCount.text = resources.getString(
                R.string.profile_reposts_count,
                viewModel.statistics.reposts.toString()
            )
            binding.txtQuoteCount.text = resources.getString(
                R.string.profile_quotes_count,
                viewModel.statistics.quotes.toString()
            )
        }
    }

    private fun fetchData() {
        fetchProfileHeader()
        fetchProfileTimeline()
    }

    private fun fetchProfileHeader() {
        viewModel.getUserById().observe { response ->
            when (response) {
                is ViewState.Loading -> onLoading()
                is ViewState.Success -> onSuccessUserHeader()
                is ViewState.Error -> {}
            }
        }
    }

    private fun fetchProfileTimeline() {
        onLoading()
        val mediatorValues = mutableMapOf<Int, ViewState<*>?>()

        val mediator = MediatorLiveData<Pair<Int, ViewState<*>>>()
        mediator.addSource(viewModel.getUserCountData()) { mediator.value = Pair(0, it) }
        mediator.addSource(viewModel.getUserTimelineData()) { mediator.value = Pair(1, it) }

        mediator.observe { response ->
            mediatorValues[response.first] = response.second
            if (mediatorValues.values.all { it is ViewState.Success }) {
                onSuccessUserTimeline()
            }
        }
    }

    private fun onLoading() {
        binding.swpFetch.isRefreshing = true
    }

    private fun onNotLoading() {
        binding.swpFetch.isRefreshing = false
    }

    private fun onSuccessUserHeader() {
        setupHeader()
    }

    private fun onSuccessUserTimeline() {
        setupStatistics()
        adapter.snapshot?.snapshotList = viewModel.timeline.toList()
        onNotLoading()
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
                    ProfileFragmentDirections.actionProfileFragmentToNewPostFragment(viewModel.timeline[position])
                navigate(directions)
            }
            val repostAction = { createRepost(viewModel.timeline[position]) }
            (cell as? TimelinePostCell)?.set(
                viewModel.timeline[position],
                repostAction = repostAction,
                quoteAction = quoteAction
            )
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
            fetchProfileTimeline()
            adapter.notifyDataSetChanged()
        }
    }

    private fun onError(stringRes: Int) {
        showSnack(message = stringRes, SnackBarType.Error)
    }
}