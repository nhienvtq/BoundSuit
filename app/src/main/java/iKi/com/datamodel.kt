package iKi.com

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custome_re_view.view.*

class datamodel(
    var itemdata1: String,
    var itemdata2: String,
    var itemdata3: String
)

class RecyclerViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
    val itemdata1 = itemView.datatextView1
    val itemdata2 = itemView.datatextView2
    val itemdata3 = itemView.datatextView3

    fun bind(datamodel: datamodel){
        itemdata1.setText(datamodel.itemdata1)
        itemdata2.setText(datamodel.itemdata2)
        itemdata3.setText(datamodel.itemdata3)
    }
}