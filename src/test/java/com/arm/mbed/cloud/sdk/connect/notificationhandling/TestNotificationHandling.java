package com.arm.mbed.cloud.sdk.connect.notificationhandling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.arm.mbed.cloud.sdk.common.Callback;
import com.arm.mbed.cloud.sdk.connect.model.Resource;
import com.arm.mbed.cloud.sdk.internal.mds.model.EndpointData;
import com.arm.mbed.cloud.sdk.internal.mds.model.NotificationData;
import com.arm.mbed.cloud.sdk.internal.mds.model.NotificationMessage;
import com.arm.mbed.cloud.sdk.internal.mds.model.ResourcesData;
import com.arm.mbed.cloud.sdk.subscribe.NotificationCallback;
import com.arm.mbed.cloud.sdk.subscribe.model.DeviceState;
import com.arm.mbed.cloud.sdk.subscribe.model.DeviceStateFilterOptions;
import com.arm.mbed.cloud.sdk.subscribe.model.DeviceStateNotification;
import com.arm.mbed.cloud.sdk.subscribe.model.DeviceStateObserver;

import io.reactivex.BackpressureStrategy;

public class TestNotificationHandling {

    @SuppressWarnings("boxing")
    @Test
    public void testNotifyNotificationMessage() {
        Future<?> handle = null;
        ScheduledExecutorService executor = null;
        String[] payloads = { "MQ==", "Mg==", "Mw==", "NA==", "NQ==" };
        try {
            executor = Executors.newScheduledThreadPool(1);
            NotificationHandlersStore store = new NotificationHandlersStore(null, null, executor, null);
            List<Integer> receivedNotificationsUsingObservers = new LinkedList<>();
            List<Integer> receivedNotificationsUsingCallbacks = new LinkedList<>();
            List<Throwable> receivedErrorsUsingCallbacks = new LinkedList<>();
            String deviceId = "015f4ac587f500000000000100100249";
            String resourcePath = "/3200/0/5501";
            Resource resource = new Resource(deviceId, resourcePath);
            store.createResourceSubscriptionObserver(resource, BackpressureStrategy.BUFFER)
                    .subscribe(object -> receivedNotificationsUsingObservers
                            .add(Integer.parseInt(String.valueOf(object.getRawValue()))));
            store.registerSubscriptionCallback(resource, new Callback<Object>() {

                @Override
                public void execute(Object arg) {
                    System.out.println("Received notification: " + arg);
                    receivedNotificationsUsingCallbacks.add(Integer.parseInt(String.valueOf(arg)));
                }
            }, new Callback<Throwable>() {

                @Override
                public void execute(Throwable arg) {
                    System.err.println("Error happened during notification handling: " + arg);
                    receivedErrorsUsingCallbacks.add(arg);

                }
            });
            // The following should not have any impact.
            store.deregisterNotificationSubscriptionCallback(new Resource(deviceId, "/3200/0/5502"));
            store.removeResourceSubscriptionObserver(new Resource(deviceId, "/3200/0/5503"));

            int Interval = 100;
            handle = executor.scheduleWithFixedDelay(new Runnable() {
                List<String> payloadList = Arrays.asList(payloads);
                private int i = 0;

                @Override
                public void run() {
                    if (i < payloadList.size()) {
                        NotificationMessage notifications = new NotificationMessage();
                        NotificationData notification = new NotificationData();
                        notification.setEp(deviceId);
                        notification.setPath(resourcePath);
                        notification.setPayload(payloadList.get(i));
                        notifications.addNotificationsItem(notification);
                        store.notify(null);
                        store.notify(notifications);
                        i++;
                    }
                }
            }, 0, Interval, TimeUnit.MILLISECONDS);
            Thread.sleep((payloads.length + 1) * Interval);
            assertTrue(receivedErrorsUsingCallbacks.isEmpty());
            assertFalse(receivedNotificationsUsingCallbacks.isEmpty());
            assertFalse(receivedNotificationsUsingObservers.isEmpty());
            for (int i = 0; i < payloads.length; i++) {
                assertEquals(i + 1, receivedNotificationsUsingCallbacks.get(i), 0);
                assertEquals(i + 1, receivedNotificationsUsingObservers.get(i), 0);
            }
            store.shutdown();
            if (handle != null) {
                handle.cancel(true);
            }
            executor.shutdownNow();
        } catch (Exception e) {
            if (handle != null) {
                handle.cancel(true);
            }
            if (executor != null) {
                executor.shutdownNow();
            }
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    /**
     * Tests subscriptions to device state changes
     */
    @Test
    public void testDeviceState() {
        Future<?> handle = null;
        ScheduledExecutorService executor = null;
        try {
            List<DeviceStateNotification> receivedNotifications = new LinkedList<>();
            executor = Executors.newScheduledThreadPool(1);
            NotificationHandlersStore store = new NotificationHandlersStore(null, null, executor, null);
            DeviceStateObserver obs1 = store.getSubscriptionManager().deviceState(new DeviceStateFilterOptions()
                    .likeDevice("016%33e").equalDeviceState(DeviceState.REGISTRATION_UPDATE),
                    BackpressureStrategy.BUFFER);
            // Generating notifications
            @SuppressWarnings("boxing")
            List<NotificationMessage> notifications = Stream.iterate(0, n -> n + 1).limit(32).map(i -> {
                NotificationMessage message = new NotificationMessage();
                EndpointData data = new EndpointData();
                if (i % 5 == 0) {
                    data.setEp("0161661e9ce10000000000010010033e");
                } else {
                    data.setEp("0161661edbab000000000001001002b7");
                }
                data.setEpt("random");
                data.setQ(false);
                data.setResources(Stream.iterate(0, n -> n + 1).limit(50).map(v -> {
                    final ResourcesData resource = new ResourcesData();
                    resource.setPath("/" + v);
                    resource.setObs(true);
                    return resource;
                }).collect(Collectors.toList()));

                if (i % 2 == 0) {
                    message.addRegUpdatesItem(data);
                } else {
                    message.addRegistrationsItem(data);
                }
                return message;
            }).collect(Collectors.toList());

            obs1.addCallback(new NotificationCallback<>(new Callback<DeviceStateNotification>() {

                @Override
                public void execute(DeviceStateNotification arg) {
                    receivedNotifications.add(arg);
                }
            }, null));
            int Interval = 300;
            handle = executor.scheduleWithFixedDelay(new Runnable() {

                private int i = 0;

                @Override
                public void run() {
                    if (i < notifications.size()) {
                        store.notify(notifications.get(i));
                        i++;
                    }
                }
            }, 0, Interval, TimeUnit.MILLISECONDS);
            // Waiting for all notifications to be emitted
            Thread.sleep((notifications.size() + 1) * Interval);

            assertFalse(receivedNotifications.isEmpty());
            // Only multiples of 10 between 0 and 32
            assertEquals(4, receivedNotifications.size());
            // Observer only cares about changes related to devices like 016%33e and REGISTRATION_UPDATE state
            receivedNotifications.forEach(n -> {
                assertEquals("0161661e9ce10000000000010010033e", n.getDeviceId());
                assertEquals(DeviceState.REGISTRATION_UPDATE, n.getState());
            });

            if (handle != null) {
                handle.cancel(true);
            }
            executor.shutdownNow();

        } catch (Exception e) {
            if (handle != null) {
                handle.cancel(true);
            }
            if (executor != null) {
                executor.shutdownNow();
            }
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
