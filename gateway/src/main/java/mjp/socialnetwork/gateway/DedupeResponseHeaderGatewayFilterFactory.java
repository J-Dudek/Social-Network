package mjp.socialnetwork.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
Use case: Both your legacy backend and your API gateway add CORS header values. So, your consumer ends up with
          Access-Control-Allow-Credentials: true, true
          Access-Control-Allow-Origin: https://musk.mars, https://musk.mars
(The one from the gateway will be the first of the two.) To fix, add
          DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin

Configuration parameters:
- name
    String representing response header names, space separated. Required.
- strategy
	RETAIN_FIRST - Default. Retain the first value only.
	RETAIN_LAST - Retain the last value only.
	RETAIN_UNIQUE - Retain all unique values in the order of their first encounter.

Example 1
      default-filters:
      - DedupeResponseHeader=Access-Control-Allow-Credentials

Response header Access-Control-Allow-Credentials: true, false
Modified response header Access-Control-Allow-Credentials: true

Example 2
      default-filters:
      - DedupeResponseHeader=Access-Control-Allow-Credentials, RETAIN_LAST

Response header Access-Control-Allow-Credentials: true, false
Modified response header Access-Control-Allow-Credentials: false

Example 3
      default-filters:
      - DedupeResponseHeader=Access-Control-Allow-Credentials, RETAIN_UNIQUE

Response header Access-Control-Allow-Credentials: true, true
Modified response header Access-Control-Allow-Credentials: true
 */

/**
 * Add following Filter Factory in order to remove duplicate value for a Http Header
 * Due to Sprng Cloud  Gateway Service Bug
 *  https://github.com/spring-cloud/spring-cloud-gateway/pull/866
 */
public class DedupeResponseHeaderGatewayFilterFactory
        extends AbstractGatewayFilterFactory<DedupeResponseHeaderGatewayFilterFactory.Config> {

    private static final String STRATEGY_KEY = "strategy";

    public DedupeResponseHeaderGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(NAME_KEY, STRATEGY_KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> dedupe(exchange.getResponse().getHeaders(), config)));
    }

    public enum Strategy {
        /*
        Default: Retain the first value only.
         */
        RETAIN_FIRST,

        /*
        Retain the last value only.
         */
        RETAIN_LAST,

        /*
        Retain all unique values in the order of their first encounter.
         */
        RETAIN_UNIQUE
    }

    void dedupe(HttpHeaders headers, Config config) {
        String names = config.getName();
        Strategy strategy = config.getStrategy();
        if (headers == null || names == null || strategy == null) {
            return;
        }
        for (String name : names.split(" ")) {
            dedupe(headers, name.trim(), strategy);
        }
    }

    private void dedupe(HttpHeaders headers, String name, Strategy strategy) {
        List<String> values = headers.get(name);
        if (values == null || values.size() <= 1) {
            return;
        }
        switch (strategy) {
            case RETAIN_FIRST:
                headers.set(name, values.get(0));
                break;
            case RETAIN_LAST:
                headers.set(name, values.get(values.size() - 1));
                break;
            case RETAIN_UNIQUE:
                headers.put(name, values.stream().distinct().collect(Collectors.toList()));
                break;
            default:
                break;
        }
    }

    public static class Config extends AbstractGatewayFilterFactory.NameConfig {
        private Strategy strategy = Strategy.RETAIN_FIRST;

        public Strategy getStrategy() {
            return strategy;
        }

        public Config setStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }
    }
}