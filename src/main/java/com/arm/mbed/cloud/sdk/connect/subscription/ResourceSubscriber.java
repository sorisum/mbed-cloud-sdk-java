package com.arm.mbed.cloud.sdk.connect.subscription;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.functions.Consumer;

import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.AbstractApi;
import com.arm.mbed.cloud.sdk.common.MbedCloudException;
import com.arm.mbed.cloud.sdk.common.listing.FilterOptions;
import com.arm.mbed.cloud.sdk.common.listing.Paginator;
import com.arm.mbed.cloud.sdk.connect.adapters.PresubscriptionAdapter;
import com.arm.mbed.cloud.sdk.connect.model.Presubscription;
import com.arm.mbed.cloud.sdk.connect.model.Resource;
import com.arm.mbed.cloud.sdk.devicedirectory.adapters.DeviceAdapter;
import com.arm.mbed.cloud.sdk.devicedirectory.model.Device;
import com.arm.mbed.cloud.sdk.subscribe.model.AsynchronousResponseNotification;
import com.arm.mbed.cloud.sdk.subscribe.model.AsynchronousResponseObserver;
import com.arm.mbed.cloud.sdk.subscribe.model.FirstValue;
import com.arm.mbed.cloud.sdk.subscribe.model.SubscriptionFilterOptions;

@Preamble(description = "Object in charge of performing all necessary action to take place when subscribing to a resource")
public class ResourceSubscriber extends AbstractSubscriptionAction {

    public ResourceSubscriber(AbstractApi api, FirstValue mode) {
        super(api, mode);
    }

    @Override
    public void execute(FilterOptions arg) throws MbedCloudException {
        if (!hasCloudConnection()) {
            return;
        }
        final SubscriptionFilterOptions filters = (SubscriptionFilterOptions) arg;
        // Setting pre-subscriptions
        final List<Presubscription> correspondingPresubscriptions = PresubscriptionAdapter.mapSubscriptionFilter(filters);
        api.addSomePresubscriptions(correspondingPresubscriptions);
        // Subscribe to currently connected devices
        if (mode == FirstValue.ON_VALUE_UPDATE || mode == FirstValue.IMMEDIATELY) {
            final Paginator<Device> iterator = api.listAllConnectedDevices(DeviceAdapter.mapSubscriptionOptions(filters));
            while (iterator.hasNext()) {
                final List<Resource> resourcesToObserve = filters.getVerifiedResources(api.listObservableResources(iterator.next()));
                api.addResourcesSubscription(resourcesToObserve);
                if (mode == FirstValue.IMMEDIATELY) {
                    for (final Resource resourceToObserve : resourcesToObserve) {
                        requestCurrentResourceValue(resourceToObserve);
                    }
                }
            }
        }

    }

    private void requestCurrentResourceValue(final Resource resourceToObserve) {
        try {
            final AsynchronousResponseObserver observer = api.createCurrentResourceValueObserver(resourceToObserve,
                                                                                                 BackpressureStrategy.BUFFER);
            observer.singleNotification(null).doOnError(new Consumer<Throwable>() {

                @Override
                public void accept(Throwable exception) throws Exception {
                    api.getLogger().logError("Could not fetch the current value of " + resourceToObserve, exception);

                }
            }).subscribe(new Consumer<AsynchronousResponseNotification>() {

                @Override
                public void accept(AsynchronousResponseNotification notification) throws Exception {
                    api.getLogger().logDebug("The following asynchronous response was received: " + notification);
                    observer.unsubscribe();
                }
            });
        } catch (MbedCloudException exception) {
            api.getLogger().logError("Could not fetch the current value of " + resourceToObserve, exception);
        }
    }

    @Override
    public ResourceSubscriber clone() {
        return new ResourceSubscriber(api, mode);
    }

}
