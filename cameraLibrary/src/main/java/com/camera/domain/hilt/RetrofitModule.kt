package com.camera.domain.hilt

import android.app.Application
import android.content.Context
import com.camera.data.api.ArchivosApi
import com.camera.data.api.CategoriasApi
import com.camera.data.api.ParametrosApi
import com.camera.data.api.PhotosApi
import com.camera.utils.LogInfo
import com.camera.utils.Utils.getSelectedEnviroment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitPreProduction(context: Context): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                val request = chain.request()
//                    .newBuilder()
//                    .addHeader("Content-Type", "application/json")
//                    .build()
//                chain.proceed(request)
//            }
            //.addInterceptor(logging)
            .build()
        val environment = getSelectedEnviroment(context)
        LogInfo("Using enviroment: $environment")

        return Retrofit.Builder()
            .baseUrl(environment)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePhotosApi(retrofit: Retrofit): PhotosApi {
        return retrofit.create(PhotosApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArchivosApi(retrofit: Retrofit): ArchivosApi {
        return retrofit.create(ArchivosApi::class.java)
    }

    @Singleton
    @Provides
    fun provideParametrosApi(retrofit: Retrofit): ParametrosApi {
        return retrofit.create(ParametrosApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoriasApi(retrofit: Retrofit): CategoriasApi {
        return retrofit.create(CategoriasApi::class.java)
    }

}
