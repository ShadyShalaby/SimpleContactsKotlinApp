package com.shady.simplecontactsapp.ui.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.shady.simplecontactsapp.repository.ContactsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class ContactsViewModel @ViewModelInject constructor(private val contactsRepository: ContactsRepository) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")
    val contactsFlow = searchQuery.flatMapLatest {
        contactsRepository.getContacts(it)
    }
    val contacts = contactsFlow.asLiveData()

}