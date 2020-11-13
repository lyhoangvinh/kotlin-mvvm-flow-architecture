package com.lyhoangvinh.simple.ui.features.avgle.home

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.lyhoangvinh.simple.R
import com.lyhoangvinh.simple.databinding.ItemSearchBinding
import com.lyhoangvinh.simple.databinding.ViewDividerBinding
import com.lyhoangvinh.simple.databinding.ViewRcyHorizontalBinding
import com.lyhoangvinh.simple.ui.base.adapter.*
import com.lyhoangvinh.simple.ui.features.avgle.home.inside.CategoriesAdapter
import com.lyhoangvinh.simple.ui.widget.recycleview.GravitySnapHelper
import com.lyhoangvinh.simple.ui.widget.recycleview.HorizontalSpaceItemDecoration
import com.lyhoangvinh.simple.utils.genericCastOrNull
import com.vinh.domain.itemviewmodel.CategoryItem2
import com.vinh.domain.itemviewmodel.DividerItem2
import com.vinh.domain.itemviewmodel.ItemData
import com.vinh.domain.itemviewmodel.SearchItemData2
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class HomeAdapter2 @Inject constructor(@ActivityContext context: Context): BaseItemDataAdapter(context, object : DiffUtil.ItemCallback<ItemData>() {
    override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
        return when {
            oldItem is CategoryItem2 && newItem is CategoryItem2 -> oldItem.idViewModel == newItem.idViewModel
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
        return when {
            oldItem is CategoryItem2 && newItem is CategoryItem2 -> oldItem.categories == newItem.categories
            else -> false
        }
    }

}) {
    companion object {
        private const val ITEM_SEARCH = 1
        private const val ITEM_CATEGORY = 2
        private const val ITEM_BANNER = 3
        private const val ITEM_VIDEO = 4
        private const val ITEM_COLLECTION_BOTTOM = 5
        private const val ITEM_DIVIDER = 6
        private const val ITEM_TITLE_SEE_ALL = 7
    }
    private var mWidth = 0

    private var mHeight = 0

    private var activity: Activity? = null

    fun setLayoutParams(mWidth: Int, mHeight: Int, activity: Activity) {
        this.mWidth = mWidth
        this.mHeight = mHeight
        this.activity = activity
    }
    override fun setItemViewType(item: ItemData): Int {
        return when (item) {
           is SearchItemData2 -> ITEM_SEARCH
           is DividerItem2 -> ITEM_DIVIDER
           is CategoryItem2 -> ITEM_CATEGORY
        }
    }
    override fun getLayoutResource(viewType: Int): Int {
        return when (viewType) {
            ITEM_SEARCH -> R.layout.item_search
            ITEM_DIVIDER -> R.layout.view_divider
            ITEM_CATEGORY -> R.layout.view_rcy_horizontal
            else -> throw RuntimeException("Not support layoutResource $viewType")
        }
    }
    override fun createItemViewHolder(view: View, viewType: Int): BaseItemDataViewHolder<ItemData, ViewDataBinding> {
        return when (viewType) {
            ITEM_SEARCH -> genericCastOrNull(SearchItemDataViewHolder(view))
            ITEM_DIVIDER -> genericCastOrNull(DividerItemSimpleViewHolder(view))
            ITEM_CATEGORY -> genericCastOrNull(CategoriesItemSimpleViewHolder(context, view))
            else -> throw RuntimeException("Not support type=$viewType")
        }
    }


    private class DividerItemSimpleViewHolder(view: View) :
        BaseItemDataViewHolder<DividerItem2, ViewDividerBinding>(view)

    private class SearchItemDataViewHolder(view: View): BaseItemDataViewHolder<SearchItemData2, ItemSearchBinding>(view)

    private class CategoriesItemSimpleViewHolder(private val context: Context, view: View) :
        BaseItemDataViewHolder<CategoryItem2, ViewRcyHorizontalBinding>(view) {
        private var isItemDecoration = false
        private var adapter: CategoriesAdapter? = null
        override fun setItem(data: CategoryItem2, binding: ViewRcyHorizontalBinding) {
            super.setItem(data, binding)
            if (adapter == null) {
                adapter = CategoriesAdapter(context)
                binding.rcv.adapter = adapter
            }
            if (!isItemDecoration) {
                isItemDecoration = true
                binding.rcv.addItemDecoration(HorizontalSpaceItemDecoration(context.resources.getDimensionPixelSize(R.dimen.padding_10dp)))
            }
//            binding.rcv.isNestedScrollingEnabled = false
            adapter?.submitList(data.categories)
//            adapter?.setOnClickItemListener {navigatorHelper.navigateVideosFragment(it)}
            if (binding.rcv.onFlingListener == null) {
                GravitySnapHelper(Gravity.START).attachToRecyclerView(binding.rcv)
            }
        }
    }
}