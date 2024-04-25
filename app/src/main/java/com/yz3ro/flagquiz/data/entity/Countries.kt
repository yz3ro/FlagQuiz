package com.yz3ro.flagquiz.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "counties")
data class Countries(@PrimaryKey(autoGenerate = true)
                     @ColumnInfo(name = "country_id") @NotNull var country_id : Int,
                     @ColumnInfo(name = "country_name") @NotNull var country_name : String,
                     @ColumnInfo(name = "flag_url") @NotNull var flag_url : String,
                     @ColumnInfo(name = "region") @NotNull var region : String,
    )
