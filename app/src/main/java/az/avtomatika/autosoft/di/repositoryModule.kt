package az.needforspeak.di

import az.avtomatika.autosoft.repository.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
}