package com.iptiq.balancer;

class RoundRobinLoadBalancerImplTest extends AbstractLoadBalancerTest {

    @Override
    LoadBalancer produce(int maxRequests) {
        return new RoundRobinLoadBalancerImpl(maxRequests);
    }

}