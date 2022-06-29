package homework_recyclerview

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.convertor.R

class DeleteDialogFragment() : DialogFragment(R.layout.dialog_delete_item) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            findViewById<Button>(R.id.del_button).setOnClickListener {
                (parentFragment as? DeleteDialogCallback)?.onDeleteButton()
                dismiss()
            }
            findViewById<Button>(R.id.cancel_button).setOnClickListener {
                dismiss()
            }
        }
    }
}

interface DeleteDialogCallback {
    fun onDeleteButton()
}