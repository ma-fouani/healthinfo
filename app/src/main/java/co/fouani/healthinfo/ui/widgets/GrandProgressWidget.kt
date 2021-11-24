package co.fouani.healthinfo.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import co.fouani.healthinfo.R
import co.fouani.healthinfo.data.models.GrandProgress
import co.fouani.healthinfo.data.models.Post
import co.fouani.healthinfo.databinding.GrandProgressWidgetBinding
import co.fouani.healthinfo.databinding.WidgetBinding
import co.fouani.healthinfo.utils.Utility.exists
import co.fouani.healthinfo.utils.Utility.getSection

class GrandProgressWidget(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    var binding: GrandProgressWidgetBinding

    fun setWidget(progress: GrandProgress ): GrandProgressWidget {
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
        this.addView(binding.root)
    }

}