package ua.ruslan.featuresandroidapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class PicturesListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictures_list)
        val pictureUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val pictureUri = MediaStore.getMediaUri(baseContext, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        Environment. getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toUri()
        val tomorrow = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2))

        val selection = "${MediaStore.Images.ImageColumns.DATE_ADDED} > ?"
        val selectionArgs = arrayOf(tomorrow.toString())
        val cursor = applicationContext.contentResolver.query(pictureUri, null, selection, selectionArgs, null)
        cursor?.use {
            it.moveToFirst()
            println("------------------ cursor size = ${it.count}")
            val columns = it.columnNames
            columns.forEach (::println)
            do {
                println("DATA -  ${it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATA))}, TAKEN - ${it.getString(it.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED))}")
            } while (it.moveToNext())
        }
    }
}