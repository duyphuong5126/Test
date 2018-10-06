package hometest.phuongduy.com.hometest.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import hometest.phuongduy.com.hometest.Constants
import hometest.phuongduy.com.hometest.model.Keyword

@Dao
interface KeywordsDAO {
    companion object {
        private const val TABLE_NAME = Constants.KEYWORDS
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg keyword: Keyword)

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    fun getNumberOfKeyword(): Int

    @Query("SELECT * FROM $TABLE_NAME")
    fun getKeywordsList(): List<Keyword>
}