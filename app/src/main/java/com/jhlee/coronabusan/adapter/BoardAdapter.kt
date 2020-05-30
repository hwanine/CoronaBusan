package com.jhlee.coronabusan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.BoardViewModel
import com.jhlee.coronabusan.Model.CoronaPeople
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.ViewModel.BoardDialogViewModel
import kotlinx.android.synthetic.main.board_itemview.view.*

class BoardAdapter(vm: BoardViewModel) : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
    var items = ArrayList<CoronaPeople>()
    val vm = vm

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.board_itemview, parent, false)
        return ViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bind(pos: Int) {
            itemView.busan_name.text = items[pos].name
            itemView.busan_route.text = items[pos].route
            itemView.busan_num.text = items[pos].num
            itemView.busan_hospital.text = items[pos].hospital
            itemView.busan_date.text = items[pos].date
            itemView.setOnClickListener {
                vm.clickPeople.value = pos
            }
        }
    }
    fun addItem(items : ArrayList<CoronaPeople>){
        this.items = items
        notifyDataSetChanged()
    }

}