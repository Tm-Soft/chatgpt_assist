package live.lafi.presentation.chat_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import live.lafi.presentation.databinding.ItemChatContentMyTextBinding
import live.lafi.presentation.databinding.ItemChatContentOtherLoadingBinding
import live.lafi.presentation.databinding.ItemChatContentOtherTextBinding
import timber.log.Timber

class ChatContentListAdapter : ListAdapter<ChatContentItem, RecyclerView.ViewHolder>(
    diffUtil
) {
    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ChatContentItem.ViewType.CHAT_CONTENT_MY_TEXT.type -> {
                MyTextContentViewHolder(
                    ItemChatContentMyTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ChatContentItem.ViewType.CHAT_CONTENT_OTHER_TEXT.type -> {
                OtherTextContentViewHolder(
                    ItemChatContentOtherTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ChatContentItem.ViewType.CHAT_CONTENT_OTHER_LOADING.type -> {
                OtherContentLoadingViewHolder(
                    ItemChatContentOtherLoadingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyTextContentViewHolder -> {
                holder.setupUi(position)
            }
            is OtherTextContentViewHolder -> {
                holder.setupUi(position)
            }
        }
    }


    inner class MyTextContentViewHolder(
        private val binding: ItemChatContentMyTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setupUi(position: Int) {
            val item = getItem(position)
            binding.textViewTextContent.text = item.content
        }
    }

    inner class OtherTextContentViewHolder(
        private val binding: ItemChatContentOtherTextBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setupUi(position: Int) {
            val item = getItem(position)
            binding.tvChatRoomTitle.text = item.nickname
            binding.textViewTextContent.text = item.content
        }
    }

    inner class OtherContentLoadingViewHolder(
        private val binding: ItemChatContentOtherLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setupUi(position: Int) {
            val item = getItem(position)
            binding.tvChatRoomTitle.text = item.nickname
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatContentItem>() {
            override fun areItemsTheSame(oldItem: ChatContentItem, newItem: ChatContentItem): Boolean {
                return oldItem.chatContentSrl == newItem.chatContentSrl
            }

            override fun areContentsTheSame(oldItem: ChatContentItem, newItem: ChatContentItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}