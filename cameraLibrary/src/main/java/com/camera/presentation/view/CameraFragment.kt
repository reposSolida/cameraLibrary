package com.camera.presentation.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.camera.data.models.UserProfile
import com.camera.data.models.UserSessionData
import com.camera.data.models.entities.ArchivoEntity
import com.camera.data.models.entities.ClientesLocalesEntity
import com.camera.data.models.entities.PhotoEntity
import com.camera.presentation.view.adapters.PhotoPagerAdapter
import com.camera.presentation.viewModel.CamaraFragmentViewModel
import com.camera.presentation.viewModel.OnBtnPressListener
import com.camera.utils.AnimExtras
import com.camera.utils.CustomToast
import com.camera.utils.GeneralStateType
import com.camera.utils.LogError
import com.camera.utils.LogInfo
import com.camera.utils.ParametrosValues
import com.camera.utils.SyncFlgStateType
import com.camera.utils.Utils
import com.camera.utils.Utils.customToast
import com.camera.utils.Utils.toJson
import com.camera.utils.customInformationMsgWithAnimation
import com.google.gson.Gson
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

@AndroidEntryPoint
class CameraFragment : OnBtnPressListener, Fragment() {

    private val REQUEST_PERMISSIONS = 1
    private var permissions_granted = false
    private var upflag = false
    private var newFileName = ""
    private var bitmap: Bitmap? = null
    private var bitmapRotate: Bitmap? = null
    private var imagepath = ""
    private var file: File? = null
    private var categoriaSeleccionada = ""
    private var observaciones = ""
    private var galeria = false
    private var calidadImagen = 50
    private var photoUri: Uri? = null
    private var binding: FragmentCamaraBinding? = null
    private val map_categories = mutableMapOf<String, String>()
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val arraySeleccionDeFotos: MutableList<Pair<PhotoEntity, Bitmap>> = mutableListOf()
    private lateinit var adapter: PhotoPagerAdapter
    private lateinit var user: UserProfile
    private val viewModel: CamaraFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCamaraBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // El fragment anterior va a tener que posicionarse como backFragment
        //setActiviyBackFragment(R.id.amd_fragment_client_visitation)  TODO

        val jsonUSer = arguments?.getString("jsonUserProfile") ?: ""
        val cliLOCID = arguments?.getString("cliLOCID") ?: ""


        user = Gson().fromJson(jsonUSer, UserProfile::class.java)

        UserSessionData.user = user
        UserSessionData.clientLocal = ClientesLocalesEntity(cliLOCID = cliLOCID)

        viewModel.setUser(user)

