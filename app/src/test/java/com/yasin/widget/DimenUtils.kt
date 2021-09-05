package com.yasin.widget

import org.junit.Test
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintWriter

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/9/5.
 */
class DimenUtils {
    private val dw = 320f
    private val dh = 480f

    private val rootPath="D:\\workspace\\project\\widget\\app\\src\\main\\res"

    @Test
    fun start(){
        make(Screen(320,480))
        make(Screen(480,854))
        make(Screen(540,960))
        make(Screen(600,1024))
        make(Screen(720,1184))
        make(Screen(720,1196))
        make(Screen(720,1280))
        make(Screen(800,1280))
        make(Screen(1080,1812))
        make(Screen(1080,1920))
        make(Screen(1440,2560))
    }


    fun make(screen: Screen){
        val sb = StringBuffer()
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
        sb.append("<resources>\n")
        val cell: Float = screen.width /dw
        println("screen.width = ${screen.width} screen.height = ${screen.height} cellw = $cell  dw = $dw")
        for (i in 1 .. dw.toInt()) {
            sb.append("<dimen name=\"x$i\">${change(cell * i)}px</dimen>\n")

        }
        val cellh: Float = screen.height /dh
        for (i in 1..dh.toInt()) {
            sb.append("<dimen name=\"y$i\">${change(cellh * i)}px</dimen>\n")
        }
        sb.append("</resources>")

        val path = "$rootPath\\values-${screen.height}x${screen.width}\\"
        val rootFile = File(path)
        if (!rootFile.exists()) {
            rootFile.mkdirs()
        }
        val dimenFile = File(path + "dimens.xml")

        try {
            var pw = PrintWriter(FileOutputStream(dimenFile))
            pw.print(sb.toString())
            pw.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    fun change(a: Float): Float {
        val temp = (a * 100).toInt()
        return temp / 100f
    }

}

class Screen(val width:Int,val height:Int)