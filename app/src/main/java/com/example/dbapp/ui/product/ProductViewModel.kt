package com.example.dbapp.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbapp.core.init.MyApp
import com.example.dbapp.model.entity.Product
import com.example.dbapp.model.enums.MessageType
import com.example.dbapp.viewmodel.MessageServiceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(
    private val messageService: MessageServiceViewModel
) : ViewModel() {

    private val productService = MyApp.getInstance().productService

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val productsFetched = withContext(Dispatchers.IO) {
                productService.all
            }
            _products.value = productsFetched
            _isLoading.value = false
        }
    }

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedProduct = withContext(Dispatchers.IO) {
                productService.getById(productId)
            }
            _product.value = fetchedProduct
            _isLoading.value = false
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.insert(product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido creado correctamente", MessageType.SUCCESS)
        }
    }

    fun updateProduct(productId: Long, product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.updateProduct(productId, product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido actualizado correctamente", MessageType.SUCCESS)
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.removeProduct(productId)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido eliminado correctamente", MessageType.SUCCESS)
        }
    }
}
