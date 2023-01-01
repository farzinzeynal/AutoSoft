package az.needforspeak.di

import az.avtomatika.autosoft.repository.FormRepository
import az.avtomatika.autosoft.repository.LoginRepository
import az.avtomatika.autosoft.repository.SplashRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { SplashRepository(get()) }
    single { FormRepository(get()) }
}