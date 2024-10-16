package com.edsondev26.folkloripedia.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.edsondev26.folkloripedia.R
import com.edsondev26.folkloripedia.databinding.FragmentHomeBinding
import com.edsondev26.folkloripedia.domain.model.ArticleModel
import com.edsondev26.folkloripedia.domain.model.HomeArticleItemInfo.*
import com.edsondev26.folkloripedia.ui.home.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val list = mutableListOf<CarouselItem>()

    private lateinit var homeAdapter: HomeAdapter

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
        initUI()
    }

    private fun initUI() {
        initCarousel()
        initArticlesList()
        initUIState()
    }

    private fun initArticlesList() {
        homeAdapter = HomeAdapter(onItemSelected = {
            val type: ArticleModel = when (it) {
                Dance -> ArticleModel.Dances
                Music -> ArticleModel.Music
                Art -> ArticleModel.Arts
                Myths -> ArticleModel.Myths
            }
            findNavController().navigate(
                // HomeFragmentDirections was generated automatically -- Add argument on categoryDetailActivity  main_graph
                HomeFragmentDirections.actionHomeFragmentToCategoryDetailActivity(type)
            )
        })
        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.home.collect {
                    homeAdapter.updateList(it)
                }
            }
        }
    }

    private fun initCarousel() {
        list.clear()
        list.add(CarouselItem(imageDrawable = R.drawable.banner_folk))
        list.add(CarouselItem(imageDrawable = R.drawable.banner_2))
        list.add(CarouselItem(imageDrawable = R.drawable.banner_3))
        list.add(CarouselItem(imageDrawable = R.drawable.banner_4))
        binding.icarMain.addData(list)
    }
}