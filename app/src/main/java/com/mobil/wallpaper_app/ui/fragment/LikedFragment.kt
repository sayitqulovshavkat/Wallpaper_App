package com.mobil.wallpaper_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mobil.wallpaper_app.R
import com.mobil.wallpaper_app.adapters.LikeAdapter
import com.mobil.wallpaper_app.databinding.FragmentLikedBinding
import com.mobil.wallpaper_app.model.ImageModel
import com.mobil.wallpaper_app.model.Links
import com.mobil.wallpaper_app.model.Urls
import com.mobil.wallpaper_app.model.User
import com.mobil.wallpaper_app.room.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikedFragment : Fragment() {

    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!
    lateinit var appDatabase: AppDatabase
    private var likedList: ArrayList<ImageModel> = ArrayList()
    lateinit var likeAdapter: LikeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        val allImage = appDatabase.imageDao().getAllImage()

        CoroutineScope(Dispatchers.Default).launch {
            likedList.clear()
            allImage.forEach {
                val imageModel = ImageModel(
                    it.id,
                    it.height,
                    Links(it.links),
                    Urls(it.urlsRegular, it.urlsSmall),
                    User(it.userName),
                    it.width
                )
                likedList.add(imageModel)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        likeAdapter = LikeAdapter(likedList, object : LikeAdapter.OnImageClickListener {
            override fun onImageClick(imageModel: ImageModel) {
                findNavController().navigate(R.id.photoFragment, Bundle().apply {
                    putSerializable("image", imageModel)
                })
            }

        })
        binding.rv.adapter = likeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}