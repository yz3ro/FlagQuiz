package com.yz3ro.flagquiz.util

import android.view.View
import androidx.navigation.Navigation

fun Navigation.navigatex(it: View, id:Int){
    findNavController(it).navigate(id)
}