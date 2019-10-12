package mx.unam.tic.docencia.mapsunamexample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import mx.unam.tic.docencia.mapsunamexample.dialog.DialogGPS


class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private var mainGoogleMap: GoogleMap? = null
    private var googleApiClient: GoogleApiClient? = null
    private var location: Location? = null
    private var locationManager: LocationManager? = null
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            onLocationChanged(p0?.lastLocation)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest? = null
    private var locationManagerAux: LocationManager? = null

    private val isLocationEnabled: Boolean
        get() {
            locationManagerAux = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManagerAux!!.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManagerAux!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

    override fun onStart() {
        super.onStart()
        if (googleApiClient != null)
            googleApiClient!!.connect()
    }

    override fun onStop() {
        super.onStop()
        if (googleApiClient != null && googleApiClient!!.isConnected)
            googleApiClient!!.disconnect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val mainMapFragment = supportFragmentManager
            .findFragmentById(R.id.mainMapFragment)
                as SupportMapFragment
        mainMapFragment.getMapAsync(this)

        googleApiClient = GoogleApiClient.Builder(this).apply {
            addConnectionCallbacks(this@MainActivity)
            addOnConnectionFailedListener(this@MainActivity)
            addApi(LocationServices.API)
        }.build()

        if(checkPermissions())
            startLocation()

        locationFloatingActionButton.setOnClickListener{
            if (checkPermissions())
                startLocation()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_GPS_GRANTED) {
            if (grantResults.size > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocation()
                } else {
                    Toast.makeText(this,"Requieres aceptar los permisos",
                        Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this,"Requieres aceptar los permisos",
                    Toast.LENGTH_LONG).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    override fun onMapReady(p0: GoogleMap?) {
        mainGoogleMap = p0
        mainGoogleMap!!.uiSettings.isZoomGesturesEnabled = true
        mainGoogleMap!!.uiSettings.isZoomControlsEnabled = true
        mainGoogleMap!!.uiSettings.isMyLocationButtonEnabled = true
    }

    override fun onConnected(p0: Bundle?) {
        startLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        googleApiClient!!.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onLocationChanged(p0: Location?) {
        showLocationInMap(p0)
    }

    fun checkPermissions():Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        this, Manifest.permission
                            .ACCESS_COARSE_LOCATION
                    )
            ) {
                val dialogGPS = DialogGPS()
                dialogGPS.setOnAcceptClickListener {
                    acceptPermission()
                }
                dialogGPS.setOnCancelClickListener {
                    Toast.makeText(
                        this,
                        "Tienes que aceptar", Toast.LENGTH_LONG
                    ).show()
                }
                dialogGPS.show(supportFragmentManager, "WARNING-GPS")
                return false
            } else {
                acceptPermission()
                return false
            }
        } else {
            return true
        }
    }

    fun acceptPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_GPS_GRANTED
        )
    }

    fun initLocation() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL
            fastestInterval = FASTEST_INTERVAL
        }

        val builder = LocationSettingsRequest.Builder()
        builder!!.addLocationRequest(locationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.getMainLooper()
        )

    }

    fun startLocation() {
        if(isLocationEnabled) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            location = getLastLocation()
            if (location != null)
                showLocationInMap(location)
            else
                initLocation()
        }else{
            Toast.makeText(this,"Requieres habilitar el GPS",
                Toast.LENGTH_LONG).show()
        }

    }

    fun getLastLocation(): Location? {
        var lastLocation: Location? = null
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            OnSuccessListener<Location> {
                if (it != null)
                    lastLocation = it
            }
        }
        return lastLocation
    }

    fun showLocationInMap(location: Location?) {
        if (location != null) {
            val position = LatLng(location.latitude, location.longitude)
            val cameraPosition = CameraPosition.Builder()
                .target(position)
                .zoom(20f)
                .bearing(20f)
                .tilt(10f)
                .build()
            mainGoogleMap?.let {
                it.addMarker(MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_home_black_18dp))
                    .position(position).title("Aqui Estas"))
                //it.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//mover el mapa sin efecto
                it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//mover el mapa con efecto
            }
            stopLocation()
        }else{
            val position = LatLng(0.3223, -0.72372373)
            val cameraPosition = CameraPosition.Builder()
                .target(position)
                .zoom(20f)
                .bearing(20f)
                .tilt(10f)
                .build()
            mainGoogleMap?.let {
                it.addMarker(MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_home_black_18dp))
                    .position(position).title("Aqui Estas"))
                //it.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//mover el mapa sin efecto
                it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))//mover el mapa con efecto
            }
        }
    }

    fun stopLocation() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        const val PERMISSION_GPS_GRANTED = 2321
        const val UPDATE_INTERVAL = (10 * 1000).toLong()
        const val FASTEST_INTERVAL: Long = 2000
    }
}