package com.edsondev26.folkloripedia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

//@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val list = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // Alt + insert
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCarousel()
    }

    private fun initCarousel() {
        list.clear()
        list.add(CarouselItem(imageDrawable = R.drawable.banner1_transparency))
        //BORRAR
        list.add(CarouselItem("https://image.api.playstation.com/vulcan/img/rnd/202010/2621/z8upfOkL4hLU1wWc2tDiAusM.png"))
        list.add(CarouselItem("https://wallpapers.com/images/hd/crash-bandicoot-surfing-fjj7ar544srysvdq.jpg"))
        list.add(CarouselItem("https://images.wallpapersden.com/image/download/crash-bandicoot-its-about-time-hd-gaming_bWdqZmiUmZqaraWkpJRoZWVlrWZrbmc.jpg"))
        binding.icarMain.addData(list)
    }
}