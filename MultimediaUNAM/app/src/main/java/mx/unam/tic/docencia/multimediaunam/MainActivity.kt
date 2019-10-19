package mx.unam.tic.docencia.multimediaunam

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        playButton.setOnClickListener {
            if (isPaused) {
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                isPaused = false
            } else {
                mediaPlayer = MediaPlayer()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mediaPlayer.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(
                                AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()
                    )
                } else {
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                }
                mediaPlayer.setDataSource(this, Uri.parse(
                    "https://quetzaly.gcsecurity.mx:89/mp3/musica.mp3"))
                mediaPlayer.prepare()
                mediaPlayer.start()
            }

            initializeProgressSeekBar()
            playButton.isEnabled = false
            pauseButton.isEnabled = true
            stopButton.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                playButton.isEnabled = true
                pauseButton.isEnabled = false
                stopButton.isEnabled = false
            }
        }

        pauseButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                isPaused = true
                playButton.isEnabled = true
                pauseButton.isEnabled = false
                stopButton.isEnabled = true
            }
        }

        stopButton.setOnClickListener {
            if (mediaPlayer.isPlaying || isPaused.equals(true)) {
                isPaused = false
                progressSeekBar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)
                playButton.isEnabled = true
                pauseButton.isEnabled = false
                stopButton.isEnabled = false
            }
        }

        progressSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if (p2) {
                        mediaPlayer.seekTo(p1 * 1000)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }
            }
        )



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

    private fun initializeProgressSeekBar() {
        progressSeekBar.max = mediaPlayer.duration

        runnable = Runnable {
            progressSeekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
}

val MediaPlayer.seconds: Int
    get() {
        return this.duration / 1000
    }

val MediaPlayer.currentSeconds: Int
    get() {
        return this.currentSeconds / 1000
    }
