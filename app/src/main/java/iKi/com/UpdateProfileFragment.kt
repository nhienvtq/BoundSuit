package iKi.com

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Update
import coil.load
import coil.transform.CircleCropTransformation
import iKi.com.databinding.FragmentUpdateProfileBinding
import iKi.com.profileData.Profile
import iKi.com.profileData.ProfileViewModel
import java.util.*

class UpdateProfileFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private lateinit var insProfileViewModel: ProfileViewModel
    private val binding get() = _binding!!
    private val args by navArgs<UpdateProfileFragmentArgs>()

    private var Name        : String? = null
    private var Age         : Int? = null
    private var Birthday    : String? = null
    private var Nation      : String? = null
    private var Gender      : String? = null
    private var PhoneNumber : String? = null
    private var Email       : String? = null
    private var Image       : String? = null
    private val PICK_IMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile,container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameTextfield.setText(args.currentProfile.Name)
        binding.birthdayTextfield.setText(args.currentProfile.Birthday)
        binding.emailTextfield.setText(args.currentProfile.Email)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.nation_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also{ adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.nationSpinner.adapter = adapter
        }
        val nationArray = resources.getStringArray(R.array.nation_array)
        for (item in nationArray){
            if (item == args.currentProfile.Nation.toString()){
                binding.nationSpinner.setSelection(nationArray.indexOf(item))
            }
        }
        if (args.currentProfile.Gender == "Male"){
            binding.malecheckBox.isChecked = true
        } else {
            binding.femalecheckBox.isChecked = true
        }
        binding.phoneTextfield.setText(args.currentProfile.PhoneNumber)
        binding.profileImageView.load(ImageBitmapString.StringToBitMap(args.currentProfile.Image_profile))

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val CalendarDialog = DatePickerDialog(
            requireContext(),
            { _, insYear, insMonth, insDay ->
                binding.birthdayTextfield.setText("$insDay/${insMonth.plus(1)}/$insYear")
            }, year, month, day
        )
        binding.birthdayTextfield.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                CalendarDialog.show()
                activity?.let { hideSoftKeyboard(it) }
            }
        }
        binding.birthdayTextfield.setOnTouchListener(){ _: View, motionEvent: MotionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_UP -> {
                    CalendarDialog.show()
                    activity?.let { hideSoftKeyboard(it) }
                }
            }
            return@setOnTouchListener true
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.nation_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also{ adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.nationSpinner.adapter = adapter
        }

        binding.malecheckBox.setOnClickListener(){
            binding.profileImageView.load(R.raw.maleavatar){
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
                size(500)
            }
        }
        binding.femalecheckBox.setOnClickListener(){
            binding.profileImageView.load(R.raw.femaleavatar){
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
                size(500)
            }
        }


        binding.insertPhotoButton.setOnClickListener(){
            val intent= Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        binding.floatingUpdateButton.setOnClickListener() {
            var iserror = false
            //Name
            if (binding.nameTextfield.text.toString().isNotEmpty()) {
                Name = binding.nameTextfield.text.toString()
            } else {
                iserror = true
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            //Age
            if (binding.birthdayTextfield.text.length >= 8) {
                if ((binding.birthdayTextfield.text.takeLast(4)).toString().toInt() > 0) {
                    Age =
                        calendar.get(Calendar.YEAR) - (binding.birthdayTextfield.text.takeLast(4)).toString()
                            .toInt()
                    Birthday = binding.birthdayTextfield.text.toString()
                }
            } else {
                iserror = true
                Toast.makeText(context, "Please enter your birthday", Toast.LENGTH_SHORT).show()
            }
            //Nation
            Nation = binding.nationSpinner.selectedItem.toString()
            //Image

            //Gender
            if (binding.malecheckBox.isChecked == true) {
                Gender = "Male"
            } else {
                Gender = "Female"
            }
            //PhoneNumber
            if (binding.phoneTextfield.text.toString().isNotEmpty()) {
                PhoneNumber = binding.phoneTextfield.text.toString()
            } else {
                PhoneNumber = ""
            }
            //Email
            if (binding.emailTextfield.text.toString().isNotEmpty()) {
                Email = binding.emailTextfield.text.toString()
            } else {
                Email = ""
            }
            //Image
            Image = ImageBitmapString.BitMapToString(binding.profileImageView.drawable.toBitmap())

            if (!iserror) {
                val profile = Profile(
                    args.currentProfile.id,
                    Name!!,
                    Age!!,
                    Birthday!!,
                    Nation!!,
                    Gender!!,
                    PhoneNumber!!,
                    Email!!,
                    Image!!
                )
                insProfileViewModel.updateProfile(profile)
                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateProfileFragment_to_controlFragment)
                activity?.let { hideSoftKeyboard(it) }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            binding.profileImageView.load(data?.data){
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
                size(500)
            }
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText){
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0
            )
        }
    }
}