package com.shady.simplecontactsapp.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shady.simplecontactsapp.base.BaseViewHolder
import com.shady.simplecontactsapp.base.OnItemClickListener
import com.shady.simplecontactsapp.databinding.ListItemContactBinding
import com.shady.simplecontactsapp.model.Contact
import java.util.*

class ContactsAdapter(val itemClickListener: OnItemClickListener<Contact>) :
    ListAdapter<Contact, ContactsAdapter.ContactViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ListItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // item
        val contact = getItem(position)

        // bind data
        holder.bind(contact)

        // click listener
        holder.itemView.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClicked(contact)
        })
    }

    inner class ContactViewHolder(val binding: ListItemContactBinding) :
        BaseViewHolder<Contact>(binding.root) {

        override fun bind(obj: Contact) {
            binding.tvContactName.text = obj.name
            binding.tvContactPhone.text = obj.phone
            if (obj.name.isNotEmpty())
                binding.tvFirstChar.text =
                    obj.name.capitalize(Locale.getDefault())[0].toString()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.phone == newItem.phone
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.name == newItem.name && oldItem.phone == newItem.phone
        }

    }

}


