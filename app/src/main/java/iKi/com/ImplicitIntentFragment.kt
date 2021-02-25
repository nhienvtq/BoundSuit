package iKi.com

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import iKi.com.databinding.FragmentImplicitIntentBinding


class ImplicitIntentFragment : Fragment() {

    private lateinit var _binding: FragmentImplicitIntentBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_implicit_intent,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        triggerButton(binding.cameraBtn)
        triggerButton(binding.mapBtn)
        triggerButton(binding.dialBtn)
    }

    private fun cameraIntent(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 1)
    }

    private fun mapIntent(){
        val mapIntent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
        startActivityForResult(mapIntent, 1)
    }

    private fun dialIntent(){
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + binding.editTextPhone.text.toString()))
        startActivity(dialIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> {
                if (resultCode == Activity.RESULT_OK || data != null){
                    binding.captureimageView.setImageBitmap(data?.extras?.get("data") as Bitmap)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    private fun triggerButton(view: CardView){
        view.setOnTouchListener() { _: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.cardElevation = 8f
                    view.alpha = 0.5f
                    view.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(resources.getString(R.color.colorbuttonPress)))
                }
                MotionEvent.ACTION_UP -> {
                    view.cardElevation = 24f
                    view.alpha = 1f
                    view.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(resources.getString(R.color.colorbutton)))
                    when (view) {
                        binding.cameraBtn -> cameraIntent()
                        binding.mapBtn -> mapIntent()
                        binding.dialBtn -> dialIntent()
                    }
                }
            }
            true
        }
    }
}