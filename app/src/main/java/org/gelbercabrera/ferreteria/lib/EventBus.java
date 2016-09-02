package org.gelbercabrera.ferreteria.lib;

public interface EventBus {
    void register(Object suscriber);
    void unregister(Object suscriber);
    void post(Object event);
}
