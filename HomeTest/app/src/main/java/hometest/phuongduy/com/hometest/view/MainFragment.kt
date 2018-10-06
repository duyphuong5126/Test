package hometest.phuongduy.com.hometest.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hometest.phuongduy.com.hometest.MainContract
import hometest.phuongduy.com.hometest.adapter.KeywordAdapter
import hometest.phuongduy.com.hometest.databinding.FragmentMainBinding
import hometest.phuongduy.com.hometest.model.Keyword

class MainFragment : Fragment(), MainContract.View {
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

    private lateinit var mKeywordAdapter: KeywordAdapter
    private lateinit var mKeywordListLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
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
        mTvNetworkError.visibility = View.GONE
    }

    override fun showLoadingError() {
        mRvKeywordList.visibility = View.GONE
        mTvNetworkError.visibility = View.VISIBLE
    }

    private fun initViews() {
        mRvKeywordList = mBinding.rvKeywordsList
        mTvNetworkError = mBinding.tvNetworkError
    }
}