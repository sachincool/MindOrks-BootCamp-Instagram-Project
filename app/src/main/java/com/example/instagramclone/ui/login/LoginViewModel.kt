package com.example.instagramclone.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.instagramclone.common.Resource
import com.example.instagramclone.common.Status
import com.example.instagramclone.utils.Validation
import com.example.instagramclone.utils.Validator

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val validationsLiveData: MutableLiveData<List<Validation>> by lazy {
        MutableLiveData<List<Validation>>()
    }

    val emailValidationLiveData: LiveData<Resource<Any>> by lazy {
        transformValidationLiveData(Validation.Field.EMAIL)
    }

    val passwordValidationLiveData: LiveData<Resource<Any>> by lazy {
        transformValidationLiveData(Validation.Field.PASSWORD)
    }

    val navigationLiveData: LiveData<Boolean> by lazy {
        Transformations.map(validationsLiveData) { validations ->
            validations.isNotEmpty()
                    && validations.filter { it.resource.status == Status.SUCCESS }.size == validations.size
        }
    }

    private fun transformValidationLiveData(field: Validation.Field) =
        Transformations.map(validationsLiveData) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource(Status.UNKNOWN)
        }

    fun onLoginClick(email: String, password: String) {
        validationsLiveData.value = Validator.validateLoginFields(email, password)
    }

}