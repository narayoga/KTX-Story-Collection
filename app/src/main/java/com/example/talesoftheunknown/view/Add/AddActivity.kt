package com.example.talesoftheunknown.view.Add

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.talesoftheunknown.Injection
import com.example.talesoftheunknown.databinding.ActivityAddBinding
import com.example.talesoftheunknown.view.Main.MainActivity
import com.example.talesoftheunknown.view.ViewModelFactory
import com.example.talesoftheunknown.view.getImageUri
import com.example.talesoftheunknown.view.reduceFileImage
import com.example.talesoftheunknown.view.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddActivity  : AppCompatActivity() {
    private val viewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(this, Injection.providePagingRepository(this))
    }
    private lateinit var binding: ActivityAddBinding
    private lateinit var currentDescription: String
    private var currentImageUri: Uri? = null


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.galleryButton.setOnClickListener{startGallery()}
        binding.cameraButton.setOnClickListener{startCamera()}
        binding.uploadButton.setOnClickListener{uploadStory()}
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun setDescription() {
        binding.apply {
            val query = descriptionEditText.text?.toString()

            if (query.isNullOrEmpty()) {
                Toast.makeText(this@AddActivity, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return
            }

            currentDescription = query
        }
    }

    private fun uploadStory() {

        binding.apply {
            val query = descriptionEditText.text?.toString()

            if (query.isNullOrEmpty()) {
                Toast.makeText(this@AddActivity, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return
            }

            currentDescription = query
        }

        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val intent = Intent(this, MainActivity::class.java)

            val requestBody = currentDescription.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            viewModel.postStory(multipartBody, requestBody)
            AlertDialog.Builder(this@AddActivity).apply {
                setTitle("Success")
                setMessage("story anda sudah berhasil dibuat")
                setPositiveButton("Next") { _, _ ->
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                create()
                show()
            }
        } ?: Toast.makeText(this, "Pilih gambar dahulu", Toast.LENGTH_SHORT).show()
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}