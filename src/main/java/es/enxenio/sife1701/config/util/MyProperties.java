package es.enxenio.sife1701.config.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Fichero de propiedades.
 * Las propiedades se configuran en application.yml
 */
@ConfigurationProperties(prefix = "properties", ignoreUnknownFields = false)
public class MyProperties {

    private final Http http = new Http();
    private final Mail mail = new Mail();
    private final Cache cache = new Cache();
    private final Security security = new Security();
    private final Social social = new Social();
    private final Async async = new Async();
    private final Paypal paypal = new Paypal();

    private final Datos datos = new Datos();
    private final Smsapi smsapi = new Smsapi();


    public Http getHttp() {
        return http;
    }

    public Mail getMail() {
        return mail;
    }

    public Cache getCache() {
        return cache;
    }

    public Security getSecurity() {
        return security;
    }

    public Social getSocial() {
        return social;
    }

    public Async getAsync() {
        return async;
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public Datos getDatos() {
        return datos;

    }
    
    public Smsapi getSmsapi() {
    	return smsapi;
    }


    // =====================================================================

    public static class Http {

        private final Http.Cache cache = new Http.Cache();

        public Http.Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = 1461;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Mail {
        private String from = "localhost@localhost";
        private String name = "";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Cache {

        private int timeToLiveSeconds = 3600;

        public int getTimeToLiveSeconds() {
            return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }
    }

    public static class Security {

        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return authentication;
        }

        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }

            public static class Jwt {

                // Campos sobreescritos en el fichero de propiedades application.yml

                private String secret = "c2VjcmV0";
                private long tokenValidityInSeconds = 1800;
                private long tokenValidityInSecondsForRememberMe = 2592000;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }
    }

    public static class Social {

        private String redirectAfterSignIn = "/#/home";
        private String redirectAfterSignUp = "/#/home";

        public String getRedirectAfterSignIn() {
            return redirectAfterSignIn;
        }

        public void setRedirectAfterSignIn(String redirectAfterSignIn) {
            this.redirectAfterSignIn = redirectAfterSignIn;
        }

        public String getRedirectAfterSignUp() {
            return redirectAfterSignUp;
        }

        public void setRedirectAfterSignUp(String redirectAfterSignUp) {
            this.redirectAfterSignUp = redirectAfterSignUp;
        }
    }

    public static class Async {

        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Paypal {
        private String clientID = "idcliente";
        private String clientSecret = "clavesecreta";
        private String environment = "sandbox";

        public String getClientID() {
            return clientID;
        }

        public void setClientID(String clientID) {
            this.clientID = clientID;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }
    }

    public static class Datos {
        private String titular = "Titular";
        private String cif = "";
        private String direccion = "";
        private String poblacion = "";
        private String prefijoSerie = "FACT";
        private String prefijoSerieSimple = "FACTSIMPL";

        public String getTitular() {
            return titular;
        }

        public void setTitular(String titular) {
            this.titular = titular;
        }

        public String getCif() {
            return cif;
        }

        public void setCif(String cif) {
            this.cif = cif;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getPoblacion() {
            return poblacion;
        }

        public void setPoblacion(String poblacion) {
            this.poblacion = poblacion;
        }

        public String getPrefijoSerie() {
            return prefijoSerie;
        }

        public void setPrefijoSerie(String prefijoSerie) {
            this.prefijoSerie = prefijoSerie;
        }

        public String getPrefijoSerieSimple() {
            return prefijoSerieSimple;
        }

        public void setPrefijoSerieSimple(String prefijoSerieSimple) {
            this.prefijoSerieSimple = prefijoSerieSimple;
        }
    }

    
    public static class Smsapi{
        private String token;
        
        private String url;
        
        public String getUrl() {
               return url;
        }
     
        public void setUrl(String url) {
               this.url = url;
        }

     public String getToken() {
          return token;
     }

     public void setToken(String token) {
          this.token = token;
     }


    }


}
