package ru.iskhakoff.rarible_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.data.remote.models.response.InvestItem
import ru.iskhakoff.rarible_app.R

class InvestsAdapter(private val inflater: LayoutInflater, private val listener: IClickInvest):
        ListAdapter<InvestItem, InvestsAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<InvestItem>(){
            override fun areItemsTheSame(oldItem: InvestItem, newItem: InvestItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: InvestItem, newItem: InvestItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.item_invest, parent, false)){
        private val title: TextView = itemView.findViewById(R.id.title_invest_item)
        private val description: TextView = itemView.findViewById(R.id.description_invest_item)
        private val owned: TextView = itemView.findViewById(R.id.invest_share_user_value)

        fun bind(item: InvestItem) {
            title.text = item.name
            description.text = item.description
            owned.text = item.owned.toString()

            itemView.setOnClickListener {
                listener.onItemClickInvest(item.id)
            }
        }
    }

    interface IClickInvest{
        fun onItemClickInvest(id: Int)
    }

}
