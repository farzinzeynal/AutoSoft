package az.avtomatika.autosoft.di

import az.avtomatika.autosoft.ui.main.addform.FormViewModel
import az.avtomatika.autosoft.ui.splash.SplashVeiwModel
import az.avtomatika.autosoft.ui.unregister.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { LoginViewModel(get()) }
    viewModel { SplashVeiwModel(get()) }
    viewModel { FormViewModel(get()) }
}