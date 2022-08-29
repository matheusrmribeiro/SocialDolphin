package com.example.socialdolphin.features.timeline.presenter.newpost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.socialdolphin.R
import com.example.socialdolphin.core.utils.ViewState
import com.example.socialdolphin.databinding.NewpostFragmentBinding
import com.example.socialdolphin.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPostFragment : BaseFragment<NewpostFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> NewpostFragmentBinding
        get() = NewpostFragmentBinding::inflate

    companion object {
        private const val MAX_LENGTH = 777
    }

    private val viewModel by viewModels<NewPostViewModel>()
    private val args by navArgs<NewPostFragmentArgs>()

    override fun setupFragment() {
        viewModel.originPost = args.originPost
        setupButtons()
        setupTextContent()
        setupButtons()
        setupQuote()
    }

    private fun setupButtons() {
        changeButtonState(false)
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnCreate.setOnClickListener {
            viewModel.createPost(binding.edtContent.text.toString()).observe { response ->
                when (response) {
                    is ViewState.Loading -> { }
                    is ViewState.Success -> onSuccess(response.result)
                    is ViewState.Error -> onError(response.messageRes ?: 0)
                }
            }
        }
    }

    private fun onSuccess(stringRes: Int) {
        showSnack(message = stringRes, SnackBarType.Success)
        findNavController().popBackStack()
    }

    private fun onError(stringRes: Int) {
        showSnack(message = stringRes, SnackBarType.Error)
    }

    private fun setupTextContent() {
        binding.edtContent.requestFocus()
        binding.edtContent.addTextChangedListener { onTextChange(it?.length ?: 0) }
    }

    private fun setupQuote() {
        with(binding.viewQuote) {
            if (args.originPost == null) {
                ctlQuote.visibility = View.GONE
            } else {
                ctlQuote.visibility = View.VISIBLE
                txtQuoteCreator.text = viewModel.originPost!!.creator!!.name
                txtQuoteContent.text = viewModel.originPost!!.content
            }
        }
    }

    private fun onTextChange(length: Int) {
        val lengthText = "${length}/$MAX_LENGTH"
        binding.txtLength.text = lengthText
        changeButtonState(length > 0)
    }

    private fun changeButtonState(enabled: Boolean) {
        if (enabled) {
            binding.btnCreate.isEnabled = true
            binding.btnCreate.setBackgroundColor(
                resources.getColor(
                    R.color.primary_color,
                    null
                )
            )
        } else {
            binding.btnCreate.isEnabled = false
            binding.btnCreate.setBackgroundColor(
                resources.getColor(
                    R.color.disable_color,
                    null
                )
            )
        }
    }

}