
package app.easyinvoice.ui.auth.home.invoices

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import app.easyinvoice.R
import app.easyinvoice.data.Resource
import app.easyinvoice.data.models.Business
import app.easyinvoice.ui.AppScreen
import app.easyinvoice.ui.auth.home.mybusinesses.MyBusiness
import app.easyinvoice.ui.commons.EmptyScreen
import app.easyinvoice.ui.commons.FullScreenProgressbar
import app.easyinvoice.ui.faker.FakeViewModelProvider
import app.easyinvoice.ui.theme.AppTheme
import app.easyinvoice.ui.theme.spacing
import app.easyinvoice.ui.utils.toast

@Composable
fun PickBusinessScreen(viewModel: InvoicesViewModel, navController: NavController) {
    val context = LocalContext.current
    val businesses = viewModel.businesses.collectAsState()

    businesses.value?.let {
        when (it) {
            is Resource.Failure -> {
                context.toast(it.exception.message!!)
            }
            Resource.Loading -> {
                FullScreenProgressbar()
            }
            is Resource.Success -> {
                PickBusiness(viewModel, it.result, navController)
            }
        }
    }
}

@Composable
fun PickBusiness(viewModel: InvoicesViewModel, businesses: List<Business>, navController: NavController) {
    val spacing = MaterialTheme.spacing
    if (businesses.isEmpty()) {
        EmptyScreen(title = stringResource(id = R.string.empty_business)) { }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.pick_a_business),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(spacing.medium)
            )

            LazyColumn {
                items(businesses) { item ->
                    MyBusiness(
                        business = item,
                        onClick = {
                            viewModel.setBusiness(item)
                            navController.navigate(AppScreen.Invoices.ManageInvoice.PickCustomer.route)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PickBusinessPreviewLight() {
    AppTheme {
        PickBusiness(
            FakeViewModelProvider.provideInvoicesViewModel(),
            listOf(
                Business(
                    name = "Simplified Coding",
                    address = "Bangalore, India",
                    email = "probelalkhan@gmail.com",
                    phone = "8340154535"
                ),
                Business(
                    name = "Simplified Coding",
                    address = "Bangalore, India",
                    email = "probelalkhan@gmail.com",
                    phone = "8340154535"
                )
            ),
            rememberNavController()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PickBusinessPreviewDark() {
    AppTheme {
        PickBusiness(
            FakeViewModelProvider.provideInvoicesViewModel(),
            listOf(
                Business(
                    name = "Simplified Coding",
                    address = "Bangalore, India",
                    email = "probelalkhan@gmail.com",
                    phone = "8340154535"
                ),
                Business(
                    name = "Simplified Coding",
                    address = "Bangalore, India",
                    email = "probelalkhan@gmail.com",
                    phone = "8340154535"
                )
            ),
            rememberNavController()
        )
    }
}