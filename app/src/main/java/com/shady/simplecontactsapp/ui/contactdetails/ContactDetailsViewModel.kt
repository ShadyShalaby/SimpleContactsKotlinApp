package com.shady.simplecontactsapp.ui.contactdetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shady.simplecontactsapp.model.Contact
import com.shady.simplecontactsapp.repository.ContactsRepository


class ContactDetailsViewModel @ViewModelInject constructor(
    private val contactsRepository: ContactsRepository,
    @Assisted private val state: SavedStateHandle
) :
    ViewModel() {

    val contactLiveData = state.getLiveData<Contact>("contact")

}