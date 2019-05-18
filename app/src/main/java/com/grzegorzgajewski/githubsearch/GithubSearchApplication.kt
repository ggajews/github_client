package com.grzegorzgajewski.githubsearch

import android.app.Application
import com.grzegorzgajewski.githubsearch.di.remoteDataSourceModule
import com.grzegorzgajewski.githubsearch.di.repositoriesModule
import org.koin.core.context.startKoin

class GithubSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(remoteDataSourceModule, repositoriesModule)
        }
    }
}