package com.example.dbapp.ui.client

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import kotlinx.coroutines.flow.StateFlow
import com.example.dbapp.model.entity.Customer
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel


class ContactViewModel(
    private val messageService: MessageServiceViewModel
) : ViewModel() {

    private val contactService = MyApp.getInstance().customerService

    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers

    private val _customer = MutableStateFlow<Customer>(Customer())
    val customer: StateFlow<Customer> = _customer

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadCustomers()
    }

    fun getCustomerById(customerId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedCustomer = withContext(Dispatchers.IO) {
                contactService.getCustomerById(customerId)
            }
            _customer.value = fetchedCustomer ?: Customer()
            _isLoading.value = false
        }
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            val customersFetched = withContext(Dispatchers.IO) {
                contactService.allCustomer
            }
            _customers.value = customersFetched
        }
    }

    fun addCustomer(customer: Customer) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactService.addCustomer(customer)
            }
            loadCustomers()
        }
        messageService.showMessage("El cliente ha sido creado correctamente", MessageType.SUCCESS)
    }

    fun updateCustomer(id: Long, customer: Customer) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactService.updateCustomer(id, customer)
            }
            loadCustomers()
        }
        messageService.showMessage("El cliente ha sido actualizado correctamente", MessageType.SUCCESS)
    }

    fun deleteCustomer(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactService.removeCustomer(id)
            }
            loadCustomers()
            messageService.showMessage("El cliente ha sido eliminado correctamente")
        }
    }

}

