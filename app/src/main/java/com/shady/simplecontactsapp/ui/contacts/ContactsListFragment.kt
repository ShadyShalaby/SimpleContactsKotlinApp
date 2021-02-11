package com.shady.simplecontactsapp.ui.contacts

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shady.simplecontactsapp.base.OnItemClickListener
import com.shady.simplecontactsapp.R
import com.shady.simplecontactsapp.databinding.ContactsListFragmentBinding
import com.shady.simplecontactsapp.model.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = ContactsListFragmentBinding.inflate(inflater, container, false)

        setupContactsList(binding.rvContacts)
        setupListeners(binding)
        setupObservers()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contacts_menu, menu)
        val searchMenuItem = menu.findItem(R.id.menu_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery.value = newText.orEmpty()
                return true
            }

        })

    }

    private fun setupListeners(binding: ContactsListFragmentBinding) {
        // fab click listener
        binding.fabAddContact.setOnClickListener() {
            val action =
                MainFragmentDirections.actionMainFragmentToAddContactFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            contactsAdapter.submitList(it)
        })
    }

    private fun setupContactsList(rvContacts: RecyclerView) {
        contactsAdapter = ContactsAdapter(object : OnItemClickListener<Contact> {
            override fun onItemClicked(t: Contact) {
                val action =
                    MainFragmentDirections.actionContactsListFragmentToContactDetailsFragment(t)

                findNavController().navigate(action)
            }
        })
        rvContacts.layoutManager = LinearLayoutManager(context)
        rvContacts.adapter = contactsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

}
