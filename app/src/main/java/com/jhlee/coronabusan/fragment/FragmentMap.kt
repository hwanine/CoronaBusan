package com.jhlee.coronabusan.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jhlee.coronabusan.MapViewModel
import com.jhlee.coronabusan.R
import kotlinx.android.synthetic.main.fragment_map.view.*

class FragmentMap : Fragment(), OnMapReadyCallback {

    private lateinit var rootView: View
    private lateinit var mMap: GoogleMap
    private var gpsTracker: GpsTracker? = null
    private lateinit var vm: MapViewModel
    private var lat: Double = 35.157662
    private var lng: Double = 129.059111

    private lateinit var marker_home: View
    private lateinit var marker_plenty: View
    private lateinit var marker_some: View
    private lateinit var marker_few: View
    private lateinit var marker_empty: View
    private lateinit var marker_other: View

    private lateinit var marker_plenty_item: Bitmap
    private lateinit var marker_some_item: Bitmap
    private lateinit var marker_few_item: Bitmap
    private lateinit var marker_empty_item: Bitmap
    private lateinit var marker_other_item: Bitmap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        vm = ViewModelProvider(this).get(MapViewModel::class.java)

        setCustomMarkerView()
        mapFragment.getMapAsync(this)
        rootView.refresh_loaction.setOnClickListener {
            rootView.card_view.visibility = View.GONE
            mMap.clear()
            getMask()
        }
        // Inflate the layout for this fragment

        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                val center: CameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition())
                mMap.animateCamera(center)
                marker.showInfoWindow()
                val index = vm.List.indexOfFirst { it.code == marker.tag }
                if(marker.tag != "myloc" && index!! >= 0) {
                    when(vm.maskLatlngList.value?.get(index)?.type) {
                        "01" -> {
                            rootView.marker_type.text = "약국"
                            rootView.map_image.setImageResource(R.drawable.num1)
                        }
                        "02" -> {
                            rootView.marker_type.text = "우체국"
                            rootView.map_image.setImageResource(R.drawable.num2)
                        }
                        "03" -> {
                            rootView.marker_type.text = "농협 하나로마트"
                            rootView.map_image.setImageResource(R.drawable.num3)
                        }
                    }
                    rootView.card_view.visibility = View.VISIBLE
                    rootView.marker_name.text = marker.title
                    rootView.marker_addr.text = vm.maskLatlngList.value?.get(index)?.addr ?: ""
                    rootView.marker_status.text = marker.snippet
                    rootView.marker_refresh.text = vm.maskLatlngList.value?.get(index)?.created_at ?: ""
                    rootView.marker_incoming.text = vm.maskLatlngList.value?.get(index)?.stock_at ?: ""
                } else
                    rootView.card_view.visibility = View.GONE
                return true
            }
        })

        mMap.setOnInfoWindowClickListener { }
        mMap.setOnMapClickListener {
            rootView.card_view.visibility = View.GONE
        }
        getMask()
        vm.legacylat = lat
        vm.legacylng = lng

        markerRendering()
    }

    private fun getMask() {
        vm.maskLatlngList.value?.clear()
        try {
            gpsTracker = GpsTracker(context!!)
            lat = gpsTracker!!.getLatitude()
            lng = gpsTracker!!.getLongitude()
            vm.getMaskLatlng(lat, lng)
            getMyLocation("내 위치")
        } catch(e: Exception) {
            Toast.makeText(
                context,
                "내 위치를 보시려면 위치 서비스를 활성화하고 권한을 허용해주세요.",
                Toast.LENGTH_LONG
            ).show()
            vm.getMaskLatlng(vm.legacylat, vm.legacylng)
            getMyLocation("임시 위치")
        }
    }

    private fun getMyLocation(str: String) {
        val marker_latlng = LatLng(lat, lng)
        var initMarker = mMap.addMarker(MarkerOptions().position(marker_latlng).title(str).icon(BitmapDescriptorFactory.fromBitmap
            (createDrawableFromView(context!!, marker_home))))
        initMarker.tag = "myloc"
        initMarker.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker_latlng, 15F))
    }

    private fun markerRendering() {
        vm.maskLatlngList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list ->
            val data = list[list.size-1]
            val marker = LatLng(data.lat, data.lng)
            var remain = ""
            val remain_temp = data.remain_stat + " "
            if(remain_temp == "plenty ") {
                remain = "재고 100개 이상 보유"
                mMap.addMarker(MarkerOptions().position(marker).title(data.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(marker_plenty_item)).snippet(remain)).tag = data.code
            }
            else if(remain_temp == "some ") {
                remain = "재고 30개 이상 보유"
                mMap.addMarker(MarkerOptions().position(marker).title(data.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(marker_some_item)).snippet(remain)).tag = data.code
            }
            else if(remain_temp == "few ") {
                remain = "재고 2개 이상 보유"
                mMap.addMarker(MarkerOptions().position(marker).title(data.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(marker_few_item)).snippet(remain)).tag = data.code
            }
            else if(remain_temp == "empty ") {
                remain = "재고 1개 이하 보유"
                mMap.addMarker(MarkerOptions().position(marker).title(data.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(marker_empty_item)).snippet(remain)).tag = data.code
            }
            else {
                remain = "재고 알 수 없음"
                mMap.addMarker(MarkerOptions().position(marker).title(data.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(marker_other_item)).snippet(remain)).tag = data.code
            }

            if(list.size == 1 && list[0].lat == 0.0)
                rootView.marker_count.text = "근처 판매 없음"
            else
                rootView.marker_count.text = "근처 " + list.size.toString() + " 곳 발견"
        })
    }

    private fun setCustomMarkerView() {
        marker_home = LayoutInflater.from(context).inflate(R.layout.marker_home_layout, null)
        marker_plenty = LayoutInflater.from(context).inflate(R.layout.marker_plenty, null)
        marker_some = LayoutInflater.from(context).inflate(R.layout.marker_some, null)
        marker_few = LayoutInflater.from(context).inflate(R.layout.marker_few, null)
        marker_empty = LayoutInflater.from(context).inflate(R.layout.marker_empty, null)
        marker_other = LayoutInflater.from(context).inflate(R.layout.marker_other, null)

        marker_plenty_item = createDrawableFromView(context!!, marker_plenty)
        marker_some_item = createDrawableFromView(context!!, marker_some)
        marker_few_item = createDrawableFromView(context!!, marker_few)
        marker_empty_item = createDrawableFromView(context!!, marker_empty)
        marker_other_item = createDrawableFromView(context!!, marker_other)
    }

    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        view.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap: Bitmap = Bitmap.createBitmap(
            view.getMeasuredWidth(),
            view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    internal class GpsTracker(private val mContext: Context) : Service(),
        LocationListener {
        private lateinit var location: Location
        private var latitude = 0.0
        private var longitude = 0.0
        protected var locationManager: LocationManager? = null
        fun getLocation(): Location? {
            try {
                locationManager =
                    mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGPSEnabled =
                    locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkEnabled =
                    locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (!isGPSEnabled && !isNetworkEnabled) {
                } else {
                    val hasFineLocationPermission = ContextCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
                    ) {
                    } else return null
                    if (isNetworkEnabled) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                            this
                        )
                        if (locationManager != null) {
                            location =
                                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager!!.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                                this
                            )
                            if (locationManager != null) {
                                location =
                                    locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location != null) {
                                    latitude = location!!.latitude
                                    longitude = location!!.longitude
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("@@@", "" + e.toString())
            }
            return location
        }

        fun getLatitude(): Double {
            if (location != null) {
                latitude = location!!.latitude
            }
            return latitude
        }

        fun getLongitude(): Double {
            if (location != null) {
                longitude = location!!.longitude
            }
            return longitude
        }

        override fun onLocationChanged(location: Location) {}
        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
        }

        override fun onBind(arg0: Intent): IBinder? {
            return null
        }

        fun stopUsingGPS() {
            if (locationManager != null) {
                locationManager!!.removeUpdates(this@GpsTracker)
            }
        }

        companion object {
            private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
            private const val MIN_TIME_BW_UPDATES = 1000 * 60 * 1.toLong()
        }

        init {
            getLocation()
        }
    }
}
