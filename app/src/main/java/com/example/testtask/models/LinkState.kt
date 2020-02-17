package com.example.testtask.models

//Состояние ссылки при загрузке с сети
data class LinkState(
    var status: Status, val link: String?,
    var thr: Throwable?
)