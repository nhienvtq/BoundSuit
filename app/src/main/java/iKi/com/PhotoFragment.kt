package iKi.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import coil.load
import iKi.com.databinding.FragmentPhotoBinding
import iKi.com.networkRESTful.PhotoModel


class PhotoFragment : Fragment() {
    private lateinit var _binding: FragmentPhotoBinding
    private val binding get() = _binding
    private lateinit var insPhotoViewModel: PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insPhotoViewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)

        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    private var jsonarray: List<PhotoModel>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position: Int
        insPhotoViewModel.status.observe(viewLifecycleOwner, Observer {

            binding.loadingImage.visibility = View.VISIBLE
            when (insPhotoViewModel.status.value) {
                PhotoApiStatus.DONE -> {
                    setDataDisplay(0)
                }
                PhotoApiStatus.LOADING -> binding.loadingImage.visibility = View.VISIBLE
            }
        })
        insPhotoViewModel.model.observe(viewLifecycleOwner, Observer {
            jsonarray = insPhotoViewModel.model.value!!
        })
        var translateAnimation: Animation
        binding.rightBtn.setOnClickListener(){
            if (insPhotoViewModel.indexArray < jsonarray?.size!!){
                insPhotoViewModel.indexArray = insPhotoViewModel.indexArray + 1
                position = insPhotoViewModel.indexArray
                setDataDisplay(position)
                translateAnimation = AnimationUtils.loadAnimation(
                    context,
                    R.anim.translate_from_right
                )
                binding.photoRESTImage.startAnimation(translateAnimation)
            } else {
                Toast.makeText(requireContext(), "last picture reached", Toast.LENGTH_SHORT).show()
            }
        }

        binding.leftBtn.setOnClickListener(){
            if (insPhotoViewModel.indexArray > 0){
                insPhotoViewModel.indexArray = insPhotoViewModel.indexArray - 1
                position = insPhotoViewModel.indexArray
                setDataDisplay(position)
                translateAnimation = AnimationUtils.loadAnimation(
                    context,
                    R.anim.translate_from_left
                )
                binding.photoRESTImage.startAnimation(translateAnimation)
            } else {
                Toast.makeText(requireContext(), "first picture reached", Toast.LENGTH_SHORT).show()
            }
        }

        binding.lifecycleOwner = this
        binding.viewModel = insPhotoViewModel
    }

    private fun setDataDisplay(position: Int){
        binding.loadingImage.visibility = View.INVISIBLE
        binding.photoRESTImage.visibility = View.VISIBLE
        binding.photoRESTImage.load(jsonarray?.get(position)?.getUrl()){
        }
        binding.idtextView.setText(jsonarray?.get(position)?.getid())
        binding.typetextView.setText(jsonarray?.get(position)?.getprice())
        binding.pricetextView.setText(jsonarray?.get(position)?.gettype())
    }
}