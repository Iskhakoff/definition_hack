package ru.iskhakoff.rarible_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.iskhakoff.data.remote.models.response.ProjectItem
import ru.iskhakoff.rarible_app.R
import java.util.zip.Inflater

class ProjectsAdapter(private val inflater: LayoutInflater, private val listener: IClickProject):
    ListAdapter<ProjectItem, ProjectsAdapter.ViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ProjectItem>(){
            override fun areItemsTheSame(oldItem: ProjectItem, newItem: ProjectItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProjectItem, newItem: ProjectItem): Boolean {
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

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.item_project, parent, false)){
        private val title: TextView = itemView.findViewById(R.id.title_project_item)
        private val description: TextView = itemView.findViewById(R.id.description_project_item)

        fun bind(item: ProjectItem) {
            title.text = item.name
            description.text = item.description

            itemView.setOnClickListener {
                listener.onItemClickProjects(item.id)
            }
        }
    }

    interface IClickProject{
        fun onItemClickProjects(id: Int)
    }
}