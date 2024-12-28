package com.example.dbapp.ui.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dbapp.model.entity.Order
import androidx.navigation.compose.rememberNavController
import com.example.dbapp.ui.client.component.TopBarCartComponent
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.border
import androidx.compose.material.FractionalThreshold
import androidx.compose.runtime.key

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeToDeleted(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }

    val state = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        println("THIS IS $state")
        androidx.compose.material.SwipeToDismiss(
            state = state,
            background = {
                Deleting(swipeDismissState = state)
            },
            dismissContent = {
                content(item)
            },
            directions = setOf(DismissDirection.EndToStart),
            dismissThresholds = {
                FractionalThreshold(0.5f) // Cambia este valor para ajustar el umbral (50% del ancho)
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Deleting(
    modifier: Modifier = Modifier,
    swipeDismissState: DismissState
) {
    val color = if(swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box (modifier = Modifier
        .fillMaxSize()
        .background(color)
        .padding(16.dp),
        contentAlignment = Alignment.CenterEnd) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White)
    }
    
}

@Composable
fun CartScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    onSave: (Order) -> Unit
) {


    val productsh = remember {
        mutableStateListOf(

            Product(name = "Producto 1", price = "5.30", imageUrl = "https://via.placeholder.com/150", notificationCount = 22),
            Product(name = "Producto 2", price = "8.00", imageUrl = "https://via.placeholder.com/150", notificationCount = 1),
            Product(name = "Producto 3", price = "12.50", imageUrl = "https://via.placeholder.com/150", notificationCount = 1),
            Product(name = "Producto 4", price = "5.30", imageUrl = "https://via.placeholder.com/150", notificationCount = 22),
            Product(name = "Producto 5", price = "8.00", imageUrl = "https://via.placeholder.com/150", notificationCount = 1),
            Product(name = "Producto 6", price = "12.50", imageUrl = "https://via.placeholder.com/150", notificationCount = 1),
            Product(name = "Producto 7", price = "5.30", imageUrl = "https://via.placeholder.com/150", notificationCount = 22),
            Product(name = "Producto 8", price = "8.00", imageUrl = "https://via.placeholder.com/150", notificationCount = 1),
            Product(name = "Producto 9", price = "12.50", imageUrl = "https://via.placeholder.com/150", notificationCount = 1)


        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column (
            modifier = Modifier
                .weight(1f)
        ) {
            // TopBar
            TopBarCartComponent(
                navController = navController
            )

            SecondaryTopBar()

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    Row (
                        modifier = Modifier
                            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 12.dp),
                            text = "Agregar cliente")
                    }

                }

                item{
                    Spacer(modifier = Modifier.padding(vertical = 6.dp))
                }


                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            ) // Borde del grupo
                            .padding(12.dp)
                    ) {
                        productsh.forEach { product ->
                            key(product) { // Usamos `key` para cada producto
                                SwipeToDeleted(
                                    item = product,
                                    onDelete = { productsh -= product }
                                ) {
                                    ProductItem(product = product)
                                }
                            }
                        }
                        ResumeCart()
                    }
                }

            }
        }

        ButtonBarCart(modifier = Modifier.weight(0.15f))

    }

}

@Composable
fun ProductItem(product: Product) {

    Column  {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ProductImageBuilder(product = product)

            Spacer(modifier = Modifier.width(16.dp))

            // Nombre del producto
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                ),
                modifier = Modifier.weight(1f)
            )

            // Precio del producto
            Text(
                text = "PEN ${product.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        HorizontalDivider()

    }

}

@Composable
fun ProductImageBuilder(modifier: Modifier = Modifier, product: Product) {
    Box(
        modifier = Modifier.size(64.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .clip(CircleShape)
        ) {
            Text(
                text = "Img",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (product.notificationCount!! > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)  // Mueve el contador a la esquina superior derecha
                    .offset(
                        x = 8.dp,
                        y = (-8).dp
                    )  // Desplaza un poco para sobresalir fuera de la caja
                    .size(28.dp)  // Tamaño del círculo grande
            ) {
                // Círculo blanco como borde
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White, shape = CircleShape)  // Fondo blanco
                )

                // Círculo rojo para el contador
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)  // Espaciado para que no se solapen los bordes
                        .background(Color.Red, shape = CircleShape)   // Borde blanco para el círculo rojo
                ) {
                    Text(
                        text = product.notificationCount.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

    }
}

@Composable
fun SecondaryTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Carrito",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 22.sp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
        IconButton(onClick = { /* Acción del icono trash */ }) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}

data class Product(
    val name: String,
    val price: String,
    val imageUrl: String,
    val notificationCount: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(imageUrl)
        parcel.writeInt(notificationCount ?: 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

@Composable
fun ResumeCart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Subtotal",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "PEN 24.00",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End
            )
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Impuestos",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "PEN 0.00",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.End
            )
        }

    }
}


@Composable
fun ButtonBarCart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                    )
                )
                Text(
                    text = "2 artículos",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold  // Aquí se aplica el estilo en negrita
                    ),
                    color = Color.Gray
                )
            }
            Text(
                text = "PEN 24.00",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold  // Aquí se aplica el estilo en negrita
                ),
                textAlign = TextAlign.End
            )
        }

        Button(
            onClick = { /* Acción para la pantalla de pago */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Pantalla de pago")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {

    val navController = rememberNavController()
    val innerPadding = PaddingValues(16.dp)
    CartScreen(navController = navController, innerPadding = innerPadding) { contact ->

    }
}
