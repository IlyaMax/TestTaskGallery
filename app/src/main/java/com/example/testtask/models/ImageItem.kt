package com.example.testtask.models

import android.net.Uri
//Элемент recyclerview images с состоянием
data class ImageItem(var status: Status, val uri: Uri, var error: Throwable?)