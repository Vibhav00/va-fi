package com.example.va_fi.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun providePokemonRepository(
//        api: PokeApi
//    ) = PokemonRepository(api)
//
//    @Singleton @Provides
//    fun providePokeApi(): PokeApi {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(PokeApi::class.java)
//    }
}