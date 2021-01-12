package es.enxenio.sife1701.model.social;

import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

public class CustomSocialConnectionRepository implements ConnectionRepository {

    private String userId;

    private SocialUserConnectionRepository socialUserConnectionRepository;

    private ConnectionFactoryLocator connectionFactoryLocator;

    public CustomSocialConnectionRepository(String userId, SocialUserConnectionRepository socialUserConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator) {
        this.userId = userId;
        this.socialUserConnectionRepository = socialUserConnectionRepository;
        this.connectionFactoryLocator = connectionFactoryLocator;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<UsuarioSocial> usuariosSocial = socialUserConnectionRepository.findAllByUserIdOrderByProviderIdAscRankAsc(userId);
        List<Connection<?>> connections = socialUserConnectionsToConnections(usuariosSocial);
        MultiValueMap<String, Connection<?>> connectionsByProviderId = new LinkedMultiValueMap<>();
        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connectionsByProviderId.put(registeredProviderId, Collections.emptyList());
        }
        for (Connection<?> connection : connections) {
            String providerId = connection.getKey().getProviderId();
            if (connectionsByProviderId.get(providerId).size() == 0) {
                connectionsByProviderId.put(providerId, new LinkedList<>());
            }
            connectionsByProviderId.add(providerId, connection);
        }
        return connectionsByProviderId;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        List<UsuarioSocial> usuariosSocial = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(userId, providerId);
        return socialUserConnectionsToConnections(usuariosSocial);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIdsByProviderId) {
        if (providerUserIdsByProviderId == null || providerUserIdsByProviderId.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<>();
        for (Map.Entry<String, List<String>> entry : providerUserIdsByProviderId.entrySet()) {
            String providerId = entry.getKey();
            List<String> providerUserIds = entry.getValue();
            List<Connection<?>> connections = providerUserIdsToConnections(providerId, providerUserIds);
            connections.forEach(connection -> connectionsForUsers.add(providerId, connection));
        }
        return connectionsForUsers;
    }

    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        UsuarioSocial usuarioSocial = socialUserConnectionRepository.findOneByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        return Optional.ofNullable(usuarioSocial)
            .map(this::socialUserConnectionToConnection)
            .orElseThrow(() -> new NoSuchConnectionException(connectionKey));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    @Transactional
    public void addConnection(Connection<?> connection) {
        Long rank = getNewMaxRank(connection.getKey().getProviderId()).longValue();
        UsuarioSocial usuarioSocialToSave = connectionToUserSocialConnection(connection, rank);
        socialUserConnectionRepository.save(usuarioSocialToSave);
    }

    @Override
    @Transactional
    public void updateConnection(Connection<?> connection) {
        UsuarioSocial usuarioSocial = socialUserConnectionRepository.findOneByUserIdAndProviderIdAndProviderUserId(userId, connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
        if (usuarioSocial != null) {
            UsuarioSocial usuarioSocialToUdpate = connectionToUserSocialConnection(connection, usuarioSocial.getRank());
            usuarioSocialToUdpate.setId(usuarioSocial.getId());
            socialUserConnectionRepository.save(usuarioSocialToUdpate);
        }
    }

    @Override
    @Transactional
    public void removeConnections(String providerId) {
        socialUserConnectionRepository.deleteByUserIdAndProviderId(userId, providerId);
    }

    @Override
    @Transactional
    public void removeConnection(ConnectionKey connectionKey) {
        socialUserConnectionRepository.deleteByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
    }

    private Double getNewMaxRank(String providerId) {
        List<UsuarioSocial> usuariosSocial = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(userId, providerId);
        return usuariosSocial.stream()
            .mapToDouble(UsuarioSocial::getRank)
            .max()
            .orElse(0D) + 1D;
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        List<UsuarioSocial> usuariosSocial = socialUserConnectionRepository.findAllByUserIdAndProviderIdOrderByRankAsc(userId, providerId);
        if (usuariosSocial.size() > 0) {
            return socialUserConnectionToConnection(usuariosSocial.get(0));
        } else {
            return null;
        }
    }

    private UsuarioSocial connectionToUserSocialConnection(Connection<?> connection, Long rank) {
        ConnectionData connectionData = connection.createData();
        return new UsuarioSocial(
            userId,
            connection.getKey().getProviderId(),
            connection.getKey().getProviderUserId(),
            rank,
            connection.getDisplayName(),
            connection.getProfileUrl(),
            connection.getImageUrl(),
            connectionData.getAccessToken(),
            connectionData.getSecret(),
            connectionData.getRefreshToken(),
            connectionData.getExpireTime()
        );
    }

    private List<Connection<?>> providerUserIdsToConnections(String providerId, List<String> providerUserIds) {
        List<UsuarioSocial> usuariosSocial = socialUserConnectionRepository.findAllByUserIdAndProviderIdAndProviderUserIdIn(userId, providerId, providerUserIds);
        return socialUserConnectionsToConnections(usuariosSocial);
    }

    private List<Connection<?>> socialUserConnectionsToConnections(List<UsuarioSocial> usuariosSocial) {
        return usuariosSocial.stream()
            .map(this::socialUserConnectionToConnection)
            .collect(Collectors.toList());
    }

    private Connection<?> socialUserConnectionToConnection(UsuarioSocial usuarioSocial) {
        ConnectionData connectionData = new ConnectionData(usuarioSocial.getProviderId(),
            usuarioSocial.getProviderUserId(),
            usuarioSocial.getDisplayName(),
            usuarioSocial.getProfileURL(),
            usuarioSocial.getImageURL(),
            usuarioSocial.getAccessToken(),
            usuarioSocial.getSecret(),
            usuarioSocial.getRefreshToken(),
            usuarioSocial.getExpireTime());
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
        return connectionFactory.createConnection(connectionData);
    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }
}
