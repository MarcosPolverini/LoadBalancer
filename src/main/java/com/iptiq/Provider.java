package com.iptiq;

import java.util.UUID;

public class Provider {

    public String get() {
        return UUID.randomUUID().toString();
    }

}
