package co.fouani.healthinfo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import co.fouani.healthinfo.R
import co.fouani.healthinfo.databinding.FragmentHomeBinding
import co.fouani.healthinfo.ui.viewmodels.HomeFragmentViewModel
import co.fouani.healthinfo.ui.viewmodels.MainActivityViewModel
import co.fouani.healthinfo.ui.widgets.GrandProgressWidget
import co.fouani.healthinfo.ui.widgets.PostsRowWidget
import co.fouani.healthinfo.utils.Utility
import co.fouani.healthinfo.utils.Utility.exists

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mainVM: MainActivityViewModel
    lateinit var homeViewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.run {
            mainVM =
                ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
            mainVM.updateTitle(this.getString(R.string.my_journy))
        }
        homeViewModel = HomeFragmentViewModel(requireContext())
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        homeViewModel.loadPage(requireActivity())
        homeViewModel.page.observe(requireActivity(), { page ->
            binding.sections.removeAllViews()
            if (exists(page.watchList)) {
                val postsWidget = PostsRowWidget(requireActivity(), null).setWidget(
                    page.watchList,
                    Utility.getSection(0)
                )
                binding.sections.addView(postsWidget)
            }
            if (exists(page.section1)) {
                val postsWidget = PostsRowWidget(requireActivity(), null).setWidget(
                    page.section1,
                    Utility.getSection(1)
                )
                binding.sections.addView(postsWidget)
            }
            if (exists(page.section2)) {
                val postsWidget = PostsRowWidget(requireActivity(), null).setWidget(
                    page.section2,
                    Utility.getSection(2)
                )
                binding.sections.addView(postsWidget)
            }
            if (exists(page.section3)) {
                val postsWidget = PostsRowWidget(requireActivity(), null).setWidget(
                    page.section3,
                    Utility.getSection(3)
                )
                binding.sections.addView(postsWidget)
            }
        })
        homeViewModel.grandProgress.observe(requireActivity(), {
            if (it != null && it.progress > 0 && !homeViewModel.grandProgressAdded) {
                val progressWidget = GrandProgressWidget(requireActivity(), null).setWidget(
                    it
                )
                homeViewModel.grandProgressAdded = true
                binding.mainContent.addView(progressWidget, 0)
            }
        })
        return binding.root
    }

}