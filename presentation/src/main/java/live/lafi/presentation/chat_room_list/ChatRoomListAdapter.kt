package live.lafi.presentation.chat_room_list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import live.lafi.presentation.databinding.ItemChatRoomBinding

class ChatRoomListAdapter(): ListAdapter<ChatRoomItem, ChatRoomListAdapter.ChatRoomListViewHolder>(
    diffUtil
) {
    private var onClickListener: ((chatRoomSrl: Long, chatRoomTitle: String) -> Unit)? = null
    private var onLongTouchListener: ((Long) -> Unit)? = null

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<ChatRoomItem>() {
            override fun areItemsTheSame(
                oldItem: ChatRoomItem,
                newItem: ChatRoomItem
            ): Boolean {
                return oldItem.chatRoomSrl == newItem.chatRoomSrl
            }

            override fun areContentsTheSame(
                oldItem: ChatRoomItem,
                newItem: ChatRoomItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomListViewHolder {
        val binding = ItemChatRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChatRoomListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatRoomListViewHolder, position: Int) {
        holder.setupUi(position)
    }

    inner class ChatRoomListViewHolder(
        private val binding: ItemChatRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(
                    getItem(adapterPosition).chatRoomSrl,
                    getItem(adapterPosition).title
                )
            }
            itemView.setOnLongClickListener {
                onLongTouchListener?.invoke(getItem(adapterPosition).chatRoomSrl)
                false
            }
        }

        fun setupUi(position: Int) {
            val itemModel = getItem(position)

            binding.chatRoomTitle.text = itemModel.title

            if (itemModel.question.isEmpty()) {
                binding.textViewQuestion.visibility = View.GONE
            } else {
                binding.textViewQuestion.visibility = View.VISIBLE
                binding.textViewQuestion.text = itemModel.question
            }

            binding.textViewContent.text = itemModel.content

            if (itemModel.lastReadTimestamp == 0L || itemModel.lastUpdateTimestamp > itemModel.lastReadTimestamp) {
                binding.layoutNewIcon.visibility = View.VISIBLE
            } else {
                binding.layoutNewIcon.visibility = View.GONE
            }
        }
    }

    fun setOnClickListener(onClickListener: (chatRoomSrl: Long, chatRoomTitle: String) -> Unit) {
        this.onClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongTouchListener: (Long) -> Unit) {
        this.onLongTouchListener = onLongTouchListener
    }

}