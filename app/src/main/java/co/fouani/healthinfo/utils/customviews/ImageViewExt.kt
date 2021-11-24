package co.fouani.healthinfo.utils.customviews

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import co.fouani.healthinfo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageViewExt(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {
    private var url: String? = ""

    init {
        val attributeSet: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ImageViewExt, 0, 0)
        url = attributeSet.getString(R.styleable.ImageViewExt_url)
        if (url != null)
            setUrl(url)
        else setImageDrawable(ContextCompat.getDrawable(context, R.drawable.no_image))
    }

    fun setUrl(u: String?) {
        url = u
        Glide
            .with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}