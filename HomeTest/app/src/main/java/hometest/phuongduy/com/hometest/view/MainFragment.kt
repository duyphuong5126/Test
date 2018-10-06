package hometest.phuongduy.com.hometest.view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import hometest.phuongduy.com.hometest.MainContract
import hometest.phuongduy.com.hometest.R
import hometest.phuongduy.com.hometest.adapter.KeywordAdapter
import hometest.phuongduy.com.hometest.databinding.FragmentMainBinding
import hometest.phuongduy.com.hometest.model.Keyword

class MainFragment : Fragment(), MainContract.View, View.OnClickListener {
    companion object {
        val TAG: String = MainFragment::class.java.simpleName
    }

    private lateinit var mPresenter: MainContract.Presenter
    private lateinit var mBinding: FragmentMainBinding

    /**
     * Views list
     */
    private lateinit var mRvKeywordList: RecyclerView
    private lateinit var mTvNetworkError: TextView
    private lateinit var mNetworkErrorLayout: ConstraintLayout
    private lateinit var mButtonRetry: Button

    private lateinit var mKeywordAdapter: KeywordAdapter
    private lateinit var mKeywordListLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        mButtonRetry.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
    }

    override fun onClick(view: View?) {
        view?.let {
            when (view.id) {
                R.id.btn_retry -> {
                    mPresenter.reloadData()
                }
            }
        }
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun setUpKeywordsList(keywordList: List<Keyword>) {
        mKeywordAdapter = KeywordAdapter(keywordList)
        mKeywordListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRvKeywordList.adapter = mKeywordAdapter
        mRvKeywordList.layoutManager = mKeywordListLayoutManager
        mRvKeywordList.visibility = View.VISIBLE
        mNetworkErrorLayout.visibility = View.GONE
    }

    override fun showLoadingError() {
        mRvKeywordList.visibility = View.GONE
        mNetworkErrorLayout.visibility = View.VISIBLE
    }

    private fun initViews() {
        mRvKeywordList = mBinding.rvKeywordsList
        mTvNetworkError = mBinding.tvNetworkError
        mNetworkErrorLayout = mBinding.clNetworkError
        mButtonRetry = mBinding.btnRetry
    }
}