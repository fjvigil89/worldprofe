package es.enxenio.sife1701.model.social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SocialUserConnectionRepository extends JpaRepository<UsuarioSocial, Long> {

    List<UsuarioSocial> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<UsuarioSocial> findAllByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

    List<UsuarioSocial> findAllByUserIdOrderByProviderIdAscRankAsc(String userId);

    List<UsuarioSocial> findAllByUserIdAndProviderIdOrderByRankAsc(String userId, String providerId);

    List<UsuarioSocial> findAllByUserIdAndProviderIdAndProviderUserIdIn(String userId, String providerId, List<String> provideUserId);

    UsuarioSocial findOneByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    void deleteByUserIdAndProviderId(String userId, String providerId);

    void deleteByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
}
