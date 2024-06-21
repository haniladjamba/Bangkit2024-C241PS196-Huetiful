package com.bangkit2024.huetiful.ui.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit2024.huetiful.R
import com.bangkit2024.huetiful.data.Result
import com.bangkit2024.huetiful.data.remote.response.PredictPalateResponse
import com.bangkit2024.huetiful.databinding.FragmentHomeBinding
import com.bangkit2024.huetiful.ui.ViewModelFactory.ViewModelFactory
import com.bangkit2024.huetiful.ui.activity.result.ResultActivity
import com.bangkit2024.huetiful.ui.utils.getImageUri
import com.bangkit2024.huetiful.ui.utils.uriToFile
import com.bumptech.glide.Glide
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance()
    }
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var destinationUri: Uri? = null

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = data?.let { UCrop.getOutput(it) }
            showImage()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(it) }
            makeToast(cropError?.message.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTvTitle()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAction() {
        binding.btnGallery.setOnClickListener {
            openGallery()
        }
        binding.btnCamera.setOnClickListener {
            openCamera()
        }
        binding.btnAnalyze.setOnClickListener {
            predictPalate()
        }
    }

    private fun predictPalate() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext())
            Log.d(TAG_IMAGE_FILE, "showImage ${imageFile.path}")

            homeViewModel.predictPalate(imageFile)

            lifecycleScope.launch {
                homeViewModel.predictPalateState.collect { result ->
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> showLoading(false)
                        is Result.Error -> {
                            showPredictPalateError(result.error)
                            showLoading(false)
                        }
                    }
                }
            }
            homeViewModel.predictPalateResult.observe(requireActivity()) { palate ->
                if (palate != null) {
                    Log.d(TAG, "palate : $palate")
                    navigateToDetail(palate)
                }
            }
        }
    }

    private fun showPredictPalateError(error: String) {
        makeToast(error)
    }

    private fun navigateToDetail(palateResponse: PredictPalateResponse) {
        val intent = Intent(requireContext(), ResultActivity::class.java)
        val bundle = Bundle()
        val palateArrayList : ArrayList<String?> = palateResponse.predictedPalette.toCollection(ArrayList())
        bundle.putStringArrayList("colorList", palateArrayList)
        bundle.putString("extractedSkinTone", palateResponse.extractedSkinTone)
        intent.putExtras(bundle)

        val optionCompact: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                Pair(binding.ivPreviewImage, "itemImage")
            )

        intent.putExtra("itemImage", currentImageUri.toString())
        startActivity(intent, optionCompact.toBundle())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbHome.isVisible = isLoading
    }

    private fun setTvTitle() {
        val textTitle = getString(R.string.home_title)
        val textEnd = getString(R.string.home_title_end)
        val spanTextTitle = SpannableString(getString(R.string.tone))
        spanTextTitle.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_700)), 0, spanTextTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvTitle.text = textTitle
        binding.tvTitle.append(spanTextTitle)
        binding.tvTitle.append(textEnd)
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
                .into(binding.ivPreviewImage)
        } ?: makeToast(getString(R.string.image_must_not_be_empty))
    }

    private fun cropImage() {
        destinationUri = getImageUri(requireContext())
        currentImageUri.let {
            if (it != null) {
                UCrop.of(it, destinationUri!!)
                    .start(requireContext(), this, UCrop.REQUEST_CROP)
            } else {
                makeToast(getString(R.string.cannot_find_image))
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            cropImage()
            showImage()
        } else {
            Log.d("Photo picker", "No media selected")
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

    companion object {
        const val TAG = "HomeFragment"
        const val TAG_IMAGE_FILE = "Image file"
    }

}