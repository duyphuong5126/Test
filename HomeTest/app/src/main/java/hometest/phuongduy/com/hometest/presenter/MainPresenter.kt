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
        KeywordsRepository.getKeywordsList(onLoadKeywordsSuccess = { keyWordList ->
            Log.d(TAG, "Keywords loaded successfully, size: ${keyWordList.size}")
            mView.setUpKeywordsList(keyWordList)
        }, onLoadKeywordsError = {
            Log.d(TAG, "Keywords loading failed")
            mView.showLoadingError()
        })
    }

    override fun stop() {

    }
}