package iKi.com

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import iKi.com.databinding.FragmentAddProfileBinding
import iKi.com.profileData.Profile
import iKi.com.profileData.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_control.*
import java.util.*


class AddProfileFragment : Fragment() {
    private lateinit var binding: FragmentAddProfileBinding
    private lateinit var insProfileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_profile,container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.birthdayTextfield.setOnTouchListener(){ view: View, motionEvent: MotionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_UP -> {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val CalendarDialog = DatePickerDialog(requireContext(),
                        { view, insYear, insMonth, insDay ->
                            binding.birthdayTextfield.setText("$insDay/$insMonth/$insYear")
                        }, year, month, day)
                    CalendarDialog.show()
                }
            }
            return@setOnTouchListener true
        }
        binding.floatingAddButton.setOnClickListener(){
            val Name = "testName1"
            val Age = 12
            val Nation = "VN"
            val Gender = "Female"
            val PhoneNumber = 223
            val Email = "testEmail1"
            val Image = R.drawable.ic_baseline_face_24
            if(inputCheck(Name)){
                val profile = Profile(0, Name, Age, Nation , Gender, PhoneNumber,
                Email, Image)
                insProfileViewModel.addProfile(profile)
                Toast.makeText(context,"Profile added", Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_addProfileFragment_to_controlFragment)
            activity?.findViewById<ViewPager2>(R.id.viewPager)?.currentItem = 1
        }
    }


    private fun inputCheck(Name: String):Boolean{
        return !(TextUtils.isEmpty(Name))
    }
}