package com.camera.model.utils

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.camera.model.models.UserProfile
import com.camera.utils.CustomToast
import com.camera.utils.EmpresasType
import com.google.gson.Gson
import com.mylibrary.R
import com.mylibrary.databinding.ZCustomToastDesignBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 ** Created by Tomas Maurente
 ** tommaurente@gmail.com
 **/

object Utils {

    /**
     * Returns today's day in format yyyy-MM-dd HH:mm:ss
     */
    fun getCurrentDateAndTime(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    /**
     * Returns today's day in format yyyy-MM-dd
     */
    fun getTodayTypeTwo(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)
    }

    fun ahoraMilisegundos(user: UserProfile): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dtFormatter = if (user.empId.toInt() == EmpresasType.VCT.companyNumber()) {
                DateTimeFormatter.ofPattern("yyMMddHHmm")
            } else {
                DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
            }
            val now = LocalDateTime.now()
            now.format(dtFormatter)
        } else {
            val dtFormat = if (user.empId.toInt() == EmpresasType.VCT.companyNumber()) {
                SimpleDateFormat("yyMMddHHmm")
            } else {
                SimpleDateFormat("yyyyMMddHHmmssSSS")
            }
            val now = Date()
            dtFormat.format(now)
        }
    }

    fun Context.customToast(
        customToast: CustomToast
    ) {
        val inflater = LayoutInflater.from(this)
        val view = ZCustomToastDesignBinding.inflate(inflater)
        //val layout = inflater.inflate(R.layout.z_custom_toast_design, null)
        val title = view.titleToast // layout.findViewById<TextView>(R.id.titleToast)
        val body = view.bodyToast  // layout.findViewById<TextView>(R.id.bodyToast)
        val pic = view.toastPic  // layout.findViewById<ImageView>(R.id.toastPic)
        val card = view.cardToast  //layout.findViewById<CardView>(R.id.cardToast)

        title.text = customToast.titleToast
        body.text = customToast.bodyToast

        pic.setImageResource(customToast.picId)

        if (customToast.cardColor != -1) {
            card.setCardBackgroundColor(ContextCompat.getColor(this, customToast.cardColor))
        }

        val toast = Toast.makeText(this, "", customToast.duration)
        toast.view = view.root
        toast.show()
    }

    fun Any.toJson() = Gson().toJson(this)
}