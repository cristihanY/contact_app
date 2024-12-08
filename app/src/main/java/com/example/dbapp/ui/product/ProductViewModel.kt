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

    // Instancia de ProductService
    private val productService = MyApp.getInstance().productService

    // StateFlow para la lista de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // StateFlow para un solo producto
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadProducts()
    }

    // Cargar todos los productos
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

    // Obtener un producto por su ID
    fun getProductById(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedProduct = withContext(Dispatchers.IO) {
                productService.getById(productId)
            }
            _product.value = fetchedProduct
            _isLoading.value = false
        }
    }

    // Agregar un producto nuevo
    fun addProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.insert(product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido creado correctamente", MessageType.SUCCESS)
        }
    }

    // Actualizar un producto existente
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.update(product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido actualizado correctamente", MessageType.SUCCESS)
        }
    }

    // Eliminar un producto
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productService.delete(product)
            }
            loadProducts()
            messageService.showMessage("El producto ha sido eliminado correctamente", MessageType.SUCCESS)
        }
    }
}
