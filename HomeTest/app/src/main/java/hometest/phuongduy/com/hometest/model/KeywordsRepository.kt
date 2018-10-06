package hometest.phuongduy.com.hometest.model

import android.arch.persistence.room.Room
import android.support.annotation.WorkerThread
import android.util.Log
import hometest.phuongduy.com.hometest.Constants
import hometest.phuongduy.com.hometest.HomeTestApplication
import hometest.phuongduy.com.hometest.database.Database
import hometest.phuongduy.com.hometest.database.KeywordsDAO
import hometest.phuongduy.com.hometest.network.ApiService
import hometest.phuongduy.com.hometest.network.ServiceGenerator
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Singleton
object KeywordsRepository {
    private val TAG = KeywordsRepository::class.java.simpleName

    private val mApiService: ApiService
    private val mDatabase: Database
    private val mKeywordsDAO: KeywordsDAO

    init {
        ServiceGenerator.setBaseUrl(Constants.BASE_URL)
        mApiService = ServiceGenerator.createService(ApiService::class.java)

        mDatabase = Room.databaseBuilder(HomeTestApplication.instance.applicationContext, Database::class.java, Constants.HOME_TEST_DB).build()
        mKeywordsDAO = mDatabase.getKeywordsDAO()
    }

    fun getKeywordsList(onLoadKeywordsSuccess: (List<Keyword>) -> Unit, onLoadKeywordsError: () -> Unit) {
        launch {
            val shouldFetch: Boolean = mKeywordsDAO.getNumberOfKeyword() <= 0
            if (shouldFetch) {
                Log.d(TAG, "Should fetch from remote")
                fetchKeywordsListFromRemote(onLoadKeywordsSuccess = { responseList ->
                    insertKeywordsDB(responseList) { result ->
                        Log.d(TAG, "Insert result: $result")
                        getKeywordsListFromDB(onLoadKeywordsSuccess = { resultList ->
                            launch(UI) {
                                onLoadKeywordsSuccess(resultList)
                            }
                        }, onLoadKeywordsError = {
                            launch(UI) {
                                onLoadKeywordsError()
                            }
                        })
                    }
                }, onLoadKeywordsError = {
                    launch(UI) {
                        onLoadKeywordsError()
                    }
                })
            } else {
                Log.d(TAG, "Local is updated, get from local")
                getKeywordsListFromDB(onLoadKeywordsSuccess = { resultList ->
                    launch(UI) {
                        onLoadKeywordsSuccess(resultList)
                    }
                }, onLoadKeywordsError = {
                    launch(UI) {
                        onLoadKeywordsError()
                    }
                })
            }
        }
    }

    // Get from remote
    @WorkerThread
    private fun fetchKeywordsListFromRemote(onLoadKeywordsSuccess: (List<Keyword>) -> Unit, onLoadKeywordsError: () -> Unit) {
        launch {
            mApiService.getKeywordsList().enqueue(object : Callback<List<String>> {
                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    Log.d(TAG, "onFailure errorMessage=${t.message}")
                    onLoadKeywordsError()
                }

                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    Log.d(TAG, "onResponse code=${response.code()}, message=${response.message()}, body=${response.body()}")
                    response.body()?.let { responseList ->
                        onLoadKeywordsSuccess(stringsToKeywordsList(responseList))
                    }
                }
            })
        }
    }

    // Get from local
    @WorkerThread
    private fun getKeywordsListFromDB(onLoadKeywordsSuccess: (List<Keyword>) -> Unit, onLoadKeywordsError: () -> Unit) {
        launch {
            try {
                mKeywordsDAO.getKeywordsList().let { localList ->
                    Log.d(TAG, "Local list size=${localList.size}")
                    onLoadKeywordsSuccess(localList)
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Get keywords from DB error=$exception")
                onLoadKeywordsError()
            }
        }
    }

    // Insert to local DB
    @WorkerThread
    private fun insertKeywordsDB(list: List<Keyword>, onKeywordsInserted: (Boolean) -> Unit) {
        if (list.isEmpty()) {
            onKeywordsInserted(true)
        }
        launch {
            try {
                mKeywordsDAO.insert(*list.toTypedArray())
                onKeywordsInserted(true)
            } catch (exception: Exception) {
                Log.d(TAG, "Insert keywords list error=$exception")
                onKeywordsInserted(false)
            }
        }
    }

    private fun stringsToKeywordsList(inputList: List<String>): List<Keyword> {
        return inputList.map { string ->
            Keyword
            Keyword(string)
        }
    }
}