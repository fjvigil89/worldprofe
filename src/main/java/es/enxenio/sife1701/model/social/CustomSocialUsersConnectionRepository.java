package es.enxenio.sife1701.model.social;

import org.springframework.social.connect.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomSocialUsersConnectionRepository implements UsersConnectionRepository {

    private SocialUserConnectionRepository socialUserConnectionRepository;

    private ConnectionFactoryLocator connectionFactoryLocator;

    public CustomSocialUsersConnectionRepository(SocialUserConnectionRepository socialUserConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator) {
        this.socialUserConnectionRepository = socialUserConnectionRepository;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<UsuarioSocial> usuariosSocial =
            socialUserConnectionRepository.findAllByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
        return usuariosSocial.stream()
            .map(UsuarioSocial::getUserId)
            .collect(Collectors.toList());
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        List<UsuarioSocial> usuariosSocial =
            socialUserConnectionRepository.findAllByProviderIdAndProviderUserIdIn(providerId, providerUserIds);
        return usuariosSocial.stream()
            .map(UsuarioSocial::getUserId)
            .collect(Collectors.toSet());
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new CustomSocialConnectionRepository(userId, socialUserConnectionRepository, connectionFactoryLocator);
    }

}
