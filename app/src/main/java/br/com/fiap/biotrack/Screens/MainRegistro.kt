
package br.com.fiap.biotrack.Screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import br.com.fiap.biotrack.R
import coil.compose.rememberAsyncImagePainter

// Modelo de dados atualizado para incluir imagem
data class Avistamento(
    val especie: String,
    val localizacao: String,
    val descricao: String,
    val imagemUri: Uri? = null
)

@Composable
fun RegistroAvistamentoScreen(navController: NavController) {
    val context = LocalContext.current

    var especie by remember { mutableStateOf(TextFieldValue("")) }
    var localizacao by remember { mutableStateOf(TextFieldValue("")) }
    var descricao by remember { mutableStateOf(TextFieldValue("")) }
    var imagemUri by remember { mutableStateOf<Uri?>(null) }

    var avistamentos by remember { mutableStateOf(listOf<Avistamento>()) }

    // função para anexar a imagem da galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagemUri = uri
    }

    // função para  abrir a camera e tirar a foto
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveImageToGallery(context, it)
            imagemUri = uri
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.registro_avistamento),
            color = Color(0xFF4CAF50),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = especie,
            onValueChange = { especie = it },
            label = { Text(stringResource(id = R.string.nome_especie)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = localizacao,
            onValueChange = { localizacao = it },
            label = { Text(stringResource(id = R.string.localizacao)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text(stringResource(id = R.string.descricao)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // função para exibir imagem selecionada
        imagemUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Imagem do Avistamento",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { galleryLauncher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                onClick = { galleryLauncher.launch("image/*") }) {
                Text("Escolher da Galeria")
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                onClick = { cameraLauncher.launch(null) }) {
                Text("Abrir Câmera")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            onClick = {
                if (especie.text.isNotBlank() && localizacao.text.isNotBlank()) {
                    avistamentos = avistamentos + Avistamento(especie.text, localizacao.text, descricao.text, imagemUri)

                    especie = TextFieldValue("")
                    localizacao = TextFieldValue("")
                    descricao = TextFieldValue("")
                    imagemUri = null

                    Toast.makeText(context, "Avistamento registrado!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.registro))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(avistamentos) { avistamento ->
                AvistamentoCard(avistamento)
            }
        }
    }
}


// Exibe o card do avistamento com a imagem na lazyColumn conforme cap 12 "APP World" da Fase 1
@Composable
fun AvistamentoCard(avistamento: Avistamento) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Espécie: ${avistamento.especie}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Localização: ${avistamento.localizacao}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Descrição: ${avistamento.descricao}", style = MaterialTheme.typography.bodySmall)

            avistamento.imagemUri?.let { uri ->
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagem do Avistamento",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

// Salvar imagem no armazenamento interno
fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
    val uri = MediaStore.Images.Media.insertImage(
        context.contentResolver, bitmap, "foto_avistamento", "Foto capturada"
    )
    return uri?.let { Uri.parse(it) }
}
