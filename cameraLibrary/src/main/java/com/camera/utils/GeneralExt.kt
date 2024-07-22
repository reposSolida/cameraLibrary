package com.camera.utils

import android.Manifest.permission.READ_PHONE_STATE
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mylibrary.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

private val socketAddress = "8.8.8.8"
private val socketPort = 53

fun getPhoneImei(context: Context): String? {

    var imei: String? = "SINIMEI"
    val permissionCheck = context?.let {
        ContextCompat.checkSelfPermission(
            it,
            READ_PHONE_STATE
        )
    }
    return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
        // no tengo permiso para acceder al IMEI, retorno sin imei
        "SINIMEI"
    } else try {
        val tm: TelephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.getImei();
        } else {
            tm.getDeviceId();
        }
        imei
    } catch (e: Exception) {
        imei
        //Print as it does in the original app
    }

    // caso que tenga permiso lo busco

}

fun String.toJSONObject() = JSONObject(this)

fun Context.getAppFolderName() = "${this.getExternalFilesDir("")}/solidatec/pv"


fun Context.customToast(
    customToast: CustomToast
) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.z_custom_toast_design, null)
    val title = layout.findViewById<TextView>(R.id.titleToast)
    val body = layout.findViewById<TextView>(R.id.bodyToast)
    val pic = layout.findViewById<ImageView>(R.id.toastPic)
    val card = layout.findViewById<CardView>(R.id.cardToast)

    title.text = customToast.titleToast
    body.text = customToast.bodyToast

    pic.setImageResource(customToast.picId)

    if (customToast.cardColor != -1) {
        card.setCardBackgroundColor(ContextCompat.getColor(this, customToast.cardColor))
    }

    val toast = Toast.makeText(this, "", customToast.duration)
    toast.view = layout
    toast.show()
}

suspend fun Context.isDeviceIsConnectedToTheInternet(): Boolean {
    return (isNetworkConnected(this) && isInternetAvailable(this)) && (!getOptionState(this, OptionValues.offline_work) && isInternetWorking(this))
}

fun Context.isDeviceIsConnectedToTheInternetDescargarPdf(): Boolean {
    return (isNetworkConnected(this) && isInternetAvailable(this)) && (!getOptionState(this, OptionValues.offline_work))
}


fun isNetworkConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)

        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        // For devices running versions prior to Android M
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)

        return capabilities != null &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        // For devices running versions prior to Android M
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


private suspend fun isInternetWorking(context: Context) =
    withContext(Dispatchers.IO) {
        return@withContext try {
            val socket = Socket()
            val socketAddress = InetSocketAddress(socketAddress, socketPort)
            socket.connect(socketAddress, 2000)
            socket.close()
            LogInfo("Dispositivo con conexion")
            true
        }
        catch (stoex : SocketTimeoutException){
            LogError("Error in isInternetWorking", stoex, context)
            LogInfo("Dispositivo sin conexion")
            false
        }
        catch (cex : CancellationException){
            throw cex
        }
        catch (ex: Exception) {
            LogError("Error in isInternetWorking", ex, context)
            false
        }
}



fun Activity.nameActivity() = this::class.simpleName ?: "activity"
fun Fragment.nameFragment() = this::class.simpleName ?: "fragment"


fun Any.toJson() = Gson().toJson(this)

fun String.fromJson(): List<String> {
    val listType = object : TypeToken<List<String>>() {}.type
    return Gson().fromJson(this, listType)
}

fun String.toSecureDouble(): Double {
    return if (this.contains(".")) this.toDouble() else this.replace(",", ".")
        .toDouble()

}

fun String.replaceLast(oldValue: String, newValue: String): String {
    val lastIndex = lastIndexOf(oldValue)
    return if (lastIndex >= 0) {
        substring(0, lastIndex) + newValue + substring(lastIndex + oldValue.length)
    } else {
        this
    }
}

fun doubleFormat_Importe(d: Double, decimalesRedondeo: Int): String {
    val unusualSymbols = DecimalFormatSymbols()
    unusualSymbols.groupingSeparator = '.'
    unusualSymbols.decimalSeparator = ','
    return when (decimalesRedondeo) {
        0 -> DecimalFormat("#,##0", unusualSymbols).format(
            BigDecimal(d).setScale(0, RoundingMode.HALF_UP).toDouble()
        )

        1 -> DecimalFormat("#,##0.0", unusualSymbols).format(
            BigDecimal(d).setScale(
                1,
                RoundingMode.HALF_UP
            ).toDouble()
        )

        3 -> DecimalFormat("#,##0.000", unusualSymbols).format(
            BigDecimal(d).setScale(
                3,
                RoundingMode.HALF_UP
            ).toDouble()
        )

        else -> DecimalFormat("#,##0.00", unusualSymbols).format(
            BigDecimal(d).setScale(
                2,
                RoundingMode.HALF_UP
            ).toDouble()
        )
    }
}


fun doubleFormat_Importe(digits: Int, d: Double): String {
    return when (digits) {
        0 -> DecimalFormat("#,##0").format(
            BigDecimal(d).setScale(0, BigDecimal.ROUND_HALF_UP).toDouble()
        )

        1 -> DecimalFormat("#,##0.0").format(
            BigDecimal(d).setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
        )

        3 -> DecimalFormat("#,##0.000").format(
            BigDecimal(d).setScale(3, BigDecimal.ROUND_HALF_UP).toDouble()
        )

        else -> DecimalFormat("#,##0.00").format(
            BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
        )
    }
}


fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}


fun obtainHtmlTextFormat(text: String): Spanned {
    return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
}


fun fromBlobToBitmap(blob: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(blob, 0, blob.size)
}

