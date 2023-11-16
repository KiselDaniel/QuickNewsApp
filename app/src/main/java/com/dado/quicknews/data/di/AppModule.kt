package com.dado.quicknews.data.di

import android.content.Context
import com.dado.quicknews.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dado.quicknews.data.AppConstants
import com.dado.quicknews.data.api.NetworkAPIServiceI
import com.dado.quicknews.data.database.ArticleDaoI
import com.dado.quicknews.data.datasource.NewsDataSource
import com.dado.quicknews.data.datasource.NewsDataSourceI
import com.dado.quicknews.data.datasource.NewsLocalDataSource
import com.dado.quicknews.data.datasource.NewsLocalDataSourceI
import com.dado.quicknews.ui.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private const val READ_TIMEOUT = 60L
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.APP_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkAPIService(retrofit: Retrofit): NetworkAPIServiceI {
        return retrofit.create(NetworkAPIServiceI::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsDataSource(apiService: NetworkAPIServiceI): NewsDataSourceI {
        return NewsDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providesNewsLocalDataSource(articleDao: ArticleDaoI): NewsLocalDataSourceI {
        return NewsLocalDataSource(articleDao)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(context: Context, newsDataSource: NewsDataSourceI, newsLocalDataSource: NewsLocalDataSourceI): NewsRepository {
        return NewsRepository(context, newsDataSource, newsLocalDataSource)
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}