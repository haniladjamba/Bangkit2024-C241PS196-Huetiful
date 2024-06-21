package com.bangkit2024.huetiful.ui.fragments.find

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.PredictPairResponse
import com.bangkit2024.huetiful.databinding.FragmentFindBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.ViewModelFactory
import com.bangkit2024.huetiful.ui.activity.resultpair.ResultPairActivity
import com.bangkit2024.huetiful.ui.utils.getImageUri
import com.bangkit2024.huetiful.ui.utils.uriToFile
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

class FindFragment : Fragment() {

    private val findViewModel by viewModels<FindViewModel> {
        ViewModelFactory.getInstance()
    }
    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var destinationUri: Uri? = null

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = data?.let { UCrop.getOutput(it) }
            showImage()
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(it) }
            makeToast(cropError?.message.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTvTitle()
        setupAction()
    }

    private fun setupAction() {
        binding.btnGalleryFind.setOnClickListener {
            openGallery()
        }
        binding.btnCameraFind.setOnClickListener {
            openCamera()
        }
        binding.btnAnalyzeFind.setOnClickListener {
            predictPair()
        }
    }

    private fun predictPair() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext())
            val sharedPreference = requireActivity().getSharedPreferences("user_palate_preference", Context.MODE_PRIVATE)
            val jsonPalateString = sharedPreference.getString("current_palate", "")
            val palateList = Gson().fromJson(jsonPalateString, Array<String>::class.java).toList()

            findViewModel.predictPair(imageFile, palateList)

            lifecycleScope.launch {
                findViewModel.predictPairState.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> showLoading(false)
                        is Result.Error -> {
                            showLoading(false)
                            showPredictPairError(result.error)
                        }
                    }
                }
            }
            findViewModel.predictPairResult.observe(requireActivity()) { palate ->
                if (palate != null) {
                    navigateToResultPair(palate)
                }
            }
        }
    }

    private fun navigateToResultPair(palate: PredictPairResponse) {
        val intent = Intent(requireContext(), ResultPairActivity::class.java)
        val bundle = Bundle()
        bundle.putString("dominantColor", palate.dominantColor)
        bundle.putString("predictedColor", palate.predictedColor)
        intent.putExtras(bundle)

        val optionCompact: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                Pair(binding.ivPreviewImageFind, "itemImage")
            )

        intent.putExtra("itemImage", currentImageUri.toString())
        startActivity(intent, optionCompact.toBundle())
    }

    private fun showPredictPairError(error: String) {
        makeToast(error)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbFind.isVisible = isLoading
    }

    private fun setTvTitle() {
        val textTitle = getString(R.string.find_title)
        val textEnd = getString(R.string.home_title_end)
        val spanTextTitle = SpannableString(getString(R.string.pair))
        spanTextTitle.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_700)), 0, spanTextTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitleFind.text = textTitle
        binding.tvTitleFind.append(spanTextTitle)
        binding.tvTitleFind.append(textEnd)
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let {
            Glide.with(requireContext())
                .load(it)
                .into(binding.ivPreviewImageFind)
        } ?: makeToast(getString(R.string.image_must_not_be_empty))
    }

    private fun cropImage() {
        destinationUri = getImageUri(requireContext())
        currentImageUri?.let {
            UCrop.of(it, destinationUri!!)
                .start(requireContext(), this, UCrop.REQUEST_CROP)
        } ?: makeToast(getString(R.string.cannot_find_image))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            cropImage()
            showImage()
        } else {
            makeToast(getString(R.string.no_media_selected))
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            cropImage()
            showImage()
        } else {
            makeToast(getString(R.string.error_opening_camera))
        }
    }
}