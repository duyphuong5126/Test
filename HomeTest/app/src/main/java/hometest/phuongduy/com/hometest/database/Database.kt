package hometest.phuongduy.com.hometest.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import hometest.phuongduy.com.hometest.model.Keyword

@Database(entities = [(Keyword::class)], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getKeywordsDAO(): KeywordsDAO
}