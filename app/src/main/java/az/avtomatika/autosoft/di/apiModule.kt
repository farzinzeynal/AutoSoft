package az.avtomatika.autosoft.di

import az.avtomatika.autosoft.service.LoginService
import az.avtomatika.autosoft.service.SplashService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(LoginService::class.java) }
    single(createdAtStart = false) { get<Retrofit>().create(SplashService::class.java) }
}
