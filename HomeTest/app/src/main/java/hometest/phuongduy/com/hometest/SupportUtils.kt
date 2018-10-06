package hometest.phuongduy.com.hometest

import android.graphics.Color

class SupportUtils {
    companion object {
        fun generateColor(a: Int, r: Int, g: Int, b: Int): Int = Color.argb(a, r, g, b)
    }
}