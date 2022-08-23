package com.iptiq.balancer;

import java.util.Set;

public interface BalancerStrategy {

    Provider get(final Set<Provider> providers);

}
