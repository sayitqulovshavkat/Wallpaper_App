package com.mobil.wallpaper_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mobil.wallpaper_app.R
import com.mobil.wallpaper_app.adapters.ImageAdapter
import com.mobil.wallpaper_app.databinding.FragmentRandomBinding
import com.mobil.wallpaper_app.model.ImageModel
import com.mobil.wallpaper_app.ui.viewmodel.RandomViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RandomFragment : Fragment(), CoroutineScope {

    private lateinit var randomViewModel: RandomViewModel
    private var _binding: FragmentRandomBinding? = null

    private val binding get() = _binding!!
    lateinit var imageAdapter: ImageAdapter
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        randomViewModel =
            ViewModelProvider(this)[RandomViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageAdapter = ImageAdapter(object : ImageAdapter.OnImageClickListener {
            override fun onImageClick(imageModel: ImageModel) {
                findNavController().navigate(R.id.photoFragment, Bundle().apply {
                    putSerializable("image", imageModel)
                })
            }
        })
        launch {
            randomViewModel.flow.catch {

            }.collect {
                imageAdapter.submitData(it)
            }
        }

//        launch {
//            randomViewModel.flow.catch {
//
//            }.collect {
//                imageAdapter.submitData(it)
//            }
//        }

        binding.rv.adapter = imageAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val coroutineContext: CoroutineContext
        get() = job
}