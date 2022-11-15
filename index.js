// main index.js

import {
    NativeModules,
    Platform,
    NativeEventEmitter,
    PermissionsAndroid,
} from 'react-native';

const RNJJPush = NativeModules.RNJJPush;
import PushNotificationIOS from '@react-native-community/push-notification-ios';

class JJPush extends NativeEventEmitter {

    // 构造
    constructor(props) {
        super(RNJJPush);
        // 初始状态
        this.state = {};
    }

    init(appkey, secret, data) {
        if (Platform.OS == 'android') {
            PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.READ_PHONE_STATE).then((state) => {
                if (state) {
                    PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE).then((state) => {
                        if (state) {

                            RNJJPush.init(appkey, secret, data);
                        } else {
                            PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE).then((granted) => {
                                if (granted === PermissionsAndroid.RESULTS.GRANTED) {
                                    RNJJPush.init(appkey, secret, data);
                                }
                            });
                        }
                    });
                } else {
                    PermissionsAndroid.requestMultiple([
                        PermissionsAndroid.PERMISSIONS.READ_PHONE_STATE,
                        PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
                    ]).then((granted) => {
                        // if (granted === PermissionsAndroid.RESULTS.GRANTED) {
                        RNJJPush.init(appkey, secret, data);
                        // }
                    });
                }
            });
        } else {
            RNJJPush.init(appkey);
        }
    }

    /**
     * 设置别名
     * @param text
     */
    setAlias(text) {
        return RNJJPush.setAlias(text);
    }

    /**
     * 注销别名
     * @param text
     */
    unsetAlias(text) {
        return RNJJPush.unsetAlias(text);
    }

    /**
     * 设置标签
     * @param text
     */
    setTag(text) {
        return RNJJPush.setTag(text);
    }

    /**
     * 注销标签
     * @param text
     */
    unsetTag(text) {
        return RNJJPush.unsetTag(text);
    }

    notify(title, body) {
        if (Platform.OS == 'ios') {
            PushNotificationIOS.addNotificationRequest({
                id: '1',
                title: title,
                body: body,
            });
        } else {
            RNJJPush.notify(title, body);
        }
    }

    /**
     *
     * @param type
     * ios :
     * notification => 监听收到apns通知
     * localNotification => 监听收到本地通知
     * register => 注册deviceToken 通知
     *
     * android :
     * jjpush_notify => 监听收到推送
     * jjpush_click => 监听推送被点击
     * @param handler
     */
    addEventListener(type, handler) {
        if (Platform.OS == 'ios') {
            switch (type) {
                case 'notification':
                case 'jjpush_notify':
                    if (type == 'jjpush_notify') {
                        type = 'notification';
                    }
                    PushNotificationIOS.addEventListener(type, (res) => {
                        handler && handler(res._data);
                    });
                    break;
                case 'localNotification':
                case 'register':
                    PushNotificationIOS.addEventListener(type, handler);
                    break;
                default:
                    this.addListener(type, handler);
                    break;
            }
        } else {
            this.addListener(type, (res) => {
                handler && handler(JSON.parse(res));
            });
        }
    }

    removeEventListener(type) {
        if (Platform.OS == 'ios') {
            switch (type) {
                case 'notification':
                case 'localNotification':
                case 'register':
                    PushNotificationIOS.removeEventListener(type);
                    break;
                default:
                    this.removeListener(type);
                    break;
            }
        } else {
            this.removeListener(type);
        }
    }

    /**
     * 通过点击通知启动app
     * @param handler
     */
    getInitialNotification(handler) {
        if (Platform.OS == 'ios') {
            PushNotificationIOS.getInitialNotification()
                .then((res) => {
                    if (res && res._data) {
                        handler && handler(res._data);
                    }
                });
        } else {
            // RNJJPush.getInitialNotification()
            //     .then(handler);
        }
    }
}

module.exports = new JJPush();
