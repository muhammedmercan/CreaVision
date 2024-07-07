package com.ai.creavision.presentation.your_arts

import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentYourArtsBinding
import com.ai.creavision.presentation.home.ArtStyleAdapter
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.review.testing.FakeReviewManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class YourArtsFragment : Fragment() {

    @Inject
    lateinit  var yourArtsAdapter: YourArtsAdapter

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



        binding.progressBar.visibility = View.VISIBLE

        viewModel.getImages()
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.recyclerViewYourArts.layoutManager = layoutManager
        binding.recyclerViewYourArts.adapter = yourArtsAdapter
    }


    private fun observeLiveData() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.liveData.observe(viewLifecycleOwner, Observer { imageResponse ->

                    binding.progressBar.visibility = View.GONE

                    imageResponse?.let {
                        if (!imageResponse.isNullOrEmpty()) {
                            yourArtsAdapter.photoFiles = viewModel.liveData.value
                            binding.txtNoFavorites.visibility = View.GONE
                        } else {
                            yourArtsAdapter.photoFiles = emptyList()
                            binding.txtNoFavorites.visibility = View.VISIBLE


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

    override fun onDestroyView() {
        binding.recyclerViewYourArts.setAdapter(null);
        _binding = null
        super.onDestroyView()


    }

    private fun onClick() {

        yourArtsAdapter.setOnItemClickListener {
            viewModel.fromHome = false
            val args = Bundle()
            args.putSerializable("favorite", it)
            findNavController().navigate(
                R.id.action_yourArtsFragment_to_singleYourArtFragment,
                args
            )
        }
    }
}