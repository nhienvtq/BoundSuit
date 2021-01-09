package iKi.com.profileData

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iKi.com.R
import kotlinx.android.synthetic.main.profile_recyclerview_section.view.*

class profileRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private var listitems: List<Profile> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolder (LayoutInflater.from(parent.context).inflate(R.layout.profile_recyclerview_section,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("flowTag", "pos: " + position.toString())
        when (holder){
            is RecyclerViewHolder ->{ holder.bind(listitems.get(position))}
        }
    }

    override fun getItemCount(): Int {
        Log.i("flowTag", "siz: " + listitems.size.toString())
        return listitems.size
    }

    fun setData(profile: List<Profile>){
        this.listitems = profile
        notifyDataSetChanged()
    }

    class RecyclerViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val textName = itemView.nameTextView
        val textEmail = itemView.emailTextView
        val textPhone = itemView.phoneTextView

        fun bind(profile: Profile){
            textName.setText(profile.Name)
            textEmail.setText(profile.Email)
            textPhone.setText(profile.PhoneNumber.toString())
        }
    }
}

