package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import live.lafi.presentation.databinding.ItemChatSystemRoleBinding
import live.lafi.presentation.databinding.ItemChatSystemRolePlusButtonBinding
import timber.log.Timber

class ChatSystemRoleAdapter : ListAdapter<ChatSystemRoleListItem, RecyclerView.ViewHolder>(
    diffUtil
) {
    private var onRolePlusListener: (() -> Unit)? = null
    private var onDeleteListener: ((chatSystemRoleSrl: Long) -> Unit)? = null
    private var onChangeChatSystemRoleContent: ((chatSystemRoleSrl: Long, content: String) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ChatSystemRoleListItem.ViewType.ROLE_CONTENT.type -> {
                RoleContentViewHolder(
                    ItemChatSystemRoleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ChatSystemRoleListItem.ViewType.PLUS_BUTTON.type -> {
                PlusButtonViewHolder(
                    ItemChatSystemRolePlusButtonBinding.inflate(
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
            is RoleContentViewHolder -> {
                holder.setupUi(position)
            }

            is PlusButtonViewHolder -> {
                holder.setupUi(position)
            }
        }
    }

    inner class RoleContentViewHolder(
        val binding: ItemChatSystemRoleBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private var textWatcher: TextWatcher? = null
        private var isManuallyTriggered = false

        init {
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(content: CharSequence?, start: Int, before: Int, count: Int) {
                    if (isManuallyTriggered) {
                        onChangeChatSystemRoleContent?.invoke(
                            getItem(adapterPosition).chatSystemRoleSrl,
                            content.toString()
                        )
                        getItem(adapterPosition).roleContent = content.toString()
                    }
                }
            }

            binding.etRoleContent.addTextChangedListener(textWatcher)
        }

        fun setupUi(position: Int) {
            val itemModel = getItem(position)

            isManuallyTriggered = false
            binding.etRoleContent.setText(itemModel.roleContent)
            isManuallyTriggered = true

            binding.flDeleteButton.setOnClickListener { onDeleteListener?.invoke(itemModel.chatSystemRoleSrl) }
        }
    }

    inner class PlusButtonViewHolder(
        val binding: ItemChatSystemRolePlusButtonBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun setupUi(position: Int) {
            binding.ivPlusButton.setOnClickListener {
                onRolePlusListener?.invoke()
            }
        }
    }

    fun setOnRolePlusListener(listener: () -> Unit) {
        this.onRolePlusListener = listener
    }

    fun setOnDeleteListener(listener: (chatSystemRoleSrl: Long) -> Unit) {
        this.onDeleteListener = listener
    }

    fun setOnChangeChatSystemRoleContentListener(listener: (chatSystemRoleSrl: Long, content: String) -> Unit) {
        this.onChangeChatSystemRoleContent = listener
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ChatSystemRoleListItem>() {
            override fun areItemsTheSame(
                oldItem: ChatSystemRoleListItem,
                newItem: ChatSystemRoleListItem
            ) = oldItem.chatSystemRoleSrl == newItem.chatSystemRoleSrl

            override fun areContentsTheSame(
                oldItem: ChatSystemRoleListItem,
                newItem: ChatSystemRoleListItem
            ) = oldItem == newItem

        }
    }
}