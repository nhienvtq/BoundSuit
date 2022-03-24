package com.Noderium.nodespinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleObserver
//import com.Noderium.nodespinner.databinding.NodeSpinnerLayoutBinding

class NodeSpinnerView: AppCompatTextView, LifecycleObserver {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(attrs)
    }

//    private val binding: LayoutNodeSpinnerBinding
//    private val binding: NodeSpinnerLayoutBinding = NodeSpinnerLayoutBinding.inflate(LayoutInflater.from(context), null, false)

//    var spinnerWindow: PopupWindow
    var isShowing: Boolean = false
    set(value){
        if (!value){
//            spinnerWindow.dismiss()
            arrowAnimate()
        } else {
            show()
            arrowAnimate()
        }
        field = value
    }

    init {
//        this.spinnerWindow = PopupWindow(this.binding.body, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        this.setOnClickListener{

        }
    }

    fun showOrDismiss(){
//        val adapter = binding.recyclerView.adapter ?: return
//        isShowing = !isShowing && adapter.itemCount > 0
    }

    private fun show(){

    }

    private fun arrowAnimate(){
//        binding.imvArrowSpinner.rotation = 90f
    }

    private fun initialize(attrs: AttributeSet){

    }
}