package com.mobil.wallpaper_app.ui.photo

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mobil.wallpaper_app.R
import com.mobil.wallpaper_app.databinding.FragmentPhotoBinding
import com.mobil.wallpaper_app.model.ImageModel
import com.mobil.wallpaper_app.room.database.AppDatabase
import com.mobil.wallpaper_app.room.entity.ImageEntity
import com.mobil.wallpaper_app.service.*
import com.squareup.picasso.Picasso
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageModel: ImageModel
    private var clickInfo = -1
    private lateinit var bitmap: Bitmap
    lateinit var appDatabase: AppDatabase
    private var isLiked = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPhotoBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInstance(requireContext())

        imageModel = arguments?.getSerializable("image") as ImageModel

        Picasso.get().load(imageModel.urls.regular).into(binding.image)

        loadViewsBlur()
        CoroutineScope(Dispatchers.IO).launch {
            bitmap = Picasso.get().load(imageModel.urls.regular).get()
        }

        isLiked = appDatabase.imageDao().getIsLiked(imageModel.id)

        if (isLiked == 1) {
            binding.btnLike.setImageResource(R.drawable.ic_photo_liked);
        }

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fragmentInAnim(view1, view2) //Animation for PhotoFragment views(buttons)
            clickInfo = 0

            btnBack.setOnClickListener {
                fragmentOutAnim(requireView(), view1, view2)
                clickInfo = -1
            }

            btnShare.setOnClickListener {
                shareImageAndText(bitmap, requireContext())
            }

            btnDownload.setOnClickListener {
                val fileName = generateFileName()
                saveImageToStorage(requireContext(), fileName, bitmap)
            }


            btnInfo.setOnClickListener {
                tvUrl.text = imageModel.links.html
                tvAuthor.text = imageModel.user.name
                tvDimention.text = "${imageModel.width} x ${imageModel.height}"
                infoInAnimation(binding)
                clickInfo = 1
            }

            btnExit.setOnClickListener { v ->
                infoOutAnimation(binding)
                clickInfo = 0
            }

            btnInstall.setOnClickListener { v ->
                inAnimation(
                    view1,
                    view2,
                    viewBackInstall,
                    viewInstall
                )
                clickInfo = 2
            }

            btnBackInstall.setOnClickListener { v ->
                outAnimation(
                    viewBackInstall,
                    viewInstall,
                    view1,
                    view2
                )
                clickInfo = 0
            }

            btnInstallHome.setOnClickListener { v ->
                setToWallPaper(
                    context,
                    bitmap,
                    InstallType.FLAG_SYSTEM
                )
            }

            btnInstallLock.setOnClickListener { v ->
                setToWallPaper(
                    context,
                    bitmap,
                    InstallType.FLAG_LOCK
                )
            }

            btnInstallAll.setOnClickListener { v ->
                setToWallPaper(
                    context,
                    bitmap,
                    InstallType.FLAG_SYSTEM_LOCK
                )
            }

            btnFilter.setOnClickListener {
                findNavController().navigate(
                    R.id.action_photoFragment_to_filterFragment,
                    Bundle().apply {
                        putSerializable("image", imageModel)
                    })
            }

            binding.btnLike.setOnClickListener { v ->
                if (isLiked == 0) {
                    binding.btnLike.setImageResource(R.drawable.ic_photo_liked)
                    Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
                    val imageEntity = ImageEntity(
                        imageModel.id,
                        imageModel.links.html,
                        imageModel.urls.thumb,
                        imageModel.urls.regular,
                        imageModel.user.name,
                        imageModel.height,
                        imageModel.width
                    )
                    appDatabase.imageDao().addImage(imageEntity)
                    isLiked = 1
                } else {
                    binding.btnLike.setImageResource(R.drawable.ic_photo_like)
                    Toast.makeText(context, "Unliked", Toast.LENGTH_SHORT).show()
                    appDatabase.imageDao().deleteImage(imageModel.id)
                    isLiked = 0
                }
            }
        }


    }

    private fun loadViewsBlur() {
        binding.apply {
            loadBlur(back)
            loadBlur(share)
            loadBlur(info)
            loadBlur(download)
            loadBlur(install)
            loadBlur(filter)
            loadBlur(like)
            loadBlur(exit)
            loadBlur(blurDialog)
            loadBlur(installAll)
            loadBlur(installHome)
            loadBlur(installLock)
            loadBlur(backInstall)
        }
    }

    private fun loadBlur(blurView: BlurView) {
        val radius = 20f
        blurView.setupWith(binding.root)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setOverlayColor(Color.parseColor("#40000000"))
            .setHasFixedTransformationMatrix(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}