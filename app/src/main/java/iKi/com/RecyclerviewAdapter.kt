package iKi.com

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custome_re_view.view.*

class RecyclerviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listitems: List<datamodel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecyclerViewHolder (LayoutInflater.from(parent.context).inflate(R.layout.custome_re_view,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is RecyclerViewHolder ->{ holder.bind(listitems.get(position))}
        }
    }

    override fun getItemCount(): Int {
        return listitems.size
    }

    fun submitList(dataList: List<datamodel>){
        listitems = dataList
    }
}