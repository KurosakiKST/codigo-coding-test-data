package com.ryan.codigo1.presentation.auth.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryan.codigo1.R
import com.ryan.codigo1.presentation.auth.viewmodel.AuthEvent
import com.ryan.codigo1.presentation.auth.viewmodel.AuthViewModel
import com.ryan.codigo1.presentation.common.components.PasswordTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var selectedGender by remember { mutableStateOf(state.gender) }

    LaunchedEffect(state) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(state.error!!)
            viewModel.onEvent(AuthEvent.ErrorShown)
        }

        if (state.isSuccess) {
            onRegisterSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Almost there!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Complete the form below to create your Ready To Travel account.",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "*Mandatory",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.guitar),
                        contentDescription = "Guitar Player",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = { viewModel.onEvent(AuthEvent.FirstNameChanged(it)) },
                    label = { Text("First Name *") },
                    isError = state.firstNameError != null,
                    supportingText = state.firstNameError?.let { errorText -> { Text(text = errorText) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = { viewModel.onEvent(AuthEvent.LastNameChanged(it)) },
                    label = { Text("Last Name *") },
                    isError = state.lastNameError != null,
                    supportingText = state.lastNameError?.let { errorText -> { Text(text = errorText) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                    label = { Text("Email Address *") },
                    isError = state.emailError != null,
                    supportingText = state.emailError?.let { errorText -> { Text(text = errorText) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.dateOfBirth,
                    onValueChange = { viewModel.onEvent(AuthEvent.DateOfBirthChanged(it)) },
                    label = { Text("Date of Birth *") },
                    placeholder = { Text("DD/MM/YYYY") },
                    isError = state.dateOfBirthError != null,
                    supportingText = state.dateOfBirthError?.let { errorText -> { Text(text = errorText) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.dob),
                            contentDescription = "Calendar",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Gender *",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FilterChip(
                        selected = selectedGender == "Female",
                        onClick = {
                            selectedGender = "Female"
                            viewModel.onEvent(AuthEvent.GenderChanged(selectedGender))
                        },
                        label = { Text("Female") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4AC1A2),
                            selectedLabelColor = Color.White
                        )
                    )

                    FilterChip(
                        selected = selectedGender == "Male",
                        onClick = {
                            selectedGender = "Male"
                            viewModel.onEvent(AuthEvent.GenderChanged(selectedGender))
                        },
                        label = { Text("Male") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4AC1A2),
                            selectedLabelColor = Color.White
                        )
                    )
                }

                if (state.genderError != null) {
                    Text(
                        text = state.genderError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.nationality ?: "",
                    onValueChange = { viewModel.onEvent(AuthEvent.NationalityChanged(it)) },
                    label = { Text("Nationality *") },
                    isError = state.nationalityError != null,
                    supportingText = state.nationalityError?.let { errorText -> { Text(text = errorText) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.countryOfResidence ?: "",
                    onValueChange = { viewModel.onEvent(AuthEvent.CountryOfResidenceChanged(it)) },
                    label = { Text("Country of Residence *") },
                    isError = state.countryOfResidenceError != null,
                    supportingText = state.countryOfResidenceError?.let { errorText -> { Text(text = errorText) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.LightGray,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.countryCode ?: "+65",
                        onValueChange = { viewModel.onEvent(AuthEvent.CountryCodeChanged(it)) },
                        label = { Text("Code") },
                        modifier = Modifier.width(80.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.LightGray,
                            containerColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = { viewModel.onEvent(AuthEvent.PhoneChanged(it)) },
                        label = { Text("Mobile no. (Optional)") },
                        isError = state.phoneError != null,
                        supportingText = state.phoneError?.let { errorText -> { Text(text = errorText) } },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.LightGray,
                            containerColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                    label = "Password *",
                    error = state.passwordError,
                    backgroundColor = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it)) },
                    label = "Confirm Password *",
                    error = state.confirmPasswordError,
                    backgroundColor = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.onEvent(AuthEvent.RegisterClicked) },
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4AC1A2), // Teal color like in the reference
                        disabledContainerColor = Color(0xFFBBDEFB)
                    ),
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
                    Text(
                        text = "Create my account now",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}