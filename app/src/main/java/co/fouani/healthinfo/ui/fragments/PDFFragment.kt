package co.fouani.healthinfo.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.databinding.FragmentPdfBinding
import co.fouani.healthinfo.ui.viewmodels.MainActivityViewModel
import co.fouani.healthinfo.ui.viewmodels.VisitedPostsViewModel
import com.google.android.material.snackbar.Snackbar
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile


class PDFFragment : Fragment(), DownloadFile.Listener {
    lateinit var mainActivityViewModel: MainActivityViewModel
    var adapter: PDFPagerAdapter? = null
    var destinationPath = ""
    private lateinit var viewModel: VisitedPostsViewModel
    private lateinit var binding: FragmentPdfBinding
    lateinit var remotePDFViewPager: RemotePDFViewPager
    var currentPosition = 0
    lateinit var post: Post
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        activity?.run {
            mainActivityViewModel =
                ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf, container, false)
        post = PDFFragmentArgs.fromBundle(requireArguments()).post!!
        currentPosition = post.progress
        mainActivityViewModel.updateTitle(post.title)
        viewModel = ViewModelProvider(this).get(VisitedPostsViewModel::class.java)
        viewModel.setPost(post,requireActivity())

        remotePDFViewPager = RemotePDFViewPager(context, post.url, this)
        remotePDFViewPager.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
            }
        })
        return binding.root
    }


    override fun onSuccess(url: String?, destinationPath: String?) {
        if (context != null) {
            this.destinationPath = destinationPath!!
            adapter = PDFPagerAdapter(context, this.destinationPath)
            remotePDFViewPager.setAdapter(adapter)
            binding.mainContent.addView(remotePDFViewPager)
            if (currentPosition > 0)
                Snackbar.make(binding.mainContent, R.string.continue_reading, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.okay) {
                        try {
                            remotePDFViewPager.currentItem = currentPosition
                        } catch (e: java.lang.Exception) {
                            Log.d("pdffragment", e.message.toString())
                        }
                    }
                    .show()
        }
    }


    override fun onFailure(e: Exception?) {
        Toast.makeText(requireContext(), "An error occurred.", Toast.LENGTH_LONG).show()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
        binding.progress.visibility = if (progress == total) View.GONE else View.VISIBLE
    }

    override fun onDestroy() {
        try {
            if (adapter != null)
                adapter!!.close()
        } catch (e: Exception) {
        }
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        post.total = adapter!!.count
        post.progress = currentPosition
        if (viewModel.visited)
            viewModel.updateVisitedPostProgress(post)
        else
            viewModel.addVisitedPost(post)
    }

    protected fun hasExternalStoragePermissions(): Boolean {
        val hasReadPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return hasReadPermission && hasWritePermission
    }

    protected fun requestExternalStoragePermissions() {
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(requireActivity(), permissions, 1)
    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                // Permission is granted. Continue the action or workflow in your
//                // app.
//            } else {
//                Toast.makeText(requireContext(), "Permission is necessary!", Toast.LENGTH_LONG).show()
//                requireActivity().supportFragmentManager.popBackStack()
//            }
//        }
//    }
//
}