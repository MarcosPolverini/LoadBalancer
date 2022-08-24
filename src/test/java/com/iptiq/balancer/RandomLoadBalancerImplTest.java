package com.iptiq.balancer;

class RandomLoadBalancerImplTest extends AbstractLoadBalancerTest {

    @Override
    LoadBalancer produce(int maxRequests) {
        return new RandomLoadBalancerImpl(maxRequests);
    }

}