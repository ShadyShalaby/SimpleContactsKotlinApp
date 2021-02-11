package com.shady.simplecontactsapp.ui.addcontact

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shady.simplecontactsapp.model.Contact
import com.shady.simplecontactsapp.repository.ContactsRepository
import kotlinx.coroutines.launch

class AddContactViewModel @ViewModelInject constructor(private val contactsRepository: ContactsRepository) :
    ViewModel() {

    val addState = MutableLiveData<OperationState>(OperationState.LOADING)

    fun addContact(name: String, phone: String) {
        viewModelScope.launch {
            val success = contactsRepository.addContact(Contact(name, phone))
            addState.value =
                if (success)
                    OperationState.SUCCESS
                else OperationState.ERROR
        }
    }

    enum class OperationState {
        LOADING, SUCCESS, ERROR
    }
}