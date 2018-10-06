package hometest.phuongduy.com.hometest.adapter

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hometest.phuongduy.com.hometest.R
import hometest.phuongduy.com.hometest.SupportUtils
import hometest.phuongduy.com.hometest.databinding.ItemKeywordBinding
import hometest.phuongduy.com.hometest.model.Keyword
import java.util.*

class KeywordAdapter(private val mKeywordList: List<Keyword>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val RANDOM_RANGE = 200
        private const val FIXED_ALPHA = 255
    }

    private val mColorsList: IntArray = IntArray(mKeywordList.size)

    init {
        // Initialize once and for all
        val random = Random()
        for (index in 0 until mKeywordList.size) {
            mColorsList[index] = SupportUtils.generateColor(
                    FIXED_ALPHA,
                    r = random.nextInt(RANDOM_RANGE), // 0 -> 199
                    g = random.nextInt(RANDOM_RANGE), // 0 -> 199
                    b = random.nextInt(RANDOM_RANGE) // 0 -> 199
            )
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_keyword -> {
                val keywordViewHolder = viewHolder as KeywordViewHolder
                keywordViewHolder.updateKeyword(getItemAt(position), mColorsList[position])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_keyword -> {
                KeywordViewHolder(ItemKeywordBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false).root)
            }
            else -> {
                KeywordViewHolder(ItemKeywordBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false).root)
            }
        }
    }

    override fun getItemCount(): Int = mKeywordList.size

    private fun getItemAt(position: Int): Keyword = mKeywordList[position]

    override fun getItemViewType(position: Int): Int = R.layout.item_keyword

    private inner class KeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mTvKeywordName: TextView = itemView.findViewById(R.id.tv_keyword)
        private val mBgDrawable: Drawable? = ContextCompat.getDrawable(itemView.context, R.drawable.bg_all_corner_radius_sample)

        fun updateKeyword(keyword: Keyword, color: Int) {
            mTvKeywordName.text = keyword.correctName
            mBgDrawable?.run {
                setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mTvKeywordName.setBackgroundDrawable(this)
                } else {
                    mTvKeywordName.background = this
                }
            }
        }
    }
}