package com.edsondev26.folkloripedia.ui.category_detail

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.edsondev26.folkloripedia.databinding.FragmentMusicBinding
import com.edsondev26.folkloripedia.ui.category_detail.adapter.MusicDetailFragmentViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MusicFragment : Fragment() {
    private val musicViewModel by viewModels<CategoryDetailFragmentViewModel>()

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewHolder: MusicDetailFragmentViewHolder

    private var mediaPlayer: MediaPlayer? = null
    private var soundUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewHolder = MusicDetailFragmentViewHolder(view)
        val itemId = requireArguments().getString("ITEM_ID", "")
        musicViewModel.fetchMusicById(itemId)

        initUI()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun initUI() {
        getMusicItem()
        initUIState()
        initListeners()
    }


    private fun getMusicItem() {
        lifecycleScope.launch {
            musicViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.pbItemInfo.visibility = View.VISIBLE
                    binding.musicPreview.visibility = View.GONE
                } else {
                    binding.pbItemInfo.visibility = View.GONE
                    binding.musicPreview.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initListeners() {
        binding.fabPlayMusic.setOnClickListener {
            if (mediaPlayer == null) {
                playAudio()
            } else {
                releaseMediaPlayer()
            }
        }
    }

    private fun playAudio() {
        try {
            mediaPlayer = MediaPlayer().apply {
                isLooping = false
                setDataSource(soundUrl)

                setOnPreparedListener {
                    start()
                }

                setOnCompletionListener {
                    releaseMediaPlayer()
                }

                setOnErrorListener { _, ex, extra ->
                    Log.e("MediaPlayer", "Error occurred. Exception: $ex, Extra: $extra")
                    releaseMediaPlayer()
                    true
                }

                prepareAsync()
            }
        } catch (e: Exception) {
            Log.e("MediaPlayer", "Error setting up MediaPlayer", e)
            releaseMediaPlayer()
        }
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                musicViewModel.musicItem.collect { musicItem ->
                    musicItem?.let {
                        soundUrl = it.sound
                        viewHolder.renderMusic(it) { }
                    }
                }
            }
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            } catch (e: Exception) {
                Log.e("MediaPlayer", "Error releasing MediaPlayer", e)
            } finally {
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }
}