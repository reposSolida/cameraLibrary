package com.camera.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.camera.model.models.entities.ArchivoEntity
import com.camera.model.models.entities.PhotoEntity
import com.camera.model.utils.SyncFlgStateType
import com.camera.model.utils.Utils
import com.camera.model.utils.Utils.customToast
import com.camera.model.utils.Utils.toJson
import com.camera.utils.AnimExtras
import com.camera.utils.CustomToast
import com.camera.utils.GeneralStateType
import com.camera.utils.ParametrosValues
import com.camera.utils.customInformationMsgWithAnimation
import com.camera.view.adapters.PhotoPagerAdapter
import com.camera.viewModel.OnBtnPressListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mylibrary.R
import com.mylibrary.databinding.FragmentCamaraBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Random


/**
 ** Created by Carlos A. Novaez Guerrero on 7/7/2023 10:25 AM
 ** cnovaez.dev@outlook.com
 **/
@AndroidEntryPoint
class CameraFragment : BaseFragment(), OnBtnPressListener, Fragment() {

    private lateinit var btnCamera: AppCompatButton
    private lateinit var btnGuardar: AppCompatButton
    private lateinit var btnSubmit: FloatingActionButton
    private lateinit var ivImage: ImageView

    private var upflag = false
    private var newFileName = ""
    private var bitmap: Bitmap? = null
    private var bitmapRotate: Bitmap? = null
    var imagepath = ""
    var file: File? = null
    private lateinit var fr_empty: LinearLayout
    private lateinit var spinnerCategoria: Spinner
    var categoriaSeleccionada = ""
    var observaciones = ""
    var galeria = false
    var calidadImagen = 50
    var photoUri: Uri? = null
    private lateinit var binding: FragmentCamaraBinding
    private val map_categories = mutableMapOf<String, String>()

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private val arraySeleccionDeFotos: MutableList<Pair<PhotoEntity, Bitmap>> = mutableListOf()

    private lateinit var adapter: PhotoPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCamaraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // El fragment anterior va a tener que posicionarse como backFragment
        //setActiviyBackFragment(R.id.amd_fragment_client_visitation)  TODO
        setUpViews()
        CoroutineScope(Dispatchers.IO).launch {
            setUpData()
        }
    }
