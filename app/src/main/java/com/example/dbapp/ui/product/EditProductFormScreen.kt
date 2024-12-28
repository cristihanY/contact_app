package com.example.dbapp.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.model.entity.Category
import com.example.dbapp.model.entity.Product
import com.example.dbapp.ui.client.component.TitleForm
import com.example.dbapp.ui.client.component.TopBarDinamicComponent
import com.example.dbapp.ui.product.component.ProductForm
import com.example.dbapp.ui.uiutil.DeleteConfirmationDialog
import com.example.dbapp.viewmodel.MessageServiceViewModel
import java.util.Date


@Composable
fun EditProductFormScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    productId: Long,
    viewModel: ProductViewModel
) {
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    val product by viewModel.product.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize())
    } else {
        var name by remember { mutableStateOf(product?.name ?: "") }
        var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
        var quantity by remember { mutableStateOf(product?.quantity?.toString() ?: "") }
        var img by remember { mutableStateOf(product?.img ?: "") }
        var barcode by remember { mutableStateOf(product?.barcode ?: "") }
        var costPrice by remember { mutableStateOf(product?.costPrice?.toString() ?: "") }
        var description by remember { mutableStateOf(product?.description ?: "") }
        var selectedCategoryId by remember { mutableLongStateOf(product?.categoryId ?: 1L) }
        var showError by remember { mutableStateOf(false) }

        val categories = listOf(
            Category(1, "Electrónica"),
            Category(2, "Ropa"),
            Category(3, "Comida")
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val lazyListState = rememberLazyListState()
            val isPrimaryTitleVisible = remember {
                derivedStateOf { lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset < 100 }
            }

            val titlePrimary = "Editar producto"
            val titleSecondary = "Información del producto"


            TopBarDinamicComponent(
                navController = navController,
                showTitle = (isPrimaryTitleVisible),
                title =titlePrimary,
                onSave = {
                    if (name.isNotEmpty() && price.isNotEmpty() && costPrice.isNotEmpty()) {
                        val updatedProduct = Product(
                            selectedCategoryId,
                            name,
                            price.toDoubleOrNull() ?: 0.0,
                            quantity.toIntOrNull() ?: 0,
                            img,
                            barcode,
                            costPrice.toDoubleOrNull() ?: 0.0,
                            description,
                            product?.createdAt ?: Date()
                        )
                        viewModel.updateProduct(productId, updatedProduct)
                        navController.navigateUp()
                    } else {
                        showError = true
                    }
                }
            )

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    TitleForm(
                        titlePrimary = titlePrimary,
                        titleSecondary = titleSecondary
                    )
                }

                item {
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
                        isCreateForm = false,
                        categories = categories,
                        selectedCategoryId = selectedCategoryId,
                        onCategorySelected = { selectedCategoryId = it },
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp)
                    )
                }

                item {
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Eliminar producto", color = Color.White)
                    }
                }

                if (showDeleteDialog) {
                    item {
                        DeleteConfirmationDialog(
                            onConfirm = {
                                viewModel.deleteProduct(productId)
                                showDeleteDialog = false
                                navController.navigateUp()
                            },
                            onDismiss = { showDeleteDialog = false }
                        )
                    }
                }

            }

            CustomBottomSheet(
                initialHeight = 160.dp,
                expandedHeight = 350.dp,
                onPrimaryClick = { println("Agregar 1 presionado") },
                onSecondaryClick = { println("Agregar 1 presionado") },
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )


            // todo Aqui un componente que funcione como buton bar y que tenga como tamaño alto 60px y los bordes redondeados como Botosheet
        }

    }
}

@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    initialHeight: Dp = 160.dp,
    expandedHeight: Dp = 300.dp,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val minHeightPx = with(LocalDensity.current) { initialHeight.toPx() }
    val maxHeightPx = with(LocalDensity.current) { expandedHeight.toPx() }
    var sheetHeight by remember { mutableFloatStateOf(minHeightPx) }

    val draggableState = rememberDraggableState { delta ->
        sheetHeight = (sheetHeight - delta).coerceIn(minHeightPx, maxHeightPx)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                ambientColor = MaterialTheme.colorScheme.onBackground,  // Color oscuro para la sombra
                spotColor = MaterialTheme.colorScheme.onBackground      // Sombra más oscura en la parte superior
            )
            .height(with(LocalDensity.current) { sheetHeight.toDp() })
            .background(
                color = MaterialTheme.colorScheme.background, // Cambio a color del tema
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            )
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = {
                    sheetHeight = if (sheetHeight > (minHeightPx + maxHeightPx) / 2) {
                        maxHeightPx
                    } else {
                        minHeightPx
                    }
                }
            )
            .padding(12.dp) // Padding de 12px a la caja principal
    ) {
        // Barra superior pegada a la parte superior del Box
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.onBackground) // Barra toma color del tema
                .align(Alignment.TopCenter) // Alineación pegada a la parte superior
                .padding(top = 12.dp) // Un pequeño margen superior
        )

        // Contenedor de opciones horizontales en la parte superior
        // Contenedor de opciones horizontales en la parte superior
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter) // Alinea la columna a la parte superior
                .padding(top = 24.dp)
                .border(
                    width = 1.dp, // Grosor del borde
                    color = Color.Gray, // Color del borde
                    shape = RoundedCornerShape(8.dp) // Asegura que el borde también sea redondeado
                ) // Un pequeño margen desde la parte superior
        ) {
            // Primer Row: Icono + "Agregar al carrito"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Usamos el ícono de "más"
                    contentDescription = "Agregar al carrito",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(25.dp) // Tamaño más grande para el ícono
                )
                Text(
                    text = "Agregar al carrito",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize =20.sp) // Texto más grande
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            // Segundo Row: Icono de carrito + "Ir al carrito (2 articulos)"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart, // Ícono de carrito
                    contentDescription = "Ir al carrito",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(25.dp) // Tamaño más grande para el ícono
                )
                Text(
                    text = "Ir al carrito (2 artículos)",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp) // Texto más grande
                )
            }
        }

    }
}




@Composable
fun CustomButtonBar(
    modifier: Modifier = Modifier,
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onPrimaryClick,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(text = "Agregar 1")
            }
            Button(
                onClick = onSecondaryClick
            ) {
                Text(text = "Agregar 1")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { showBottomSheet = true }
        ) {
            Text("Display partial bottom sheet")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false }
            ) {
                Text(
                    "Swipe up to open sheet. Swipe down to dismiss.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UpdateProductFormScreenPreview() {
    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    val messageViewModel = MessageServiceViewModel()
    val viewModel = ProductViewModel(messageViewModel)

    EditProductFormScreen(
        navController = navController,
        innerPadding = innerPadding,
        productId = 1,
        viewModel = viewModel
     )

}