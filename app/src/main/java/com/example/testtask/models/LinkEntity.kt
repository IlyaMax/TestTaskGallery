package com.example.testtask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Сушность используется для хранения в БД и recyclerview
@Entity(tableName = "link")
data class LinkEntity(@PrimaryKey val link: String)
