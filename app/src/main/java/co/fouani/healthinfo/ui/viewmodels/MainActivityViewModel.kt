package co.fouani.healthinfo.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var title = MutableLiveData<String>()

    fun updateTitle(title: String) {
        this.title.value = title
    }

}