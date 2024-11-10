package com.example.dbapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.entity.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactViewModel : ViewModel() {

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
    }

    fun updateCustomer(id: Long, customer: Customer) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactService.updateCustomer(id, customer)
            }
            loadCustomers()
        }
    }

    fun deleteCustomer(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                contactService.removeCustomer(id)
            }
            loadCustomers()
        }
    }

}

