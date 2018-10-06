package hometest.phuongduy.com.hometest.presenter

import android.util.Log
import hometest.phuongduy.com.hometest.MainContract
import hometest.phuongduy.com.hometest.model.KeywordsRepository

class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {
    companion object {
        private val TAG: String = MainPresenter::class.java.simpleName
    }

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        Log.d(TAG, "Start presenter")
        fetchAndReload()
    }

    override fun reloadData() {
        Log.d(TAG, "Reload data")
        fetchAndReload()
    }

    override fun stop() {
        Log.d(TAG, "Stop presenter")
    }

    private fun fetchAndReload() {
        KeywordsRepository.getKeywordsList(onLoadKeywordsSuccess = { keyWordList ->
            Log.d(TAG, "Keywords loaded successfully, size: ${keyWordList.size}")
            mView.setUpKeywordsList(keyWordList)
        }, onLoadKeywordsError = {
            Log.d(TAG, "Keywords loading failed")
            mView.showLoadingError()
        })
    }
}