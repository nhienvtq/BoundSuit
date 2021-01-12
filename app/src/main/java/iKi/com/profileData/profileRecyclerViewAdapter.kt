package iKi.com.profileData

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import iKi.com.ControlFragmentDirections
import iKi.com.ImageBitmapString
import iKi.com.PagerFragments.ProfileFragment
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

        val currentItem = listitems[position]
        //binding views to data of database
        binding.nameTextView.text = currentItem.Name
        binding.emailTextView.text = currentItem.Email
        binding.phoneTextView.text = currentItem.PhoneNumber
        binding.avatarImage.load(ImageBitmapString.StringToBitMap(currentItem.Image_profile)){
            crossfade(true)
            crossfade(1000)
            transformations(CircleCropTransformation())
            size(500)
        }
        if (currentItem.Gender == "Male"){
            val color = Color.parseColor("#7fdbda")
            binding.genderImage.setColorFilter(color)
        } else {
            val color = Color.parseColor("#e79cc2")
            binding.genderImage.setColorFilter(color)
        }

        binding.rowLayout.setOnClickListener(){
            val action = ControlFragmentDirections.actionControlFragmentToUpdateProfileFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
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
        RecyclerView.ViewHolder(binding.getRoot()){}
}

