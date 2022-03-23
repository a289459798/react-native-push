package com.reactlibrary;

public interface IPush {
    void init();
    void init(String appId, String appKey);
    void setAlias(String alias);
    void unsetAlias(String alias);
}
