package co.fouani.healthinfo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.fouani.healthinfo.R
import co.fouani.healthinfo.utils.CachingHelper
import co.fouani.healthinfo.utils.CachingHelper.FIRST_OPEN

class DummyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dummy, container, false)
        view.findViewById<View>(R.id.option1).setOnClickListener {
            findNavController().navigateUp()
            CachingHelper.setBoolean(FIRST_OPEN, false, requireContext())
        }
        view.findViewById<View>(R.id.option2).setOnClickListener {
            findNavController().navigateUp()
            CachingHelper.setBoolean(FIRST_OPEN, false, requireContext())
        }
        return view
    }
}