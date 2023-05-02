import android.content.Context
import dagger.Module
import dagger.Provides
import io.tokend.certificates.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun appContext(): Context = app.applicationContext
}