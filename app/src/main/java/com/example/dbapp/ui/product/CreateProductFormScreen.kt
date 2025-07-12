package com.example.dbapp.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Date
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Category
import com.example.dbapp.model.entity.Product
import com.example.dbapp.ui.client.component.TitleForm
import com.example.dbapp.ui.client.component.TopBarClientComponent
import com.example.dbapp.ui.product.component.ProductForm
import java.math.BigDecimal

@Composable
fun CreateProductFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    onSave: (Product) -> Unit
) {
    val categories = listOf(
        Category(1, "Electrónica"),
        Category(2, "Ropa"),
        Category(3, "Comida")
    )

    var selectedCategoryId by remember { mutableLongStateOf(1L) }

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var img by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }
    var costPrice by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TopBarClientComponent(
            navController = navController,
            onSave = {
                if (name.isNotEmpty() && price.isNotEmpty() && costPrice.isNotEmpty()) {
                    val newProduct = Product(
                        selectedCategoryId,
                        name,
                        price.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                        quantity.toIntOrNull() ?: 0,
                        img,
                        barcode,
                        costPrice.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                        description,
                        Date()
                    )
                    onSave(newProduct)

                    name = ""
                    price = ""
                    quantity = ""
                    img = ""
                    barcode = ""
                    costPrice = ""
                    description = ""
                    showError = false

                    navController.navigateUp()
                } else {
                    showError = true
                }
            },
            //modifier = Modifier.weight(0.1f)
        )

        TitleForm(titlePrimary = "Agregar producto", titleSecondary = "Información del producto")

        ProductForm(
            name = name,
            onNameChange = { name = it },
            price = price,
            onPriceChange = { price = it },
            quantity = quantity,
            onQuantityChange = { quantity = it },
            img = img,
            onImgChange = { img = it },
            barcode = barcode,
            onBarcodeChange = { barcode = it },
            costPrice = costPrice,
            onCostPriceChange = { costPrice = it },
            description = description,
            onDescriptionChange = { description = it },
            showError = showError,
            isCreateForm = true,
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { selectedCategoryId = it },
            modifier = Modifier.weight(0.9f)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProductFormScreenPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    CreateProductFormScreen(navController = navController, innerPadding = innerPadding) { contact ->

    }
}
