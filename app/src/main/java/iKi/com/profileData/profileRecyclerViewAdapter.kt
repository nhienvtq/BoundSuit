package iKi.com.profileData

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import iKi.com.ImageBitmapString
import iKi.com.ProfileFragmentDirections
import iKi.com.R
import iKi.com.databinding.ProfileRecyclerviewSectionBinding


class profileRecyclerViewAdapter(private var clickListener: OnProfileItemClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var listitems: List<Profile> = ArrayList()
    private var _binding: ProfileRecyclerviewSectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        layoutInflater = LayoutInflater.from(parent.getContext())
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.profile_recyclerview_section,
            parent,
            false
        )
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

        binding.editimageBtn.setOnClickListener(){
            val action = ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment(
                currentItem
            )
            holder.itemView.findNavController().navigate(action)
        }

        when (holder){
            is RecyclerViewHolder -> {
                holder.DisplayMoreFunction(context)
                holder.initialize(listitems.get(position),clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return listitems.size
    }

    fun setData(profile: List<Profile>, context: Context){
        this.listitems = profile
        this.context = context
        notifyDataSetChanged()
    }

    class RecyclerViewHolder constructor(private var binding: ProfileRecyclerviewSectionBinding):
        RecyclerView.ViewHolder(binding.getRoot()){
        private lateinit var translateAnimation: Animation
        private var morebutton_click = false

        fun DisplayMoreFunction(context: Context){
            binding.moreimageBtn.setOnClickListener(){
                //appear on click
                if (morebutton_click == false){
                    translateAnimation = AnimationUtils.loadAnimation(context,R.anim.translate_from_right)
                    morebutton_click = true
                    binding.editimageBtn.visibility = View.VISIBLE
                    binding.delSigimageBtn.visibility = View.VISIBLE
                } else { //disappear on click
                    translateAnimation = AnimationUtils.loadAnimation(context,R.anim.translate_to_right)
                    morebutton_click = false
                    binding.editimageBtn.visibility = View.INVISIBLE
                    binding.delSigimageBtn.visibility = View.INVISIBLE
                }
                binding.editimageBtn.startAnimation(translateAnimation)
                binding.delSigimageBtn.startAnimation(translateAnimation)
            }
        }

        fun initialize(item: Profile, action: OnProfileItemClickListener){
            //passing position when delete button click
            binding.delSigimageBtn.setOnClickListener(){
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}

interface OnProfileItemClickListener{
    fun onItemClick(item: Profile,position: Int)
}
