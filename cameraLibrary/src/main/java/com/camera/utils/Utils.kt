package com.camera.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.camera.data.models.UserProfile
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.PhotoEntity
import com.google.gson.Gson
import com.mylibrary.databinding.ZCustomToastDesignBinding
import java.io.File
import java.io.FileOutputStream
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

    fun Context.getUserSharedPreferences() = getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun getSelectedEnviroment(context: Context): String {
        return context.getUserSharedPreferences()
            .getString("environment", GlobalEnviromentValues.BASE_URL_PRODUCTION)!!
    }

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

    /**
     * Este metodo crea un archivo en la memoria del dispositivo con la informacion de la imagen pero sin almacenar la imagen
     */
    fun enviarFoto(
        pair: Pair<PhotoEntity, Bitmap>
    ): Pair<String, File> {
        val loaclPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val root = loaclPath.absolutePath
        val myDir = File(root)
        myDir.mkdirs()

        val newFileName = pair.first.fotoID
        val file = File(myDir, "$newFileName.jpeg")

        return Pair(newFileName, file)
    }

    fun generarRegistroArchivo(
        estadoSync: String,
        newFileName: String,
        photoEntity: PhotoEntity,
        user: UserProfile
    ): ArchivoEntity? {
        //Grabo foto en BDD
        val nuevaFotoArch = ArchivoEntity()
        nuevaFotoArch.empID = (user.empId.toLong())
        nuevaFotoArch.archivoID = (newFileName)
        nuevaFotoArch.archCLIID = (photoEntity.fotoCliiD)
        nuevaFotoArch.archCLILOCID = (photoEntity.fotoCLILOCID)
        nuevaFotoArch.archFecha = (getCurrentDateAndTime())
        nuevaFotoArch.archDsc = ("Foto generada desde mobile.")
        nuevaFotoArch.archNomFile = ("$newFileName.jpeg")
        nuevaFotoArch.archActivo = (GeneralStateType.S.toString())
        nuevaFotoArch.modDT = getCurrentDateAndTime()
        nuevaFotoArch.archFlgSync = SyncFlgStateType.Pendiente.toString()
        nuevaFotoArch.archTipo = ("FOTO")
        nuevaFotoArch.archCategoriaID = (photoEntity.fotoCategoria)
        nuevaFotoArch.archObservaciones = (photoEntity.fotoDsc)
        return if (estadoSync.equals(
                SyncFlgStateType.Pendiente.toString(),
                ignoreCase = true
            )
        ) nuevaFotoArch else null
    }

    /**
     * Convierto la imagen de bitmap a un archivo jpeg y la almaceno en el archivo pasado por parametro
     * Para regular la calidad de comprecion del archivo modificar compressionQuality, 0 es mas comprimido/menor callidad
     */
    fun saveFileOnly(sourceUri: Bitmap, destination: File) {
        try {
            val out = FileOutputStream(destination)
            val compresionQuality = 20
            sourceUri.compress(Bitmap.CompressFormat.JPEG, compresionQuality, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LogError("Error in saveFileOnly", e)
        }
    }
}