/*
    fun setActiviyBackFragment(
        frag: Int = (requireActivity().findNavController(R.id.navHost)?.currentDestination?.id
            ?: -1), bundle: Bundle? = null
    ) {
        try {
            MainActivity.setBackFragment(frag, bundle)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error setting back fragment", ex)
        }
    }
*/
    private fun setUpViews() {
        btnSubmit = binding.btnSubmit
        btnCamera = binding.btnCamera
        btnGuardar = binding.fcBtnGrabar
        ivImage = binding.ivImage
        fr_empty = binding.frEmpty
        spinnerCategoria = binding.fcSpinnerCat

        // Inicializar el lanzador de actividad para capturar la foto con la cámara
        cameraLauncher = camaraLouncher()

        galleryLauncher = galleryLauncher()

        btnSubmit.setOnClickListener {
            submitData()
        }
        btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoUri = createPhotoUri()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            cameraLauncher.launch(photoUri)
        }
        btnGuardar.setOnClickListener {
            galeria = true
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            galleryLauncher.launch(intent)
        }

    }

    private fun camaraLouncher(): ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("camera louncher", "Entered cameraLauncher success: $success")
            if (success) {
                if (photoUri != null) {
                    arraySeleccionDeFotos.clear()
                    val photoBitmap = getBitmapFromUri(photoUri!!)
                    if (photoBitmap != null) {
                        generarImagenDesdeCamara(photoBitmap)
                    }
                }
            }
        }
    }

    private fun submitData() {
        var success = false
        if (arraySeleccionDeFotos.isEmpty()) {
            val context: Context = requireContext()
            context.customToast(CustomToast(getString(R.string.debe_seleccionar_imagen)))
            return
        }
        val total = arraySeleccionDeFotos.size
        CoroutineScope(Dispatchers.IO).launch {
            val archivos: MutableList<ArchivoEntity> = mutableListOf()
            var qty = 1
            for (pair in arraySeleccionDeFotos) {
                withContext(Dispatchers.Main) {
                    context?.let { context ->
                        AnimExtras.toggleAnimGeneric(
                            true,
                            getString(R.string.enviando_fotos_al_servidor),
                            R.raw.anim_sending_photos,
                            title = "Enviando foto $qty de $total, por favor espere...",
                            context = context
                        )

                    }
                }
                qty += 1
                pair.first.fotoActiva = (GeneralStateType.S.toString())
                pair.first.fotoFlgSync = (SyncFlgStateType.Pendiente.toString())
                pair.first.fotoCategoria = (categoriaSeleccionada)
                pair.first.fotoDsc = (observaciones)

                success = storePhotoRelatedData(pair, archivos)
            }
            Log.d("uploadFiles","Qty of archivos to store: ${archivos.size}")
            if (archivos.isNotEmpty()) {
                insertArchivesListEntireLifeCycle(archivos.map { it.toApi() }, true)
            }
            withContext(Dispatchers.Main) {
                if (success){
                    requireContext().customToast(
                        CustomToast(
                            getString(R.string.pics_sended_label),
                            duration = Toast.LENGTH_LONG
                        )
                    )
                }
                ivImage.setImageResource(0)
                if (::adapter.isInitialized) {
                    adapter.clearList()
                    arraySeleccionDeFotos.clear()
                    binding.imagesCorouselRv.adapter?.notifyDataSetChanged()
                }
                AnimExtras.dismissData()
            }

            if (!syncPendingData(showNoInternetMsg = false)){
                withContext(Dispatchers.Main) {
                    requireContext().customInformationMsgWithAnimation(
                        getString(R.string.device_without_internet_tried_to_sync),
                        R.raw.anim_data_into_phone
                    )
                }
            }
        }
    }

    private fun galleryLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("galleryLouncher","Entered galleryLauncher")
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.clipData?.let { clipData ->
                    arraySeleccionDeFotos.clear()
                    val selectedImages = mutableListOf<Uri>()
                    for (i in 0 until clipData.itemCount) {
                        val uri: Uri? = clipData.getItemAt(i).uri
                        uri?.let {
                            try {
                                val inputStream =
                                    requireActivity().contentResolver.openInputStream(it)
                                val photo = BitmapFactory.decodeStream(inputStream)
                                generarImagenDesdeGaleria(photo)
                                selectedImages.add(uri)
                                inputStream?.close()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.d("error","Error in galleryLauncher")
                            }
                        }
                    }
                    withContext(Dispatchers.Main) {
                        displayPhotoCarousel()
                    }
                }
            }
        }
    }

    private fun displayPhotoCarousel() {
        val viewpager = binding.imagesCorouselRv
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewpager.layoutManager = layoutManager
        adapter = PhotoPagerAdapter(arraySeleccionDeFotos.map {
            Bitmap.createScaledBitmap(
                it.second, 200,
                (it.second.height * (200f / it.second.width.toFloat())).toInt(),
                false
            )
        }.toMutableList(), this)
        viewpager.adapter = adapter
        viewpager.visibility = View.VISIBLE
    }

    private fun createPhotoUri(): Uri {
        val photoName = "${Utils.getCurrentDateAndTime()}.jpg"
        val storageDir = requireActivity().getExternalFilesDir(null)
        val photoFile = File(storageDir, photoName)
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("error","Error in getBitmapFromUri", e)
            null
        }
    }

    private suspend fun setUpData() {
        val calIma: String = getParamByParId(ParametrosValues._CALIDAD_IMAGEN)?.parTxt.orEmpty()
        if (calIma.isNotEmpty()) {
            calidadImagen = Integer.valueOf(calIma)
        }

        val lCat = getAllCategoriasDBUseCase()
        map_categories[getString(R.string.seleccione_categoria_label)] = getString(R.string.no_data)
        for (categoria in lCat) {
            map_categories[categoria.categoriaDsc.orEmpty()] = categoria.categoriaID
        }

        val adapterCategoria = ArrayAdapter(
            requireActivity(), R.layout.item_spinner, map_categories.keys.toMutableList()
        )
        withContext(Dispatchers.Main) {
            adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategoria.prompt = "Seleccione categoria"
            spinnerCategoria.adapter = adapterCategoria
            spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    pos: Int,
                    id: Long
                ) {
                    val categoria = map_categories[parent.getItemAtPosition(pos) as String]
                    if (categoria != getString(R.string.no_data)) {
                        categoriaSeleccionada = categoria.orEmpty()
                    } else {
                        categoriaSeleccionada = ""
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            // Observaciones
            val obs = binding.txtObservaciones
            obs.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    observaciones = obs.text.toString()
                }
            })
        }


        //TODO review the need for this   ArchivoDS().syncSet(syncSet)

    }

    suspend fun generarImagenDesdeCamara(data: Bitmap) {
        try {
            bitmap = data
            if (java.lang.Float.valueOf(getImageOrientation().toFloat()) >= 0) {
                bitmapRotate =
                    rotateImage(bitmap!!, (getImageOrientation().toFloat()))
            } else {
                bitmapRotate = bitmap
                bitmap!!.recycle()
            }
            withContext(Dispatchers.Main) {
                displayPhoto(bitmapRotate!!)
            }
            val bos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, calidadImagen, bos)
            val bArray = bos.toByteArray()

            bos.close()
            val foto = PhotoEntity()
            foto.empID = (user.empId.toLong())
            foto.fotoCliiD = (UserSessionData.clientLocal?.cliID.orEmpty())
            foto.fotoCLILOCID = (UserSessionData.clientLocal?.cliLOCID.orEmpty())
            foto.fotoFecha = (Utils.getTodayTypeTwo())
            foto.fotoDsc = (observaciones)
            foto.fotoPathFile = ("")
            foto.fotoCategoria = (categoriaSeleccionada)
            foto.fotoGrupo = ("")
            foto.fotoActiva = ("N")
            foto.fotoFlgSync = ("P")
            foto.fotoBlob = (bArray)
            foto.fotoModdt = (Utils.getCurrentDateAndTime())

            //Muestro la imagen, la cargo en el ImageView
            ivImage.visibility = View.VISIBLE
            //    ivImage.setImageBitmap(bitmapRotate)

            //Saving image to mobile internal memory for sometime
            val loaclPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val root = loaclPath.absolutePath
            val myDir = File(root)
            myDir.mkdirs()
            val generator = Random()
            var n = 10000
            n = generator.nextInt(n)

            //Give the file name that u want
            newFileName =
                UserSessionData.clientLocal!!.cliID + "_" + Utils.ahoraMilisegundos(user)
            imagepath = "$root/$newFileName.jpeg"
            file = File(myDir, "$newFileName.jpeg")
            upflag = true
            foto.fotoID = (newFileName)
            foto.fotoNombre = ("$newFileName.jpeg")
            arraySeleccionDeFotos.add(Pair(foto, bitmap!!))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("error","Error inserting generarImagenDesdeCamara", e)
        }
    }

    suspend fun generarImagenDesdeGaleria(data: Bitmap) {
        try {
            bitmap = data
            if ((getImageOrientation().toFloat()) >= 0) {
                bitmapRotate =
                    rotateImage(bitmap!!, (getImageOrientation().toFloat()))
            } else {
                bitmapRotate = bitmap
                bitmap!!.recycle()
            }
            withContext(Dispatchers.Main) {
                displayPhoto(bitmap!!)
            }
            val bos = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, calidadImagen, bos)
            val bArray = bos.toByteArray()
            bos.close()
            val foto = PhotoEntity()
            foto.empID = (user.empId.toLong())
            foto.fotoCliiD = (UserSessionData.clientLocal?.cliID.orEmpty())
            foto.fotoCLILOCID = (UserSessionData.clientLocal?.cliLOCID.orEmpty())
            foto.fotoFecha = (_Utils.getTodayTypeTwo())
            foto.fotoDsc = (observaciones)
            foto.fotoPathFile = ("")
            foto.fotoCategoria = (categoriaSeleccionada)
            foto.fotoGrupo = ("")
            foto.fotoActiva = (GeneralStateType.S.toString())
            foto.fotoFlgSync = (SyncFlgStateType.Pendiente.toString())
            foto.fotoBlob = (bArray)
            foto.fotoModdt = (Utils.getCurrentDateAndTime())
            //  ivImage.setImageBitmap(bitmapRotate)
            val loaclPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val root = loaclPath.absolutePath
            val myDir = File(root)
            myDir.mkdirs()
            val generator = Random()
            newFileName =
                UserSessionData.clientLocal!!.cliID + "_" + Utils.ahoraMilisegundos(user)
            imagepath = "$root/$newFileName.jpeg"
            file = File(myDir, "$newFileName.jpeg")
            upflag = true
            foto.fotoID = (newFileName)
            foto.fotoNombre = ("$newFileName.jpeg")
            arraySeleccionDeFotos.add(Pair(foto, bitmap!!))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("error","Error in generarImagenDesdeGaleria", e)
        }
    }

    //In some mobiles image will get rotate so to correting that this code will help us
    private fun getImageOrientation(): Int {
        Log.d("Camera fragment", "Entered getImageOrientation")
        val imageColumns =
            arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.ORIENTATION)
        val imageOrderBy = MediaStore.Images.Media._ID + " DESC"
        val cursor: Cursor? = requireActivity().contentResolver!!.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageColumns, null, null, imageOrderBy
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION)
            val orientation =
                cursor.getInt(if (idx > 0) idx else 0)
            Log.d("error","orientation is: $orientation")
            cursor.close()
            orientation
        } else {
            0
        }
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val retVal: Bitmap
        val matrix = Matrix()
        matrix.postRotate(angle)
        retVal = Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        return retVal
    }

    private fun displayPhoto(photo: Bitmap) {
        try {
            Log.d("Displaying photo","Entered in displayPhoto ${photo.toJson() ?: "null"}")
            val requestOptions = RequestOptions().apply {
                // Establecer la opción ALLOW_HARDWARE_CONFIG en falso
                disallowHardwareConfig()
            }
            Glide.with(this)
                .load(photo)
                .apply(requestOptions)
                .fitCenter()
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        ivImage.setImageDrawable(resource)
                        fr_empty.visibility = View.GONE
                        ivImage.visibility = View.VISIBLE
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        // Acción que deseas ejecutar si hay un error al cargar la imagen
                        ivImage.visibility = View.VISIBLE
                        ivImage.setImageResource(R.drawable.camera2)
                        fr_empty.visibility = View.GONE
                    }
                })
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogError("Error in displayPhoto", ex)
        }

    }

    override fun onResume() {
        super.onResume()
        if (ivImage.drawable == null) {
            fr_empty.visibility = View.VISIBLE
            ivImage.visibility = View.GONE
        } else {
            fr_empty.visibility = View.GONE
            ivImage.visibility = View.VISIBLE
        }
    }

    override fun buttonPress(longPress: Boolean, obj: Any?) {
        obj?.let {
            val image = obj as Int
            displayPhoto(arraySeleccionDeFotos[image].second)
        }
    }


}