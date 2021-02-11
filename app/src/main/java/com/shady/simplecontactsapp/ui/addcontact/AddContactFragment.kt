package com.shady.simplecontactsapp.ui.addcontact

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.shady.simplecontactsapp.R
import com.shady.simplecontactsapp.databinding.AddContactFragmentBinding
import com.shady.simplecontactsapp.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment() {

    companion object {
        fun newInstance() = AddContactFragment()
    }

    private val viewModel: AddContactViewModel by viewModels()
    private var _binding: AddContactFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddContactFragmentBinding.inflate(inflater, container, false)

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.addState.observe(viewLifecycleOwner) {
            if (it == AddContactViewModel.OperationState.SUCCESS) {
                Snackbar.make(requireView(), "Added Successfully", Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else if (it == AddContactViewModel.OperationState.ERROR)
                Snackbar.make(requireView(), "Error Occurred", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_contact_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                if (binding.etName.text.isNullOrEmpty() || binding.etPhone.text.isNullOrEmpty())
                    Toast.makeText(requireContext(), "Please fill all the data", Toast.LENGTH_SHORT)
                        .show()
                else {
                    hideKeyboard()

                    viewModel.addContact(
                        binding.etName.text.toString().trim(),
                        binding.etPhone.text.toString().trim()
                    )
                }
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}