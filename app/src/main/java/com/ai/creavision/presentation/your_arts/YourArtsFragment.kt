package com.ai.creavision.presentation.your_arts

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.adapty.Adapty
import com.adapty.ui.AdaptyUI
import com.adapty.utils.AdaptyResult
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentYourArtsBinding
import com.ai.creavision.presentation.results.AllResultsViewModel
import com.ai.creavision.utils.DataHolder
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class YourArtsFragment @Inject constructor(
    private val yourArtsAdapter: YourArtsAdapter

) : Fragment() {


    private var _binding: FragmentYourArtsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: YourArtsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentYourArtsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(YourArtsViewModel::class.java)


        onClick()
        //onScroll()
        observeLiveData()

        viewModel.getImages()
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerViewYourArts.layoutManager = layoutManager
        binding.recyclerViewYourArts.adapter = yourArtsAdapter

    }

    private fun onScroll() {

        binding.recyclerViewYourArts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val canScrollDown = recyclerView.canScrollVertically(1)

                    if (!canScrollDown) {
                        viewModel.getImages()
                    }
                }
            }
        })
    }

    private fun observeLiveData() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.liveData.observe(viewLifecycleOwner, Observer { imageResponse ->

                    Toast.makeText(requireContext(), "Geldi", Toast.LENGTH_LONG)
                        .show()

                    imageResponse?.let {

                        if (!imageResponse.isNullOrEmpty()) {

                            yourArtsAdapter.photoFiles = viewModel.liveData.value
                            //yourArtsAdapter.photoFiles = DataModel.deneme

                        }
                    }
                }
                )
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.fromHome = true
    }





    private fun onClick() {

        yourArtsAdapter.setOnItemClickListener {
            viewModel.fromHome = false
            val args = Bundle()
            args.putSerializable("favorite",it)
            findNavController().navigate(
                R.id.action_yourArtsFragment_to_singleYourArtFragment,
                args
            )
        }
    }
}