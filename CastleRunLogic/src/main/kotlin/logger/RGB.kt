package org.example.logger

data class RGB(val red: Int, val green: Int, val blue: Int) {
    init {
        require(red in 0..255) { "Red component must be between 0 and 255" }
        require(green in 0..255) { "Green component must be between 0 and 255" }
        require(blue in 0..255) { "Blue component must be between 0 and 255" }
    }

    override fun toString(): String {
        return "\u001B[38;2;${red};${green};${blue}m"
    }

    companion object Colors {
        val Green = RGB(0, 255, 0)
        val Red = RGB(255, 0, 0)
        val Blue = RGB(0, 0, 255)
        val Black = RGB(0, 0, 0)
        val White = RGB(255, 255, 255)
        val Yellow = RGB(255, 255, 0)
        val Brown = RGB(139, 69, 19)
        val Orange = RGB(255, 165, 0)
        val Purple = RGB(128, 0, 128)
        val Cyan = RGB(0, 255, 255)
        val Magenta = RGB(255, 0, 255)
        val LightGray = RGB(211, 211, 211)
        val DarkGray = RGB(169, 169, 169)
        val LightBlue = RGB(173, 216, 230)
        val LightGreen = RGB(144, 238, 144)
        val LightPink = RGB(255, 182, 193)
        val DarkRed = RGB(139, 0, 0)
        val DarkGreen = RGB(0, 100, 0)
        val DarkBlue = RGB(0, 0, 139)
        val LightYellow = RGB(255, 255, 224)
        val Gold = RGB(255, 215, 0)
        val Silver = RGB(192, 192, 192)
        val Coral = RGB(255, 127, 80)
        val Tomato = RGB(255, 99, 71)
        val Salmon = RGB(250, 128, 114)
        val HotPink = RGB(255, 105, 180)
        val DarkOrange = RGB(255, 140, 0)
        val LightCoral = RGB(240, 128, 128)
        val Orchid = RGB(218, 112, 214)
        val Turquoise = RGB(64, 224, 208)
        val SkyBlue = RGB(135, 206, 235)
        val MediumPurple = RGB(147, 112, 219)
        val SlateGray = RGB(112, 128, 144)
    }
}
