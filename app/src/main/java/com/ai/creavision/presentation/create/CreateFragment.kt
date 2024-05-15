package com.ai.creavision.presentation.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.ai.creavision.R
import com.ai.creavision.databinding.FragmentCreateBinding
import com.ai.creavision.databinding.FragmentHomeBinding
import com.ai.creavision.domain.model.CreateImageModel
import com.ai.creavision.presentation.create.CreateViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CreateViewModel


    var prompt : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments.let {
            prompt = it?.getString("prompt").toString()!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CreateViewModel::class.java)
        onClick()

        observeLiveData()

        viewModel.createImage(CreateImageModel(prompt = prompt))

    }

    private fun observeLiveData() {

        viewModel.liveData.observe(viewLifecycleOwner, Observer {  imageResponse ->

            imageResponse.let {

                println(imageResponse.toString())

                Glide
                    .with(this)
                    .load(imageResponse.data?.get(0)?.url)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progressBar.visibility = View.GONE
                            binding.txtGenerating.visibility = View.GONE
                            return false
                        }

                    })
                    //.placeholder(R.drawable.loading_spinner)
                    .into(binding.imageView)




            }
        }
        )
    }

    private fun onClick() {

        binding.btnBack.setOnClickListener() {

            findNavController().popBackStack()

        }
    }
}