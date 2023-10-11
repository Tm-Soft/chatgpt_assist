package live.lafi.library_dialog.recyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import live.lafi.library_dialog.recyclerAdapter.model.StringSelectModel
import live.lafi.util.databinding.ItemStringSelectListBinding

class RecyclerStringSelectAdapter
    : ListAdapter<StringSelectModel, RecyclerStringSelectAdapter.TrainerDetailTagViewHolder>(
diffUtil
) {
    private lateinit var context: Context

    private var clickCallbackListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerDetailTagViewHolder {
        val binding = ItemStringSelectListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        this.context = parent.context
        return TrainerDetailTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrainerDetailTagViewHolder, position: Int) {
        val itemModel = getItem(position)

        holder.binding.textViewTypeTitle.text = itemModel.typeTitle
        holder.binding.textViewTypeContentExplain.text = itemModel.typeContent
        holder.binding.layoutBackground.isSelected = itemModel.isSelected

        if(holder.binding.layoutBackground.isSelected) {
            holder.binding.textViewTypeContentExplain.visibility = View.VISIBLE
        } else {
            holder.binding.textViewTypeContentExplain.visibility = View.GONE
        }
    }


    // 내부 뷰 홀더 클래스
    inner class TrainerDetailTagViewHolder(
        val binding: ItemStringSelectListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.layoutBackground.setOnClickListener {
                clickCallbackListener?.invoke(adapterPosition)
            }
        }
    }

    fun categoryClickEvent(callback: (position: Int) -> Unit) {
        this.clickCallbackListener = callback
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<StringSelectModel>() {
            override fun areItemsTheSame(
                oldItem: StringSelectModel,
                newItem: StringSelectModel
            ): Boolean {
                return oldItem.typeTitle == newItem.typeTitle
            }

            override fun areContentsTheSame(
                oldItem: StringSelectModel,
                newItem: StringSelectModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
