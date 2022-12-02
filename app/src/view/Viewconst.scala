package view

import scala.swing.*
import javax.swing.border.MatteBorder

object Viewconst {
    val WIDTH = 1600
    val HEIGHT = 900
    val BORDER_COLOR = new Color(0, 0, 0)
    val BORDER_WIDTH = 2
    def Border(t: Boolean, b: Boolean, l: Boolean, r: Boolean): MatteBorder = {
        val top = {
            if t then
                BORDER_WIDTH
            else {
                0
            }
        }
        val bottom = {
            if b then
                BORDER_WIDTH
            else {
                0
            }
        }
        val left = {
            if l then
                BORDER_WIDTH
            else {
                0
            }
        }
        val right = {
            if r then
                BORDER_WIDTH
            else {
                0
            }
        }
        Swing.MatteBorder(top, left, bottom, right, BORDER_COLOR)
    }

    val BACKGROUND_DARK = new Color(0x20, 0x20, 0x20)
    val BACKGROUND_MEDIUM = new Color(0x38, 0x38, 0x38)
    val BACKGROUND_LIGHT = new Color(0x83, 0x83, 0x83)

    val FOREGROUND_DARK = new Color(0xff, 0xcc, 0x00)
    val FOREGROUND_MEDIUM = new Color(0xfa, 0xe0, 0x7a)
    val FOREGROUND_LIGHT = new Color(0xfa, 0xf8, 0xf2)
}
