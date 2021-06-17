package com.example.mymoviedatabase.view.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviedatabase.MAIN_SERVICE_STRING_EXTRA
import com.example.mymoviedatabase.MainService
import com.example.mymoviedatabase.R
import com.example.mymoviedatabase.app.App.Companion.getHistoryDao
import com.example.mymoviedatabase.databinding.MainFragmentBinding
import com.example.mymoviedatabase.model.Movie
import com.example.mymoviedatabase.room.LocalRepository
import com.example.mymoviedatabase.room.LocalRepositoryImpl
import com.example.mymoviedatabase.view.adapters.MDBAdapter
import com.example.mymoviedatabase.viewmodel.AppState
import com.example.mymoviedatabase.viewmodel.MainViewModel
import com.example.retrofittest2.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.*
import java.io.IOException
import java.util.jar.Manifest

const val BROADCAST_INTENT_FILTER = "BROADCAST INTENT FILTER"
const val LIST_SETTINGS = "LIST_SETTINGS_KEY"
var ADULT_SETTINGS = "ADULT_SETTINGS"
var FIRST_RUN : Boolean = true
val REQUEST_CODE = 42
private const val REFRESH_PERIOD = 100L
private const val MINIMAL_DISTANCE = 10f

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val fragmentReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            //Достаём данные из интента
            intent.getStringExtra(MAIN_SERVICE_STRING_EXTRA)?.let {
                Toast.makeText(context, MAIN_SERVICE_STRING_EXTRA, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())

        context?.registerReceiver(fragmentReceiver, IntentFilter(BROADCAST_INTENT_FILTER))

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        val listsSettings = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(LIST_SETTINGS, "")
        val adultSettings = activity?.getPreferences(Context.MODE_PRIVATE)?.getString(ADULT_SETTINGS,"true")

        if (FIRST_RUN) {
            showLists(listsSettings, adultSettings)
            binding.statusTv.text = "$listsSettings, $adultSettings"
            FIRST_RUN = false
        } else {
            showLists(listsSettings, adultSettings)

            setFragmentResultListener("request") { requestKey, bundle ->

                showLists(bundle.getString("key"),bundle.getString(ADULT_SETTINGS))

                activity?.let {
                    with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                        putString(LIST_SETTINGS, bundle.getString("key"))
                        apply()
                    }
                }
            }
        }

                 context?.let {
                    it.startService(Intent(it, MainService::class.java).apply {
                        initTopList()
                        initPopList()
                    })
                }
        return binding.root
    }

    private fun showLists(listsSettings : String?, adultSettings : String?) {
        when (listsSettings) {
            "Only popular" -> {
                binding.topListRecyclerView.hide()
                binding.topHeaderTv.hide()
            }
            "Only top" -> {
                binding.popListRecyclerView.hide()
                binding.popularHeaderTv.hide()
            }
            "Both lists" -> {
                binding.popListRecyclerView.show()
                binding.popularHeaderTv.show()
                binding.topListRecyclerView.show()
                binding.topHeaderTv.show()
            }
        }
        binding.statusTv.text = "$listsSettings, $adultSettings"
    }

    fun View.show() : View {
        if (visibility != View.VISIBLE) {
            visibility = View.VISIBLE
        }
        return this
    }

    fun View.hide() : View {
        if (visibility != View.GONE) {
            visibility = View.GONE
        }
        return this
    }

     private fun initTopList() {
        val ADULT_CONTENT = activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean("ADULT_CONTENT",true)
        binding.topListLoadingLayout.show()
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getTopMovies(getString(R.string.api_key), "ru", ADULT_CONTENT)

        call.enqueue(object : Callback<Movies>{

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    binding.topListLoadingLayout.hide()
                    binding.topListRecyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = MDBAdapter(response.body()!!.results, onClickListener = { view, movie ->
                            activity?.supportFragmentManager?.apply {
                                historyRepository.saveEntity(movie)
                                beginTransaction()
                                    .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
                                        Toast.makeText(context, "Loading Movie Page", Toast.LENGTH_SHORT).show()
                                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                                    }))
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()
                        }})
                    }
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

     private fun initPopList() {
        val ADULT_CONTENT = activity?.getPreferences(Context.MODE_PRIVATE)?.getBoolean("ADULT_CONTENT",true)
        binding.popListLoadingLayout.show()
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getPopularMovies(getString(R.string.api_key), "ru", ADULT_CONTENT)
        call.enqueue(object : Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    binding.popListLoadingLayout.hide()
                    binding.popListRecyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = MDBAdapter(response.body()!!.results, onClickListener = { view, movie ->
                            activity?.supportFragmentManager?.apply {
                                historyRepository.saveEntity(movie)
                                beginTransaction()
                                    .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
                                        Toast.makeText(context, "Loading Movie Page", Toast.LENGTH_SHORT).show()
                                        putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
                                    }))
                                    .addToBackStack("")
                                    .commitAllowingStateLoss()
                        }})
                    }
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.newListRecyclerView.adapter = newListAdapter
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()
        binding.mainFragmentFABLocation.setOnClickListener { checkPermission() }
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun showRationaleDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                    .setTitle(getString(R.string.dialog_rationale_title))
                    .setMessage(getString(R.string.dialog_rationale_meaasge))
                    .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                        requestPermission()
                    }
                    .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
        }
    }

    private fun requestPermission() {
        requestPermissions(
                arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>, grantResults: IntArray
    ) {
        checkPermissionsResult(requestCode, grantResults)
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        showDialog(
                                getString(R.string.dialog_title_no_gps),
                                getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                    )
                }
                return
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
        }
    }

    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                            context,
                            ACCESS_FINE_LOCATION
                    ) ==
                    PackageManager.PERMISSION_GRANTED
            ) {
                // Получить менеджер геолокаций
                val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        // Будем получать геоположение через каждые 1 секунд или каждые 10 метров
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                REFRESH_PERIOD,
                                MINIMAL_DISTANCE,
                                onLocationListener
                        )
                    }
                } else {
                    val location =
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        showDialog(
                                getString(R.string.dialog_title_gps_turned_off),
                                getString(R.string.dialog_message_last_location_unknown)
                        )
                    } else {
                        getAddressAsync(context, location)
                        showDialog(
                                getString(R.string.dialog_title_gps_turned_off),
                                getString(R.string.dialog_message_last_known_location)
                        )
                    }
                }
            } else {
                showRationaleDialog()
            }
        }
    }

    private val onLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAddressAsync(
            context: Context,
            location: Location
    ) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                )
                status_Tv.post {
                    showAddressDialog(addresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {

            AlertDialog.Builder(it)
                    .setTitle(getString(R.string.dialog_address_title))
                    .setMessage(address)
                    .setPositiveButton("Show address") { _, _ ->
                        status_Tv.text = address
                        Toast.makeText(context, "You are here!", Toast.LENGTH_SHORT).show()
//                        openDetailsFragment(
//                                Weather(
//                                        City(
//                                                address,
//                                                location.latitude,
//                                                location.longitude
//                                        )
//                                )
//                        )
                    }
                    .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //newListAdapter.setMovieList(appState.newMovieList)
                //popListAdapter.setMovieList(appState.popMovieList)
            }
            is AppState.Loading -> {
                //Snackbar.make(binding.statusTv, "Loading...", Snackbar.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                binding.mainFragmentRootView.snackBarCreateAndShow(
                    getString(R.string.reload),
                    { viewModel.getMovieFromLocalSource() })
            }
        }

    }

    //KOTLIN style for LOCAL SOURCE
//    private val newListAdapter = NewListAdapter(object : OnItemViewClickListener {
//        override fun onItemViewClick(movie: Movie) {
//            activity?.supportFragmentManager?.apply {
//                beginTransaction()
//                        .replace(R.id.container, MovieFragment.newInstance(Bundle().apply {
//                            putParcelable(MovieFragment.BUNDLE_EXTRA, movie)
//                        }))
//                        .addToBackStack("")
//                        .commitAllowingStateLoss()
//            }
//        }
//    })

//    private fun View.showSnackBar(
//            text: String,
//            actionText: String,
//            action: (View) -> Unit,
//            length: Int = Snackbar.LENGTH_INDEFINITE
//    ) {
//        Snackbar.make(this, text, length).setAction(actionText, action).show()
//    }

    fun View.snackBarCreateAndShow(
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, R.string.error, length).setAction(actionText, action).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

}