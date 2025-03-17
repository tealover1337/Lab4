package kfd.cherkasov.lab4.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String,
    val accessTokenExpirationDate: Long,
    val refreshTokenExpirationDate: Long,
)

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtPropertiesBean