package io.tokend.certificates.data.datasource.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface CertificateApi {
    @GET("https://script.googleusercontent.com/macros/echo?user_content_key=Cocd0goMZy80t9vCrBlkDhQ-eRx5LYH6YK057rFBgyEaGobDGz1MjQJt-z8zzU5HXkxt8V_D5icURWMPaKW1pa9TbXufEyfKm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnMExAVbLgu_OvrTyJbFzp7VnQMXP5N6ZBiDL1daUbxC5bLyUtlW9p6DC7wiA2qVziWAiUva1N7ZbOOfA_3npVNjd8iUjuBvOBtz9Jw9Md8uu&lib=MqxSBsS-_kO-yCSPTYdB8Zta9yFOYf39W")
    fun getCertificates() : Single<ResponseBody>
}