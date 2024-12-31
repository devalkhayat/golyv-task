package com.golyv.core.common

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaStoreUtil (private  val context:Context){

    suspend fun saveImage(bitmap: Bitmap):String{
        var output:String=""
        withContext(Dispatchers.IO){
            val resolver=context.contentResolver
            val imageCollection=MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            val timeInMillis=System.currentTimeMillis()
            val imageContentValues=ContentValues().apply {
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES
                )
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,"${timeInMillis}_image"+".jpg"
                )
                put(
                    MediaStore.Images.Media.MIME_TYPE,"image/jpg"
                )
                put(
                    MediaStore.Images.Media.DATE_TAKEN,timeInMillis
                )
                put(
                    MediaStore.Images.Media.IS_PENDING,1
                )

            }

            val imageMediaStoreUri=resolver.insert(
                imageCollection,imageContentValues
            )
            imageMediaStoreUri?.let { uri->
                try {

                    resolver.openOutputStream(uri)?.let { outputStream ->  
                        bitmap.compress(
                            Bitmap.CompressFormat.JPEG,100,outputStream
                        )
                    }
                    output=uri.toString()
                    imageContentValues.clear()
                    imageContentValues.put(MediaStore.MediaColumns.IS_PENDING,0)
                    resolver.update(uri,imageContentValues,null,null)

                }catch (e:Exception){
                    e.printStackTrace()
                    resolver.delete(uri,null,null)

                }
            }
        }

        return  output
    }
}