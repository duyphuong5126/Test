package hometest.phuongduy.com.hometest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import hometest.phuongduy.com.hometest.presenter.MainPresenter
import hometest.phuongduy.com.hometest.view.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mMainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainTag = MainFragment.TAG
        var mainFragment = supportFragmentManager.findFragmentByTag(mainTag) as MainFragment?
        if (mainFragment == null) {
            mainFragment = MainFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.cl_fragment_container, mainFragment, mainTag)
                    .addToBackStack(mainTag)
                    .commitAllowingStateLoss()
        }
        mMainPresenter = MainPresenter(mainFragment)
    }
}
