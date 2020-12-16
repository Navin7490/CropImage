package com.example.cropimage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MainActivity : AppCompatActivity() {
    lateinit var imageview:ImageView
    lateinit var btnbrowse:Button
    lateinit var uri:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageview=findViewById(R.id.image_view)
        btnbrowse=findViewById(R.id.Btn_getimage)

        btnbrowse.setOnClickListener {
            CropImage.startPickImageActivity(this)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
            && resultCode == Activity.RESULT_OK) {
            var imageuri:Uri = CropImage.getPickImageResultUri(this,data)
              if (CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)) {
                  uri=imageuri
                  requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                  0)
              } else {
                  startCrop(imageuri)
              }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
         var result:   CropImage.ActivityResult=CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                imageview.setImageURI(result.uri)
                Toast.makeText(this,"Imageset",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun startCrop(imageuri: Uri) {
        CropImage.activity(imageuri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true)
            .start(this)

    }
}