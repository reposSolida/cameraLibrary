package com.camera.utils

import android.animation.Animator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.camera.presentation.viewModel.DialogFragmentCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mylibrary.R
import com.mylibrary.databinding.CustomAnimationLayoutBinding
import com.mylibrary.databinding.CustomAnimationMultilineLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun Context.customInformationMsgWithAnimation(
    message: String = "Sincronizando Tablas...",
    anim: Int = -1,
    cancelable: Boolean = false,
    textCarousel: Boolean = false,
    closeable: Boolean = true,
    multiLine: Boolean = false,
    loopEveryCustomSeconds: Int = -1,
    loop: Boolean = true,
    customSize: Float = -1f,
    error: Boolean = false,
    title: String = "Info",
    messageTypeIcon: MessageTypeIcon = MessageTypeIcon.INFO,
    onDialogFragmentCallback: DialogFragmentCallback? = null
): Pair<AlertDialog, LottieAnimationView> {
    var localJob: Job? = null
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(cancelable)
    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val lottieAnimationView: LottieAnimationView
    val fab: FloatingActionButton
    val cardAnimCv: CardView
    val msg: TextView

    val view = if (!multiLine){
        val binding = CustomAnimationLayoutBinding.inflate(inflater)
        fab = binding.closeCustomFab
        lottieAnimationView = binding.lottieAnimationView
        binding.headerLayoutTextTv.text = title
        binding.actionIconIv.setImageResource(messageTypeIcon.SvgType())
        msg = binding.actionDescriptionChip
        binding.root
    }
    else {
        val binding = CustomAnimationMultilineLayoutBinding.inflate(inflater)
        fab = binding.closeCustomFab
        lottieAnimationView = binding.lottieAnimationView
        msg = binding.actionDescriptionChip

        if (error) {
            cardAnimCv = binding.cardAnimCv
            cardAnimCv.setCardBackgroundColor(resources.getColor(R.color.card_background_color_error))
        }

        binding.root
    }

    if (closeable) {
        fab.visibility = VISIBLE
    } else {
        fab.visibility = GONE
    }

    msg.isSelected = true

    if (anim != -1) {
        lottieAnimationView.setAnimation(anim)
        lottieAnimationView.scaleX = 1f
        lottieAnimationView.scaleY = 1f
    } else {
        lottieAnimationView.setAnimation(R.raw.anim_loading_truck)
        lottieAnimationView.scaleX = 1.3f
        lottieAnimationView.scaleY = 1.3f
    }

    if (customSize != -1f) {
        lottieAnimationView.scaleX = customSize
        lottieAnimationView.scaleY = customSize
    }
    if (loop) {
        if (loopEveryCustomSeconds > 0) {
            try {
                localJob = CoroutineScope(Dispatchers.IO).launch {
                    while (true) {
                        delay((loopEveryCustomSeconds * 1000).toLong())
                        withContext(Dispatchers.Main) {
                            lottieAnimationView.playAnimation()
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                LogError("Error in customInformationMsgWithAnimation", ex)
            }
        } else {
            lottieAnimationView.repeatCount = LottieDrawable.INFINITE
            lottieAnimationView.repeatMode = LottieDrawable.RESTART
        }
    }

    if (textCarousel) msg.text = listMsgsLoading[0]
    else msg.text = message
    // Crear un AnimatorListener
    val animatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {}
        override fun onAnimationEnd(p0: Animator) {}
        override fun onAnimationCancel(p0: Animator) {}
        override fun onAnimationRepeat(p0: Animator) {
            if (textCarousel) {
                msg.text = listMsgsLoading[(listMsgsLoading.indices).random()]
            }
        }
    }

// Agregar el AnimatorListener a la vista LottieAnimationView
    lottieAnimationView.addAnimatorListener(animatorListener)
    lottieAnimationView.playAnimation()
    builder.setView(view)
    val alertDialog = builder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()

    fab.setOnClickListener {
        lottieAnimationView.cancelAnimation()
        alertDialog.dismiss()
        try {
            localJob?.let { job ->
                job.cancel()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Job canceling exception", ex)
        }
        onDialogFragmentCallback?.onFinishEditDialog(ActionType.FINISH)
    }

    return Pair(alertDialog, lottieAnimationView)
}


val listMsgsLoading = listOf<String>(
    "Cargando datos del sistema...",
    "Actualizando información del servidor...",
    "Procesando datos recibidos...",
    "Validando la entrada del usuario...",
    "Sincronizando la información...",
    "Ejecutando operaciones de mantenimiento...",
    "Optimizando la velocidad de respuesta...",
    "Recopilando información de actividad...",
    "Analizando los datos recibidos...",
    "Comprobando la integridad de los archivos...",
    "Realizando copias de seguridad...",
    "Preparando la información para la presentación...",
    "Ordenando la información en la base de datos...",
    "Verificando la información introducida...",
    "Cargando configuraciones del sistema...",
    "Ajustando la configuración del sistema...",
    "Mapeando los datos recibidos...",
    "Analizando los resultados de la búsqueda...",
    "Verificando la existencia de datos antiguos...",
    "Ajustando la velocidad de la conexión...",
    "Verificando la información de inicio de sesión...",
    "Preparando los datos para la presentación...",
    "Iniciando la transmisión de datos...",
    "Actualizando el estado del sistema...",
    "Comprobando la conexión a internet...",
    "Obteniendo la información del servidor...",
    "Recopilando información de los usuarios...",
    "Comprobando la disponibilidad de los recursos...",
    "Cargando los datos en la memoria...",
    "Ajustando la calidad de los datos...",
    "Revisando los registros del sistema...",
    "Optimizando el rendimiento del sistema...",
    "Comprobando la seguridad del sistema...",
    "Verificando la información de la cuenta...",
    "Limpiando los datos antiguos...",
    "Comprobando la validez de la información...",
    "Actualizando los permisos de acceso...",
    "Configurando la seguridad del sistema...",
    "Recopilando los datos necesarios...",
    "Registrando las operaciones realizadas...",
    "Analizando los resultados de la operación...",
    "Enviando la información al servidor...",
    "Comprobando la disponibilidad del servidor...",
    "Registrando las entradas del usuario...",
    "Configurando los ajustes de privacidad...",
    "Cargando los datos de la caché...",
    "Realizando tareas de mantenimiento programadas...",
    "Verificando la actualización de los datos...",
    "Solicitando la información requerida...",
    "Comprobando la información de la base de datos...",
    "Procesando las solicitudes de los usuarios..."
)
