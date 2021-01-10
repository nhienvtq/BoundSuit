package iKi.com

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import iKi.com.databinding.FragmentAddProfileBinding
import iKi.com.profileData.Profile
import iKi.com.profileData.ProfileViewModel
import java.util.*


class AddProfileFragment : Fragment() {
    private lateinit var binding: FragmentAddProfileBinding
    private lateinit var insProfileViewModel: ProfileViewModel
    private var Name        : String? = null
    private var Age         : Int? = null
    private var Nation      : String? = null
    private var Gender      : String? = null
    private var PhoneNumber : Int? = null
    private var Email       : String? = null
    private var Image       : Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_add_profile,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val CalendarDialog = DatePickerDialog(
            requireContext(),
            { _, insYear, insMonth, insDay ->
                binding.birthdayTextfield.setText("$insDay/${insMonth.plus(1)}/$insYear")
            }, year,month,day
        )
        binding.birthdayTextfield.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                CalendarDialog.show()
                activity?.let { hideSoftKeyboard(it) }
            }
        }
        binding.birthdayTextfield.setOnTouchListener(){ view: View, motionEvent: MotionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_UP -> {
                    CalendarDialog.show()
                    activity?.let { hideSoftKeyboard(it) }
                }
            }
            return@setOnTouchListener true
        }

        ArrayAdapter.createFromResource(requireContext(), R.array.nation_array,android.R.layout.simple_spinner_dropdown_item).also{
            adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.nationSpinner.adapter = adapter
        }

        binding.malercheckBox.isChecked = true

        binding.floatingAddButton.setOnClickListener(){
            var iserror = false
            //Name
            if (!binding.nameTextfield.text.toString().isNullOrEmpty()) {
                Name = binding.nameTextfield.text.toString()
            } else {
                iserror = true
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            //Age
            if (binding.birthdayTextfield.text.length >=8 ){
                if ((binding.birthdayTextfield.text.takeLast(4)).toString().toInt() > 0){
                    Age = calendar.get(Calendar.YEAR) - (binding.birthdayTextfield.text.takeLast(4)).toString().toInt()
                }
            } else {
                iserror = true
                Toast.makeText(context, "Please enter your birthday", Toast.LENGTH_SHORT).show()
            }
            //Nation
            Nation = binding.nationSpinner.selectedItem.toString()
            //Gender
            if (binding.malercheckBox.isChecked == true){
                Gender = "Male"
            } else {
                Gender = "Female"
            }
            //PhoneNumber
            if (binding.phoneTextfield.text.toString().toInt() > 0){
                PhoneNumber = binding.phoneTextfield.text.toString().toInt()
            } else {
                PhoneNumber = 0
            }
            //Email
            if (binding.emailTextfield.toString().isNotEmpty()){
                Email = binding.emailTextfield.text.toString()
            } else {
                Email = ""
            }
            //Image
            Image = R.drawable.ic_baseline_face_24

            if (!iserror){
                val profile = Profile(0, Name!!, Age!!, Nation!! , Gender!!, PhoneNumber!!,Email!!, Image!!)
                insProfileViewModel.addProfile(profile)
                Toast.makeText(context,"Profile added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addProfileFragment_to_controlFragment)
            }
//            activity?.findViewById<ViewPager2>(R.id.viewPager)?.currentItem = 1
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}