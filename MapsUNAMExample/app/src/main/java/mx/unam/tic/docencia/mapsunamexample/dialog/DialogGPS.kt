package mx.unam.tic.docencia.mapsunamexample.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import mx.unam.tic.docencia.mapsunamexample.R

class DialogGPS : DialogFragment(){

    private lateinit var onAcceptClickListener: () -> Unit
    private lateinit var onCancelClickListener: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder= context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(
            getString(
                R.string.title_dialog_gps
            )
        )
        builder?.setMessage(getString(R.string.message_dialog_gps))
        builder?.setPositiveButton(android.R.string.ok){ dialog,wich ->
            if (::onAcceptClickListener.isInitialized)
                onAcceptClickListener()
        }
        builder?.setNegativeButton(android.R.string.cancel){ dialog,wich ->
            if (::onCancelClickListener.isInitialized)
                onCancelClickListener()
        }
        return builder!!.create()
    }

    fun setOnAcceptClickListener(listener: () -> Unit){
        this.onAcceptClickListener = listener
    }

    fun setOnCancelClickListener(listener: () -> Unit){
        this.onCancelClickListener = listener
    }
}