package com.shady.simplecontactsapp.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shady.simplecontactsapp.model.Contact
import com.shady.simplecontactsapp.utils.getDataFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val FIRESTORE_CONTACTS_COLLECTION = "contacts"

class ContactsRepository @Inject constructor() {

    fun getContacts(searchQuery: String): Flow<List<Contact>> {
        return Firebase.firestore
            .collection(FIRESTORE_CONTACTS_COLLECTION)
            .getDataFlow { querySnapshot ->
                (querySnapshot?.documents?.map {
                    it.toObject(Contact::class.java)!!
                } ?: listOf())
                    .filter { contact ->
                        contact.name.toLowerCase().contains(searchQuery.toLowerCase())
                    }
            }
    }


    suspend fun addContact(contact: Contact): Boolean {
        try {
            Firebase.firestore
                .collection(FIRESTORE_CONTACTS_COLLECTION)
                .document(contact.phone)
                .set(contact)
                .await()
            return true
        } catch (e: Exception) {
            return false
        }

    }

}