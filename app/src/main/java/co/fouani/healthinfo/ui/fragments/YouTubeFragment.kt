package co.fouani.healthinfo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.databinding.FragmentYoutubeBinding
import co.fouani.healthinfo.ui.viewmodels.MainActivityViewModel
import co.fouani.healthinfo.ui.viewmodels.VisitedPostsViewModel
import co.fouani.healthinfo.utils.Utility.getVideoId
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import kotlin.math.roundToInt


class YouTubeFragment : Fragment() {
    private lateinit var homeVM: MainActivityViewModel
    private lateinit var viewModel: VisitedPostsViewModel
    private lateinit var binding: FragmentYoutubeBinding
    var player: YouTubePlayer? = null
    val tracker = YouTubePlayerTracker()
    lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.run {
            homeVM =
                ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        }
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_youtube, container, false)
        post = YouTubeFragmentArgs.fromBundle(requireArguments()).post!!
        homeVM.updateTitle(post.title)
        viewModel = ViewModelProvider(this).get(VisitedPostsViewModel::class.java)
        viewModel.setPost(post, requireActivity())
        //viewModel.visitedPost.observe(requireActivity(), {
        //    if (it != null && it.progress > 1) {
        //        post.progress = it.progress
        //    }
        //})
        setupYouTubeView()
        return binding.root
    }

    private fun setupYouTubeView() {
        lifecycle.addObserver(binding.youtubeView)
        binding.youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                player = youTubePlayer
                val videoId = getVideoId(post.url)
                player?.loadVideo(videoId, post.progress.toFloat())
                player?.addListener(tracker)
                post.total = tracker.videoDuration.roundToInt()

            }
        })
    }

    override fun onPause() {
        super.onPause()
        post.progress = tracker.currentSecond.roundToInt()
        if (viewModel.visited)
            viewModel.updateVisitedPostProgress(post)
        else
            viewModel.addVisitedPost(post)
    }
}