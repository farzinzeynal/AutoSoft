package az.avtomatika.autosoft.di

import az.avtomatika.autosoft.ui.unregister.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { LoginViewModel(get()) }
}