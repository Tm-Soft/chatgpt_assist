package live.lafi.library_dialog.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import live.lafi.library_dialog.builder.DialogBaseBuilder
import live.lafi.library_dialog.listener.StringCallbackListener
import live.lafi.library_dialog.recyclerAdapter.RecyclerStringSelectAdapter
import live.lafi.library_dialog.recyclerAdapter.model.StringSelectModel
import live.lafi.util.databinding.DialogRecyclerListSelectBinding

class RecyclerStringSelectDialogShow(
    context: Context,
    private val builder: DialogBaseBuilder<*>,
    private val stringCallBackListener : StringCallbackListener? = null
): Dialog(context) {

    private lateinit var binding: DialogRecyclerListSelectBinding

    private var mStringSelectAdapter : RecyclerStringSelectAdapter? = null

    private val recyclerList = arrayListOf<StringSelectModel>()

    private var selectTitle :String? = null

    private var mSelectTitleList: List<String>? = null
    private var mSelectContentList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mSelectTitleList = builder.selectTitleList
        mSelectContentList = builder.selectContentList

        if (mSelectTitleList == null || mSelectContentList == null) {
            this.dismiss()
            return
        }


        super.onCreate(savedInstanceState)
        binding = DialogRecyclerListSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        initRecyclerView(setRecyclerList())

        binding.btnPositive.setOnClickListener {
            if(selectTitle != null)
                stringCallBackListener?.onStringCallBack(selectTitle!!)

            this.dismiss()
        }
    }

    private fun initView() {
        binding.layoutSelectDialogFrame.layoutParams.width = context.resources.displayMetrics.widthPixels

        when(builder.title) {
            null -> { binding.textViewTitle.text = "유형 선택"}
            else -> { binding.textViewTitle.text = builder.title }
        }

        when(builder.positiveText) {
            null -> { binding.btnPositive.text = "선택" }
            else -> { binding.btnPositive.text = builder.positiveText }
        }

        // Title Color Set
        binding.textViewTitle.setTextColor(
            ContextCompat.getColor(context, builder.titleTextColorResId)
        )

        // Content Color Set
        binding.btnPositive.setTextColor(
            ContextCompat.getColor(context, builder.positiveTextColorResId)
        )
    }

    private fun initRecyclerView(list : ArrayList<StringSelectModel>) {

        if(mStringSelectAdapter == null) {
            mStringSelectAdapter = RecyclerStringSelectAdapter()

            binding.recyclerViewTypeSelect.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = mStringSelectAdapter
            }

            mStringSelectAdapter!!.categoryClickEvent { position ->
                setIsSelected(position)
                selectTitle = recyclerList[position].typeTitle
            }
        }

        mStringSelectAdapter?.submitList(list)
    }

    private fun setIsSelected(position : Int) {
        for(i in 0 until recyclerList.size) {
            if(i == position) {
                recyclerList[i] = StringSelectModel(
                    recyclerList[i].typeTitle,
                    recyclerList[i].typeContent,
                    true
                )
            } else {
                recyclerList[i] = StringSelectModel(
                    recyclerList[i].typeTitle,
                    recyclerList[i].typeContent,
                    false
                )
            }
        }
        mStringSelectAdapter!!.submitList(recyclerList)
        mStringSelectAdapter!!.notifyItemRangeChanged(0, recyclerList.size)
    }

    private fun setRecyclerList() : ArrayList<StringSelectModel> {
        if (mSelectTitleList != null && mSelectContentList != null) {
            for (i in mSelectTitleList!!.indices) {
                recyclerList.add(
                    StringSelectModel(
                        mSelectTitleList!![i],
                        mSelectContentList!![i],
                        false
                    )
                )
            }
        }
        return recyclerList
    }

}