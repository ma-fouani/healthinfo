package co.fouani.healthinfo.ui.widgets

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.GrandProgress
import co.fouani.healthinfo.databinding.GrandProgressWidgetBinding
import java.text.SimpleDateFormat
import java.util.*

class GrandProgressWidget(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    var binding: GrandProgressWidgetBinding

    fun setWidget(progress: GrandProgress): GrandProgressWidget {
        binding.grandProgress = progress
        return this
    }

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(
                layoutInflater, R.layout.grand_progress_widget,
                parent as ViewGroup?, false
            )
        binding.share.setOnClickListener {
            val bitmap= loadBitmapFromView(binding.root)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imagePath = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "img_$timeStamp",
                null
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath))
            }
            context.startActivity(Intent.createChooser(shareIntent, "Check out my progress on this awesome app!"))

        }
        this.addView(binding.root)
    }

    fun loadBitmapFromView(v: View): Bitmap? {
        val b = Bitmap.createBitmap(
            v.width, v.height, Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        v.layout(0, 0, v.width, v.height)
        v.draw(c)
        return b
    }
}