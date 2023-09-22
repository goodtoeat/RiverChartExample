package com.example.riverchart.dto

data class RiverData(
    val `data`: List<Data>
)

data class Data(
    val 同業本淨比中位數: String,
    val 同業本益比中位數: String,
    val 平均本淨比: String,
    val 平均本益比: String,
    val 本淨比基準: List<String>,
    val 本淨比股價評估: String,
    val 本益成長比: String,
    val 本益比基準: List<String>,
    val 本益比股價評估: String,
    val 河流圖資料: List<河流圖資料>,
    val 目前本淨比: String,
    val 目前本益比: String,
    val 股票代號: String,
    val 股票名稱: String
)

data class 河流圖資料(
    val 平均本淨比: String,
    val 平均本益比: String,
    val 年月: String,
    val 月平均收盤價: String,
    val 月近一季本淨比: String,
    val 月近四季本益比: String,
    val 本淨比股價基準: List<String>,
    val 本益比股價基準: List<String>,
    val 近3年年複合成長: String,
    val 近一季BPS: String,
    val 近四季EPS: String
)