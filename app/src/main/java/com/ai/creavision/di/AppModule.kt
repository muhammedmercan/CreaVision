package com.ai.creavision.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ai.creavision.R
import com.ai.creavision.data.local.Dao
import com.ai.creavision.data.local.Database
import com.ai.creavision.data.remote.Api
import com.ai.creavision.data.repository.Repository
import com.ai.creavision.domain.repository.RepositoryInterface
import com.ai.creavision.utils.Constants.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, Database::class.java, "FavoriteDB").build()

    @Singleton
    @Provides
    fun injectDao(
        database: Database
    ) = database.dao()



    private var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun injectRetrofitApi(): Api {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(Api::class.java)

    }

    @Singleton
    @Provides
    fun injectNormalRepo(api: Api, dao: Dao) = Repository(api, dao) as RepositoryInterface

    /*
    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

     */

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("mainPreference", Context.MODE_PRIVATE)
    }
}