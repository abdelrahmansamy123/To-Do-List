package com.route.todo.Base

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    var progressDialog: ProgressDialog? = null

    fun showLoadingDialoge() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.show()
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }

    var alertDialog: AlertDialog? = null
    fun showMessage(
        message: String,
        posActionTitle: String? = null,
        posAction: DialogInterface.OnClickListener? = null,
        negActionTitle: String? = null,
        negAction: DialogInterface.OnClickListener? = null,
        cancelable: Boolean = true
    ) {
        var messageDialogeBuilder = AlertDialog.Builder(requireContext())
        messageDialogeBuilder.setMessage(message)

        if (posActionTitle != null) {
            messageDialogeBuilder.setPositiveButton(
                posActionTitle,
                posAction ?: DialogInterface.OnClickListener { dialog, p1 -> dialog?.dismiss() })
        }
        if (negActionTitle != null) {
            messageDialogeBuilder.setNegativeButton(
                negActionTitle,
                negAction ?: DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface?.dismiss()
                })
        }
        messageDialogeBuilder.setCancelable(cancelable)
        alertDialog = messageDialogeBuilder.show()
    }
}