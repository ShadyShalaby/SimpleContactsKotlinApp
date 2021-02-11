package com.shady.simplecontactsapp.ui.contactdetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shady.simplecontactsapp.databinding.ContactDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

const val REQUEST_CODE_PERMISSION_CALL_PHONE = 123

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactDetailsFragment()
    }

    private val viewModel: ContactDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ContactDetailsFragmentBinding.inflate(inflater, container, false)

        setupObservers(binding)
        setupViewListeners(binding)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }


    private fun setupViewListeners(binding: ContactDetailsFragmentBinding) {

        binding.cvContactsDetails.setOnClickListener {
            askForPhoneCallPermission()
        }
    }

    private fun askForPhoneCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CODE_PERMISSION_CALL_PHONE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION_CALL_PHONE) {
            val granted = ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED

            if (granted) {
                doCall()
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    Toast.makeText(
                        requireContext(),
                        "please, go to the settings to able to enable the needed permission",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    askForPhoneCallPermission()
                }
            }

        }
    }


    private fun doCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${viewModel.contactLiveData.value?.phone}")
        startActivity(intent)
    }

    private fun setupObservers(binding: ContactDetailsFragmentBinding) {
        viewModel.contactLiveData.observe(viewLifecycleOwner) { contact ->
            binding.apply {
                tvName.text = contact.name
                tvPhone.text = contact.phone
            }

        }
    }

}