        setUpViews()
        CoroutineScope(Dispatchers.IO).launch {
            setUpData()
        }

    }

    private fun setUpViews() {

        // Inicializar el lanzador de actividad para capturar la foto con la cámara
        cameraLauncher = camaraLouncher()

        galleryLauncher = galleryLauncher()

        binding?.btnSubmit?.setOnClickListener {
            submitData()
        }
        binding?.btnCamera?.setOnClickListener {
            if (permissions_granted || checkPermissions()) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                photoUri = viewModel.createPhotoUri()
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                cameraLauncher.launch(photoUri)
            }
        }
        binding?.fcBtnGrabar?.setOnClickListener {
            galeria = true
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            galleryLauncher.launch(intent)
        }

    }

    private fun camaraLouncher(): ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        CoroutineScope(Dispatchers.IO).launch {
            LogInfo("Entered cameraLauncher success: $success")
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

    private fun galleryLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            CoroutineScope(Dispatchers.IO).launch {
                LogInfo("Entered galleryLauncher")
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    data?.clipData?.let { clipData ->
                        arraySeleccionDeFotos.clear()
                        val selectedImages = mutableListOf<Uri>()
                        for (i in 0 until clipData.itemCount) {
                            val uri: Uri? = clipData.getItemAt(i).uri
                            uri?.let { it ->
                                try {
                                    val inputStream =
                                        requireActivity().contentResolver.openInputStream(it)
                                    val photo = BitmapFactory.decodeStream(inputStream)
                                    generarImagenDesdeGaleria(photo)
                                    selectedImages.add(uri)
                                    inputStream?.close()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    context?.let { context ->
                                        LogError("Error in galleryLauncher", e)
                                    }
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

    private fun submitData() {
        var success = true
        if (arraySeleccionDeFotos.isEmpty()) {
            val context: Context = requireContext()
            context.customToast(CustomToast(getString(R.string.debe_seleccionar_imagen)))
            return
        }
        val total = arraySeleccionDeFotos.size
        LogInfo("Qty of fotos to store: $total")
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

                if (!viewModel.storePhotoRelatedData(pair, archivos)){
                    success = false
                }
            }
            LogInfo("Qty of archivos to store: ${archivos.size}")
            if (archivos.isNotEmpty()) {
                viewModel.insertArchivesListEntireLifeCycle(archivos.map { it.toApi() }, true)
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
                binding?.ivImage?.setImageResource(0)
                if (::adapter.isInitialized) {
                    adapter.clearList()
                    arraySeleccionDeFotos.clear()
                    binding?.imagesCorouselRv?.adapter?.notifyDataSetChanged()
                }
                AnimExtras.dismissData()
            }
            val syncSuccesfull =
                viewModel.syncPendingData(showNoInternetMsg = false)
            if (!syncSuccesfull) {
                withContext(Dispatchers.Main) {
                    requireContext().customInformationMsgWithAnimation(
                        getString(R.string.device_without_internet_tried_to_sync),
                        R.raw.anim_data_into_phone
                    )
                }
            }
        }
    }

    private fun displayPhotoCarousel() {
        val viewpager = binding?.imagesCorouselRv
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewpager?.layoutManager = layoutManager
        adapter = PhotoPagerAdapter(arraySeleccionDeFotos.map {
            Bitmap.createScaledBitmap(
                it.second, 200,
                (it.second.height * (200f / it.second.width.toFloat())).toInt(),
                false
            )
        }.toMutableList(), this)
        viewpager?.adapter = adapter
        viewpager?.visibility = View.VISIBLE
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
            LogError("Error in getBitmapFromUri", e)

            null
        }
    }

    private suspend fun setUpData() {
        val calIma: String = viewModel.getParamByParId(ParametrosValues._CALIDAD_IMAGEN)?.parTxt.orEmpty()
        if (calIma.isNotEmpty()) {
            calidadImagen = Integer.valueOf(calIma)
        }

        val lCat = viewModel.getAllCategoriasDBUseCase()
        map_categories[getString(R.string.seleccione_categoria_label)] = getString(R.string.no_data)
        for (categoria in lCat) {
            map_categories[categoria.categoriaDsc.orEmpty()] = categoria.categoriaID
        }

        val adapterCategoria = ArrayAdapter(
            requireActivity(), R.layout.item_spinner, map_categories.keys.toMutableList()
        )
        withContext(Dispatchers.Main) {
            adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.fcSpinnerCat?.prompt = "Seleccione categoria"
            binding?.fcSpinnerCat?.adapter = adapterCategoria
            binding?.fcSpinnerCat?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
            val obs = binding?.txtObservaciones
            obs?.addTextChangedListener(object : TextWatcher {
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
                    viewModel.rotateImage(bitmap!!, (getImageOrientation().toFloat()))
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
            binding?.ivImage?.visibility = View.VISIBLE

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
            context?.let { c ->
                LogError("Error inserting generarImagenDesdeCamara", e)
            }
        }
    }

    suspend fun generarImagenDesdeGaleria(
        data: Bitmap) {
        try {
            bitmap = data
            if ((getImageOrientation().toFloat()) >= 0) {
                bitmapRotate =
                    viewModel.rotateImage(bitmap!!, (getImageOrientation().toFloat()))
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
            foto.fotoFecha = (Utils.getTodayTypeTwo())
            foto.fotoDsc = (observaciones)
            foto.fotoPathFile = ("")
            foto.fotoCategoria = (categoriaSeleccionada)
            foto.fotoGrupo = ("")
            foto.fotoActiva = (GeneralStateType.S.toString())
            foto.fotoFlgSync = (SyncFlgStateType.Pendiente.toString())
            foto.fotoBlob = (bArray)
            foto.fotoModdt = (Utils.getCurrentDateAndTime())
            val loaclPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val root = loaclPath.absolutePath
            val myDir = File(root)
            myDir.mkdirs()
            newFileName =
                (UserSessionData.clientLocal?.cliID.orEmpty()) + "_" + Utils.ahoraMilisegundos(user)
            imagepath = "$root/$newFileName.jpeg"
            file = File(myDir, "$newFileName.jpeg")
            upflag = true
            foto.fotoID = (newFileName)
            foto.fotoNombre = ("$newFileName.jpeg")
            arraySeleccionDeFotos.add(Pair(foto, bitmap!!))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            context?.let { c ->
                LogError("Error in generarImagenDesdeGaleria", e)
            }
        }
    }

    //In some mobiles image will get rotate so to correting that this code will help us
    private fun getImageOrientation(): Int {
        LogInfo("Entered getImageOrientation", true)
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
            LogInfo("orientation is: $orientation", true)
            cursor.close()
            orientation
        } else {
            0
        }
    }

    private fun displayPhoto(photo: Bitmap) {
        try {
            LogInfo("Entered in displayPhoto ${photo.toJson() ?: "null"}", true)
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
                        binding?.ivImage?.setImageDrawable(resource)
                        binding?.frEmpty?.visibility = View.GONE
                        binding?.ivImage?.visibility = View.VISIBLE
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        // Acción que deseas ejecutar si hay un error al cargar la imagen
                        binding?.ivImage?.visibility = View.VISIBLE
                        binding?.ivImage?.setImageResource(R.drawable.camera2)
                        binding?.frEmpty?.visibility = View.GONE
                    }
                })
        } catch (ex: Exception) {
            ex.printStackTrace()
            context?.let { c ->
                LogError("Error in displayPhoto", ex)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (binding?.ivImage?.drawable == null) {
            binding?.frEmpty?.visibility = View.VISIBLE
            binding?.ivImage?.visibility = View.GONE
        } else {
            binding?.frEmpty?.visibility = View.GONE
            binding?.ivImage?.visibility = View.VISIBLE
        }
    }

    override fun buttonPress(longPress: Boolean, obj: Any?) {
        obj?.let {
            val image = obj as Int
            displayPhoto(arraySeleccionDeFotos[image].second)
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.CAMERA
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissions(permissionsToRequest.toTypedArray(), REQUEST_PERMISSIONS)
        } else {
            permissions_granted = true
            return true
        }
        permissions_granted = false
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                permissions_granted = true
            } else {
                // Algunos permisos fueron denegados
                permissions_granted = false
                Toast.makeText(context, "Permissions are required to use the camera and storage", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

