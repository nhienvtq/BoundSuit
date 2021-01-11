package iKi.com.profileData

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import iKi.com.ImageBitmapString
import iKi.com.R
import iKi.com.databinding.ProfileRecyclerviewSectionBinding


class profileRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var listitems: List<Profile> = ArrayList()
    private var _binding: ProfileRecyclerviewSectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.getContext())
        _binding = DataBindingUtil.inflate(layoutInflater,R.layout.profile_recyclerview_section,parent,false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is RecyclerViewHolder -> {
                holder.bind(listitems.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return listitems.size
    }

    fun setData(profile: List<Profile>){
        this.listitems = profile
        notifyDataSetChanged()
    }


    class RecyclerViewHolder constructor(binding: ProfileRecyclerviewSectionBinding):
        RecyclerView.ViewHolder(binding.getRoot()){
        val textName = binding.nameTextView
        val textEmail = binding.emailTextView
        val textPhone = binding.phoneTextView
        val imageProfile = binding.avatarImage
        val imageGender = binding.genderImage


        fun bind(profile: Profile){

            textName.setText(profile.Name)
            textEmail.setText(profile.Email)
            textPhone.setText(profile.PhoneNumber)
//            imageProfile.setImageBitmap(ImageBitmapString.StringToBitMap(profile.Image_profile))
            imageProfile.load(ImageBitmapString.StringToBitMap(profile.Image_profile)){
                crossfade(true)
                crossfade(1000)
                transformations(CircleCropTransformation())
                size(500)
            }
            if (profile.Gender == "Male"){
                val color = Color.parseColor("#7fdbda")
                imageGender.setColorFilter(color)
            } else {
                val color = Color.parseColor("#e79cc2")
                imageGender.setColorFilter(color)
            }
        }
    }
}

