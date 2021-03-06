package iKi.com

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import iKi.com.databinding.FragmentAddProfileBinding
import iKi.com.profileData.Profile
import iKi.com.profileData.ProfileViewModel
import java.util.*


class AddProfileFragment : Fragment() {
    private lateinit var binding: FragmentAddProfileBinding
    private lateinit var insProfileViewModel: ProfileViewModel
    private var Name        : String? = null
    private var Age         : Int? = null
    private var Birthday        : String? = null
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

        binding.malecheckBox.isChecked = true
        binding.profileImageView.load(R.raw.maleavatar){
            crossfade(true)
            crossfade(1000)
            transformations(CircleCropTransformation())
            size(500)
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

        binding.floatingAddButton.setOnClickListener(){
            var iserror = false
            //Name
            if (binding.nameTextfield.text.toString().isNotEmpty()) {
                Name = binding.nameTextfield.text.toString()
            } else {
                iserror = true
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            //Age
            if (binding.birthdayTextfield.text.length >=8 ){
                if ((binding.birthdayTextfield.text.takeLast(4)).toString().toInt() > 0){
                    Age = calendar.get(Calendar.YEAR) - (binding.birthdayTextfield.text.takeLast(4)).toString().toInt()
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
            if (binding.malecheckBox.isChecked == true){
                Gender = "Male"
            } else {
                Gender = "Female"
            }
            //PhoneNumber
            if (binding.phoneTextfield.text.toString().isNotEmpty()){
                PhoneNumber = binding.phoneTextfield.text.toString()
            } else {
                PhoneNumber = ""
            }
            //Email
            if (binding.emailTextfield.text.toString().isNotEmpty()){
                Email = binding.emailTextfield.text.toString()
            } else {
                Email = ""
            }
            //Image
            Image = ImageBitmapString.BitMapToString(binding.profileImageView.drawable.toBitmap())

            if (!iserror){
                val profile = Profile(
                    0,
                    Name!!,
                    Age!!,
                    Birthday!!,
                    Nation!!,
                    Gender!!,
                    PhoneNumber!!,
                    Email!!,
                    Image!!
                )
                insProfileViewModel.addProfile(profile)
                Toast.makeText(context, "Profile added", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
                activity?.let { hideSoftKeyboard(it) }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
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