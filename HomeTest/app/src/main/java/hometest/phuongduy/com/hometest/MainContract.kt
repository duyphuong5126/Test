package hometest.phuongduy.com.hometest

import hometest.phuongduy.com.hometest.model.Keyword

interface MainContract {
    interface View {
        fun setPresenter(presenter: Presenter)
        fun setUpKeywordsList(keywordList: List<Keyword>)
    }

    interface Presenter {
        fun start()
        fun stop()
    }
}