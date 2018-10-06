package hometest.phuongduy.com.hometest.model

import android.arch.persistence.room.*
import android.util.Log
import hometest.phuongduy.com.hometest.Constants

@Entity(tableName = Constants.KEYWORDS, indices = (arrayOf(Index(value = [Constants.KEYWORD_ID], unique = true))))
open class Keyword(@ColumnInfo(name = Constants.KEYWORD_NAME) var mName: String) {
    companion object {
        private val TAG: String = Keyword::class.java.simpleName
    }

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Constants.KEYWORD_ID)
    var id: Int = 0

    @Ignore
    val correctName: String

    init {
        correctName = breakKeywordIntoLines(mName)
    }

    private fun breakKeywordIntoLines(keyword: String): String {
        Log.d(TAG, "Breaking keyword \"$keyword\" into lines.")
        val charArray = keyword.toCharArray()
        val keywordLength = charArray.size
        if (keywordLength <= 2) {
            return keyword
        }

        val center = keywordLength / 2
        if (charArray[center].isWhitespace()) {
            charArray[center] = '\n'
            return String(charArray)
        }

        var leftSpacePos: Int = center - 1
        var rightSpacePos: Int = center + 1

        while (leftSpacePos >= 0 && rightSpacePos < keywordLength) {
            if (charArray[leftSpacePos].isWhitespace()) {
                charArray[leftSpacePos] = '\n'
                return String(charArray)
            }
            if (charArray[rightSpacePos].isWhitespace()) {
                charArray[rightSpacePos] = '\n'
                return String(charArray)
            }
            leftSpacePos--
            rightSpacePos++
        }
        return keyword
    }
}