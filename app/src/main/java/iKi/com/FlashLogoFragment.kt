package iKi.com

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class FlashLogoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({findNavController().navigate(R.id.action_flashLogoFragment_to_homeFragment)},3000)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flash_logo, container, false)
    }
}