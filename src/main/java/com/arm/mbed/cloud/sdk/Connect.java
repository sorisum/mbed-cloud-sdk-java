package com.arm.mbed.cloud.sdk;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

import com.arm.mbed.cloud.sdk.annotations.API;
import com.arm.mbed.cloud.sdk.annotations.Daemon;
import com.arm.mbed.cloud.sdk.annotations.DefaultValue;
import com.arm.mbed.cloud.sdk.annotations.Module;
import com.arm.mbed.cloud.sdk.annotations.NonNull;
import com.arm.mbed.cloud.sdk.annotations.Nullable;
import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.AbstractApi;
import com.arm.mbed.cloud.sdk.common.ApiUtils;
import com.arm.mbed.cloud.sdk.common.Callback;
import com.arm.mbed.cloud.sdk.common.CloudCaller;
import com.arm.mbed.cloud.sdk.common.CloudRequest.CloudCall;
import com.arm.mbed.cloud.sdk.common.ConnectionOptions;
import com.arm.mbed.cloud.sdk.common.GenericAdapter;
import com.arm.mbed.cloud.sdk.common.GenericAdapter.Mapper;
import com.arm.mbed.cloud.sdk.common.JsonSerialiser;
import com.arm.mbed.cloud.sdk.common.MbedCloudException;
import com.arm.mbed.cloud.sdk.common.PageRequester;
import com.arm.mbed.cloud.sdk.common.SynchronousMethod;
import com.arm.mbed.cloud.sdk.common.SynchronousMethod.AsynchronousMethod;
import com.arm.mbed.cloud.sdk.common.TimePeriod;
import com.arm.mbed.cloud.sdk.common.TranslationUtils;
import com.arm.mbed.cloud.sdk.common.UuidGenerator;
import com.arm.mbed.cloud.sdk.common.listing.ListOptions;
import com.arm.mbed.cloud.sdk.common.listing.ListResponse;
import com.arm.mbed.cloud.sdk.common.listing.Paginator;
import com.arm.mbed.cloud.sdk.common.listing.filtering.Filter;
import com.arm.mbed.cloud.sdk.common.listing.filtering.FilterOperator;
import com.arm.mbed.cloud.sdk.connect.adapters.MetricAdapter;
import com.arm.mbed.cloud.sdk.connect.adapters.PresubscriptionAdapter;
import com.arm.mbed.cloud.sdk.connect.adapters.ResourceAdapter;
import com.arm.mbed.cloud.sdk.connect.adapters.WebhookAdapter;
import com.arm.mbed.cloud.sdk.connect.model.AbstractMetricsListOptions;
import com.arm.mbed.cloud.sdk.connect.model.EndPoints;
import com.arm.mbed.cloud.sdk.connect.model.Metric;
import com.arm.mbed.cloud.sdk.connect.model.MetricsPeriodListOptions;
import com.arm.mbed.cloud.sdk.connect.model.MetricsStartEndListOptions;
import com.arm.mbed.cloud.sdk.connect.model.Presubscription;
import com.arm.mbed.cloud.sdk.connect.model.Resource;
import com.arm.mbed.cloud.sdk.connect.model.Subscription;
import com.arm.mbed.cloud.sdk.connect.model.Webhook;
import com.arm.mbed.cloud.sdk.connect.subscription.NotificationHandlersStore;
import com.arm.mbed.cloud.sdk.connect.subscription.ResourceAction;
import com.arm.mbed.cloud.sdk.connect.subscription.ResourceActionParameters;
import com.arm.mbed.cloud.sdk.connect.subscription.ResourceValueType;
import com.arm.mbed.cloud.sdk.connect.subscription.adapters.ResourceActionAdapter;
import com.arm.mbed.cloud.sdk.devicedirectory.model.Device;
import com.arm.mbed.cloud.sdk.devicedirectory.model.DeviceListOptions;
import com.arm.mbed.cloud.sdk.devicedirectory.model.DeviceState;
import com.arm.mbed.cloud.sdk.internal.mds.model.DeviceRequest;
import com.arm.mbed.cloud.sdk.internal.mds.model.NotificationMessage;
import com.arm.mbed.cloud.sdk.internal.mds.model.PresubscriptionArray;
import com.arm.mbed.cloud.sdk.internal.statistics.model.SuccessfulResponse;
import com.arm.mbed.cloud.sdk.subscribe.CloudSubscriptionManager;
import com.arm.mbed.cloud.sdk.subscribe.NotificationMessageValue;
import com.arm.mbed.cloud.sdk.subscribe.Observer;
import com.arm.mbed.cloud.sdk.subscribe.model.AsynchronousResponseNotification;
import com.arm.mbed.cloud.sdk.subscribe.model.AsynchronousResponseObserver;

import retrofit2.Call;

@Preamble(description = "Specifies Connect API")
@Module
/**
 * API exposing functionality for doing a range of device related actions:
 * <p>
 * 1) Listing connected devices
 * <p>
 * 2) Exploring and managing resources and resource values on said devices
 * <p>
 * 3) Setup resource subscriptions and webhooks for resource monitoring
 */
public class Connect extends AbstractApi {
    private static final String BUFFER = "BUFFER";
    private static final String TAG_VALUE_TYPE = "valueType";
    private static final String TAG_PRESUBSCRIPTION = "presubscription";
    private static final String TAG_ON_NOTIFICATION_CALLBACK = "on notification callback";
    private static final String TAG_WEBHOOK = "webhook";
    private static final Filter CONNECTED_DEVICES_FILTER = new Filter("state", FilterOperator.EQUAL,
                                                                      DeviceState.getIsConnectedState().getString());
    private static final String TAG_RESOURCE = "resource";
    private static final String FALSE = "false";
    private static final String TAG_RESOURCE_PATH = "resource path";
    private static final String TAG_METRIC_OPTIONS = "Metric options";
    private static final String TAG_DEVICE_ID = "Device Id";
    private static final String TAG_DEVICE = "Device";
    private final EndPoints endpoint;
    private final DeviceDirectory deviceDirectory;
    private final NotificationHandlersStore handlersStore;

    /**
     * Connect module constructor.
     * <p>
     * This module spawns threads for retrieving notifications when daemon is started @see
     * {@link #startNotifications()}.
     * <p>
     * By default, the executor services in charge are defined and managed internally. It is however possible to specify
     * your own executors @see #Connect(ConnectionOptions, ExecutorService, ExecutorService).
     *
     * @param options
     *            connection options @see {@link ConnectionOptions}.
     */
    public Connect(@NonNull ConnectionOptions options) {
        this(options, null, null);
    }

    /**
     * Connect module constructor.
     * <p>
     * As opposed to {@link #Connect(ConnectionOptions)} which uses default thread pools for retrieving notifications,
     * this constructor lets you the possibility to specify the executor services to use.
     *
     * @param options
     *            connection options @see {@link ConnectionOptions}.
     *
     * @param notificationHandlingThreadPool
     *            Threads in charge of retrieving notifications for a specific resource. If null, a default thread pool
     *            will be created internally.
     * @param notificationPullingThreadPool
     *            Threads in charge of listening to notifications. The pool can either be a scheduled thread pool or a
     *            fixed thread pool depending on what best suits your system. If null, an internal timer will be created
     *            internally.
     */
    public Connect(@NonNull ConnectionOptions options, @Nullable ExecutorService notificationHandlingThreadPool,
                   @Nullable ExecutorService notificationPullingThreadPool) {
        super(options);
        endpoint = new EndPoints(options);
        deviceDirectory = new DeviceDirectory(options);
        this.handlersStore = new NotificationHandlersStore(this,
                                                           (notificationPullingThreadPool == null) ? createDefaultDaemonThreadPool()
                                                                                                   : notificationPullingThreadPool,
                                                           notificationHandlingThreadPool, endpoint);

    }

    /**
     * Creates a default thread pool in case none was specified.
     *
     * @return thread pool
     */
    private static ScheduledExecutorService createDefaultDaemonThreadPool() {
        return Executors.newScheduledThreadPool(1, new ThreadFactory() {

            @Override
            public Thread newThread(Runnable runable) {
                final Thread thread = new Thread(runable);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    /**
     * Starts notification pull.
     * <p>
     * If an external callback is not set up (using `update_webhook`) then calling this function is mandatory to get or
     * set resources. Unless {@link ConnectionOptions#setAutostartDaemon(boolean)} has been set to true or left as
     * default.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * connectApi.startNotifications();
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    @Daemon(task = "Notification pull", start = true)
    public void startNotifications() throws MbedCloudException {
        logger.logInfo(getModuleName() + ": startNotifications()");
        Webhook webhook = null;
        try {
            if (isForceClear()) {
                deleteWebhook();
            }
        } catch (MbedCloudException exception) {
            // Nothing to do
        }
        try {
            webhook = getWebhook();
        } catch (MbedCloudException exception) {
            // Nothing to do
        }
        if (webhook != null) {
            logger.throwSdkException("A webhook is currently set up [" + webhook
                                     + "]. Notification pull cannot be used at the same time. Please remove the webhook if you want to use this mechanism instead.");
        }
        handlersStore.startNotificationPull();
    }

    private void autostartDaemonIfNeeded() throws MbedCloudException {
        if (!handlersStore.isPullingActive() && endpoint.isAutostartDaemon()) {
            startNotifications();
        }
    }

    /**
     * Stops notification pull for notifications.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * connectApi.stopNotifications();
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    @Daemon(task = "Notification pull", stop = true)
    public void stopNotifications() throws MbedCloudException {
        logger.logInfo(getModuleName() + ": stopNotifications()");
        handlersStore.stopNotificationPull();
        CloudCaller.call(this, "stopNotification()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getNotifications().deleteLongPollChannel();
            }
        });
    }

    /**
     * Shuts down all daemon services.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * connectApi.shutdownConnectService();
     * }
     * </pre>
     */
    @API
    @Daemon(task = "Notification pull", shutdown = true)
    public void shutdownConnectService() {
        logger.logInfo(getModuleName() + ": shutdownConnectService()");
        handlersStore.shutdown();
    }

    /**
     * Lists connected devices (One page).
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     DeviceListOptions options = new DeviceListOptions();
     *
     *     Calendar date = GregorianCalendar(2017,10,30,10,20,56);
     *     options.addCreatedAtFilter(date.getTime(), FilterOperator.GREATER_THAN);
     *
     *     options.addDeviceTypeFilter("default", FilterOperator.EQUAL);
     *
     *     ListResponse<Device> devices = connectApi.listConnectedDevices(options);
     *     for (Device device : devices) {
     *         System.out.println("Device ID: " + device.getId());
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param options
     *            filter options
     * @return the list of connected devices (One page).
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable ListResponse<Device> listConnectedDevices(DeviceListOptions options) throws MbedCloudException {
        return deviceDirectory.listDevicesWithExtraFilters("listConnectedDevices()", options, CONNECTED_DEVICES_FILTER);
    }

    /**
     * Gets an iterator over all connected devices according to filter options.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     DeviceListOptions options = new DeviceListOptions();
     *
     *     Calendar date = GregorianCalendar(2017,10,30,10,20,56);
     *     options.addCreatedAtFilter(date.getTime(), FilterOperator.GREATER_THAN);
     *
     *     options.addDeviceTypeFilter("default", FilterOperator.EQUAL);
     *
     *     Paginator<Device> devices = connectApi.listAllDevices(options);
     *     while (devices.hasNext()) {
     *         Device device = devices.next();
     *         System.out.println("Device ID: " + device.getId());
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param options
     *            filter options.
     * @return paginator @see {@link Paginator} for the list of devices corresponding to filter options.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Paginator<Device>
           listAllConnectedDevices(@Nullable DeviceListOptions options) throws MbedCloudException {
        return new Paginator<>((options == null) ? new DeviceListOptions() : options, new PageRequester<Device>() {

            @Override
            public ListResponse<Device> requestNewPage(ListOptions opt) throws MbedCloudException {
                return listConnectedDevices((DeviceListOptions) opt);
            }
        });
    }

    /**
     * Lists device's resources.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *
     *     List<Resource> resources = connectApi.listResources(device);
     *     for (Resource resource : resources) {
     *         System.out.println("Resource path: " + resource.getPath());
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param device
     *            Device.
     * @return list of resources present on a device.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable List<Resource> listResources(@NonNull Device device) throws MbedCloudException {
        checkNotNull(device, TAG_DEVICE);
        checkNotNull(device.getId(), TAG_DEVICE_ID);
        final String finalDeviceId = device.getId();

        return CloudCaller.call(this, "listResources()", ResourceAdapter.getListMapper(finalDeviceId),
                                new CloudCall<List<com.arm.mbed.cloud.sdk.internal.mds.model.Resource>>() {

                                    @Override
                                    public Call<List<com.arm.mbed.cloud.sdk.internal.mds.model.Resource>> call() {
                                        return endpoint.getEndpoints().getEndpointResources(finalDeviceId);
                                    }
                                });
    }

    /**
     * Lists device's observable resources.
     *
     * @see Resource#isObservable()
     *      <p>
     *      Example:
     *
     *      <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *
     *     List<Resource> resources = connectApi.listObservableResources(device);
     *     for (Resource resource : resources) {
     *         System.out.println("Resource path: " + resource.getPath());
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     *      </pre>
     *
     * @param device
     *            Device.
     * @return list of observable resources present on a device.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable List<Resource> listObservableResources(@NonNull Device device) throws MbedCloudException {
        final List<Resource> resources = listResources(device);
        if (resources == null || resources.isEmpty()) {
            return null;
        }
        final List<Resource> observableResources = new LinkedList<>();
        for (final Resource resource : resources) {
            if (resource.isObservable()) {
                observableResources.add(resource);
            }
        }
        return observableResources.isEmpty() ? null : observableResources;
    }

    /**
     * Gets device's resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *
     *     String resourcePath = "/3201/0/5853";
     *
     *     Resource resource = connectApi.getResource(device, resourcePath);
     *     System.out.println("Confirmed resource path: " + resource.getPath());
     *     assert resourcePath == resource.getPath();
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param device
     *            Device.
     * @param resourcePath
     *            Path of the resource to get
     * @return resource present on the device.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Resource getResource(@NonNull Device device,
                                          @NonNull String resourcePath) throws MbedCloudException {
        checkNotNull(device, TAG_DEVICE);
        checkNotNull(device.getId(), TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        final List<Resource> resources = listResources(device);
        if (resources == null || resources.isEmpty()) {
            return null;
        }
        for (final Resource resource : resources) {
            if (ApiUtils.comparePaths(resourcePath, resource.getPath())) {
                return resource;
            }
        }
        return null;
    }

    /**
     * Lists a device's subscriptions.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *
     *     List<String> subscriptions = connectApi.listDeviceSubscriptions(device);
     *     for (String subscription : subscriptions) {
     *         System.out.println("Device subscription: " + subscription);
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param device
     *            Device.
     * @return list of subscriptions
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable List<String> listDeviceSubscriptions(@NonNull Device device) throws MbedCloudException {
        checkNotNull(device, TAG_DEVICE);
        checkNotNull(device.getId(), TAG_DEVICE_ID);
        final String finalDeviceId = device.getId();

        return CloudCaller.call(this, "listDeviceSubscriptions()", PresubscriptionAdapter.getResourcePathListMapper(),
                                new CloudCall<String>() {

                                    @Override
                                    public Call<String> call() {
                                        return endpoint.getSubscriptions().getEndpointSubscriptions(finalDeviceId);
                                    }
                                });
    }

    /**
     * Gets the subscribe module.
     *
     * @return subscribe module.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public CloudSubscriptionManager subscribe() throws MbedCloudException {
        autostartDaemonIfNeeded();
        return this.handlersStore.getSubscriptionManager();
    }

    /**
     * Lists metrics.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Calendar startDate = GregorianCalendar(2017,10,30,10,20,56);
     *     Calendar endDate = GregorianCalendar(2017,11,31,10,20,56);
     *
     *     MetricsStartEndListOptions listOptions = new MetricsStartEndListOptions();
     *     listOptions.setStart(startDate.getTime());
     *     listOptions.setEnd(endDate.getTime());
     *     listOptions.setInterval(new TimePeriod(360)); //Once an hour
     *
     *     ListResponse<Metric> metrics = connectApi.listMetrics(listOptions);
     *     //Iterates over a page
     *     for (Metric metric : metrics.getData()) {
     *         System.out.println("Time: " + dateFormat.format(metric.getTimestamp()));
     *         System.out.println("Successful bootstraps: " + metric.getSuccessfulBootstraps());
     *         System.out.println("Successful api calls: " + metric.getSuccessfulApiCalls());
     *         System.out.println("");
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param options
     *            metrics options.
     * @param <T>
     *            Type of metrics list options
     * @return list of metrics for the corresponding options.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable <T extends AbstractMetricsListOptions> ListResponse<Metric>
           listMetrics(@NonNull T options) throws MbedCloudException {
        checkNotNull(options, TAG_METRIC_OPTIONS);
        final T finalOptions = options;
        final Date finalStart = options instanceof MetricsStartEndListOptions ? ((MetricsStartEndListOptions) options).getStart()
                                                                              : null;
        final Date finalEnd = options instanceof MetricsStartEndListOptions ? ((MetricsStartEndListOptions) options).getEnd()
                                                                            : null;
        final String finalPeriod = options instanceof MetricsPeriodListOptions ? ((MetricsPeriodListOptions) options).getPeriod()
                                                                                                                     .toString()
                                                                               : null;

        return CloudCaller.call(this, "listMetrics()", MetricAdapter.getListMapper(),
                                new CloudCall<SuccessfulResponse>() {

                                    @Override
                                    public Call<SuccessfulResponse> call() {
                                        return endpoint.getStatistic()
                                                       .getMetrics(MetricAdapter.mapIncludes(finalOptions.getInclude()),
                                                                   finalOptions.getInterval().toString(),
                                                                   TranslationUtils.toLocalDate(finalStart),
                                                                   TranslationUtils.toLocalDate(finalEnd), finalPeriod,
                                                                   finalOptions.getPageSize(), finalOptions.getAfter(),
                                                                   finalOptions.getOrder().toString());
                                    }
                                });

    }

    /**
     * Gets an iterator over all metrics according to filter options.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Calendar startDate = GregorianCalendar(2017,10,30,10,20,56);
     *     Calendar endDate = GregorianCalendar(2017,11,31,10,20,56);
     *
     *     MetricsStartEndListOptions listOptions = new MetricsStartEndListOptions();
     *     listOptions.setStart(startDate.getTime());
     *     listOptions.setEnd(endDate.getTime());
     *     listOptions.setInterval(new TimePeriod(360)); //Once an hour
     *
     *     Paginator<Metric> metrics = connectApi.listAllMetrics(listOptions);
     *     for (Metric metric : metrics) {
     *         System.out.println("Time: " + dateFormat.format(metric.getTimestamp()));
     *         System.out.println("Successful bootstraps: " + metric.getSuccessfulBootstraps());
     *         System.out.println("Successful api calls: " + metric.getSuccessfulApiCalls());
     *         System.out.println("");
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * }
     * </pre>
     *
     * @param options
     *            filter options.
     * @param <T>
     *            Type of metrics list options
     * @return paginator @see {@link Paginator} for the list of metrics corresponding to filter options.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable <T extends AbstractMetricsListOptions> Paginator<Metric>
           listAllMetrics(@NonNull T options) throws MbedCloudException {
        checkNotNull(options, TAG_METRIC_OPTIONS);
        return new Paginator<>(options, new PageRequester<Metric>() {

            @SuppressWarnings("unchecked")
            @Override
            public ListResponse<Metric> requestNewPage(ListOptions opt) throws MbedCloudException {
                return listMetrics((T) opt);
            }
        });
    }

    /**
     * Creates a observer to obtain resource current value.
     * <p>
     * See {@link Observer}
     * 
     * @param resource
     *            resource of interest.
     * @param strategy
     *            backpressure strategy.
     * @return corresponding observer.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    public @Nullable AsynchronousResponseObserver
           createCurrentResourceValueObserver(@NonNull Resource resource,
                                              @Nullable @DefaultValue(BUFFER) BackpressureStrategy strategy) throws MbedCloudException {

        return createResourceActionObserver(createResourceAction("getResourceValueAsync()",
                                                                 ResourceActionAdapter.getGetResourceValueMapper()),
                                            resource, strategy, null, null, true);
    }

    /**
     * Creates a observer to set a resource value.
     * <p>
     * See {@link Observer}
     * 
     * @param resource
     *            resource of interest.
     * @param strategy
     *            backpressure strategy.
     * @param value
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @return corresponding observer.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    public @Nullable AsynchronousResponseObserver
           createSetResourceValueObserver(@NonNull Resource resource,
                                          @Nullable @DefaultValue(BUFFER) BackpressureStrategy strategy,
                                          @Nullable Object value,
                                          @NonNull ResourceValueType valueType) throws MbedCloudException {

        return createResourceActionObserver(createResourceAction("setResourceValueAsync()",
                                                                 ResourceActionAdapter.getSetResourceValueMapper()),
                                            resource, strategy, value, valueType, true);
    }

    /**
     * Creates a observer to execute a resource.
     * <p>
     * See {@link Observer}
     * 
     * @param resource
     *            resource of interest.
     * @param strategy
     *            backpressure strategy.
     * @param value
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @return corresponding observer.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    public @Nullable AsynchronousResponseObserver
           createExecuteResourceValueObserver(@NonNull Resource resource,
                                              @Nullable @DefaultValue(BUFFER) BackpressureStrategy strategy,
                                              @Nullable Object value,
                                              @NonNull ResourceValueType valueType) throws MbedCloudException {
        return createResourceActionObserver(createResourceAction("executeResourceAsync()",
                                                                 ResourceActionAdapter.getExecuteResourceValueMapper()),
                                            resource, strategy, value, valueType, false);
    }

    protected ResourceAction createResourceAction(String functionName,
                                                  Mapper<ResourceActionParameters, DeviceRequest> requestMapper) {
        final AbstractApi module = this;
        final String function = functionName;
        final Mapper<ResourceActionParameters, DeviceRequest> finalMapper = requestMapper;
        return new ResourceAction() {

            @Override
            public void execute(ResourceActionParameters arg) throws MbedCloudException {
                final ResourceActionParameters finalArgs = arg;
                CloudCaller.call(module, function, null, new CloudCall<Void>() {

                    @Override
                    public Call<Void> call() {
                        return endpoint.getAsync().createAsyncRequest(finalArgs.getResource().getDeviceId(),
                                                                      finalArgs.getAsyncId(),
                                                                      finalMapper.map(finalArgs));
                    }
                });

            }

        };
    }

    protected AsynchronousResponseObserver
              createResourceActionObserver(ResourceAction action, Resource resource, BackpressureStrategy strategy,
                                           Object value, ResourceValueType type,
                                           boolean notifyOtherObservers) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        autostartDaemonIfNeeded();
        final ResourceActionParameters parameters = new ResourceActionParameters(UuidGenerator.generate(), resource,
                                                                                 value, type);
        final BackpressureStrategy finalStrategy = (strategy == null) ? BackpressureStrategy.BUFFER : strategy;
        final AsynchronousResponseObserver observer = handlersStore.createAsyncResponseObserver(parameters.getResource(),
                                                                                                parameters.getAsyncId(),
                                                                                                finalStrategy,
                                                                                                notifyOtherObservers);

        try {
            action.execute(parameters);
        } catch (MbedCloudException exception) {
            observer.unsubscribe();
            throw exception;
        }
        return observer;
    }

    protected Future<Object> convertObserverToFuture(AsynchronousResponseObserver observer) throws MbedCloudException {
        try {
            final Flowable<Object> newFlow = observer.flow()
                                                     .map(new Function<AsynchronousResponseNotification, Object>() {

                                                         @Override
                                                         public Object
                                                                apply(AsynchronousResponseNotification notification) throws Exception {
                                                             if (notification.reportsFailure()) {
                                                                 return notification.toError();
                                                             } else {
                                                                 return notification.getPayload();
                                                             }
                                                         }
                                                     }).take(1);
            return newFlow.toFuture();

        } catch (Exception exception) {
            throw new MbedCloudException(exception);
        }
    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Future<Object> futureLedPattern = connectApi.getResourceValueAsync(deviceId, resourcePath, false, false);
     *     String ledPattern = (String)futureLedPattern.get();
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param cacheOnly
     *            If true, the response will come only from the cache.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @return A Future from which it is possible to obtain resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Future<Object>
           getResourceValueAsync(@NonNull String deviceId, @NonNull String resourcePath,
                                 @DefaultValue(value = FALSE) boolean cacheOnly,
                                 @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        return getResourceValueAsync(deviceId, resourcePath);
    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Future<Object> futureLedPattern = connectApi.getResourceValueAsync(deviceId, resourcePath);
     *     String ledPattern = (String)futureLedPattern.get();
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @return A Future from which it is possible to obtain resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Future<Object> getResourceValueAsync(@NonNull String deviceId,
                                                          @NonNull String resourcePath) throws MbedCloudException {
        checkNotNull(deviceId, TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        return getResourceValueAsync(new Resource(deviceId, resourcePath));
    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = connectApi.getResource(device, resourcePath);
    
     *     Future<Object> futureLedPattern = connectApi.getResourceValueAsync(resource, false, false);
     *     String ledPattern = (String)futureLedPattern.get();
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to get the value of.
     * @param cacheOnly
     *            If true, the response will come only from the cache.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @return A Future from which it is possible to obtain resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Future<Object>
           getResourceValueAsync(@NonNull Resource resource, @DefaultValue(value = FALSE) boolean cacheOnly,
                                 @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        return getResourceValueAsync(resource);

    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     device.setId("015f4ac587f500000000000100100249");
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = connectApi.getResource(device, resourcePath);
    
     *     Future<Object> futureLedPattern = connectApi.getResourceValueAsync(resource);
     *     String ledPattern = (String)futureLedPattern.get();
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to get the value of.
     * @return A Future from which it is possible to obtain resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Future<Object> getResourceValueAsync(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        return convertObserverToFuture(createCurrentResourceValueObserver(resource, BackpressureStrategy.BUFFER));

    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String ledPattern = String.valueOf(connectApi.getResourceValue(deviceId, resourcePath, false, false, new TimePeriod(5)));
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param cacheOnly
     *            If true, the response will come only from the cache.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Object getResourceValue(@NonNull String deviceId, @NonNull String resourcePath,
                                             @DefaultValue(value = FALSE) boolean cacheOnly,
                                             @DefaultValue(value = FALSE) boolean noResponse,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        return getResourceValue(deviceId, resourcePath, timeout);
    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String ledPattern = String.valueOf(connectApi.getResourceValue(deviceId, resourcePath,  new TimePeriod(5)));
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param timeout
     *            Timeout for the request.
     * @return resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object getResourceValue(@NonNull String deviceId, @NonNull String resourcePath,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        checkNotNull(deviceId, TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        return getResourceValue(new Resource(deviceId, resourcePath), timeout);
    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String ledPattern = String.valueOf(connectApi.getResourceValue(resource, false, false, new TimePeriod(5)));
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to get the value of.
     * @param cacheOnly
     *            If true, the response will come only from the cache.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Object getResourceValue(@NonNull Resource resource, @DefaultValue(value = FALSE) boolean cacheOnly,
                                             @DefaultValue(value = FALSE) boolean noResponse,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        return getResourceValue(resource, timeout);

    }

    /**
     * Gets a resource value for a given device id and resource path.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String ledPattern = String.valueOf(connectApi.getResourceValue(resource, new TimePeriod(5)));
     *     System.out.println("LED pattern from device: " + ledPattern);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     *
     * @param resource
     *            The resource path to get the value of.
     * @param timeout
     *            Timeout for the request.
     * @return resource value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object getResourceValue(@NonNull Resource resource,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResource = resource;
        try {
            return SynchronousMethod.waitForCompletion(this, "getResourceValue()", new AsynchronousMethod<Object>() {

                @Override
                public Future<Object> submit() throws MbedCloudException {
                    return getResourceValueAsync(finalResource);
                }
            }, timeout);
        } catch (MbedCloudException exception) {
            logger.throwSdkException(exception);
        }
        return null;
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String resourceValue = "500:500:500";
     *
     *     Future<Object> futureLedPattern = connectApi.setResourceValueAsync(deviceId, resourcePath, resourceValue, false);
     *     String setValue = (String)futureLedPattern.get();
     *     assert setValue == resourceValue;
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param resourceValue
     *            value to set.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @return A Future from which it is possible to set the value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Future<Object>
           setResourceValueAsync(@NonNull String deviceId, @NonNull String resourcePath, @Nullable String resourceValue,
                                 @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        return setResourceValueAsync(deviceId, resourcePath, resourceValue, ResourceValueType.STRING);
    }

    /**
     * Sets the value of a resource.
     * 
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param resourceValue
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @return A Future from which it is possible to set the value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Future<Object>
           setResourceValueAsync(@NonNull String deviceId, @NonNull String resourcePath, @Nullable Object resourceValue,
                                 @NonNull ResourceValueType valueType) throws MbedCloudException {
        checkNotNull(deviceId, TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        return setResourceValueAsync(new Resource(deviceId, resourcePath), resourceValue, valueType);
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String resourceValue = "500:500:500";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     Future<Object> futureLedPattern = connectApi.setResourceValueAsync(resource, resourceValue, false);
     *     String setValue = (String)futureLedPattern.get();
     *     assert setValue == resourceValue;
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to set the value of.
     * @param resourceValue
     *            value to set.
     * @param noResponse
     *            If true, Pelion Cloud will not wait for a response.
     * 
     * @return A Future from which it is possible to set the value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @Deprecated
    @API
    public @Nullable Future<Object>
           setResourceValueAsync(@NonNull Resource resource, @Nullable String resourceValue,
                                 @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        return setResourceValueAsync(resource, resourceValue, ResourceValueType.STRING);
    }

    /**
     * Sets the value of a resource.
     * 
     * @param resource
     *            The resource to set the value of.
     * @param resourceValue
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @return A Future from which it is possible to set the value.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Future<Object>
           setResourceValueAsync(@NonNull Resource resource, @Nullable Object resourceValue,
                                 @NonNull ResourceValueType valueType) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        checkNotNull(valueType, TAG_VALUE_TYPE);
        return convertObserverToFuture(createSetResourceValueObserver(resource, BackpressureStrategy.BUFFER,
                                                                      resourceValue, valueType));
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String resourceValue = "500:500:500";
     *
     *     Object resultObject = connectApi.setResourceValue(deviceId, resourcePath, resourceValue, false, new TimePeriod(5));
     *     String setValue = (String)resultObject;
     *     assert setValue == resourceValue;
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param resourceValue
     *            value to set.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Object setResourceValue(@NonNull String deviceId, @NonNull String resourcePath,
                                             @Nullable String resourceValue,
                                             @DefaultValue(value = FALSE) boolean noResponse,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        return setResourceValue(deviceId, resourcePath, resourceValue, ResourceValueType.STRING, timeout);
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param resourceValue
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object setResourceValue(@NonNull String deviceId, @NonNull String resourcePath,
                                             @Nullable Object resourceValue, @NonNull ResourceValueType valueType,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        checkNotNull(deviceId, TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        return setResourceValue(new Resource(deviceId, resourcePath), resourceValue, valueType, timeout);
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param resourceValue
     *            value to set.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object setResourceValue(@NonNull String deviceId, @NonNull String resourcePath,
                                             @Nullable String resourceValue,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {

        return setResourceValue(deviceId, resourcePath, resourceValue, ResourceValueType.STRING, timeout);
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String resourceValue = "500:500:500";
     *
     *     Object resultObject = connectApi.setResourceValue(resource, resourceValue, new TimePeriod(5));
     *     String setValue = (String)resultObject;
     *     assert setValue == resourceValue;
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to set the value of.
     * @param resourceValue
     *            value to set.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @Deprecated
    @API
    public @Nullable Object setResourceValue(@NonNull Resource resource, @Nullable String resourceValue,
                                             @DefaultValue(value = FALSE) boolean noResponse,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        return setResourceValue(resource, resourceValue, ResourceValueType.STRING, timeout);
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * 
     * @param resource
     *            The resource to set the value of.
     * @param resourceValue
     *            value to set.
     * @param valueType
     *            type of the value to set.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object setResourceValue(@NonNull Resource resource, @Nullable Object resourceValue,
                                             @NonNull ResourceValueType valueType,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResouce = resource;
        final Object value = resourceValue;
        final ResourceValueType type = valueType;
        try {
            return SynchronousMethod.waitForCompletion(this, "setResourceValue()", new AsynchronousMethod<Object>() {

                @Override
                public Future<Object> submit() throws MbedCloudException {
                    return setResourceValueAsync(finalResouce, value, type);
                }
            }, timeout);
        } catch (MbedCloudException exception) {
            logger.throwSdkException(exception);
        }
        return null;
    }

    /**
     * Sets the value of a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String resourceValue = "500:500:500";
     *
     *     Object resultObject = connectApi.setResourceValue(resource, resourceValue, new TimePeriod(5));
     *     String setValue = (String)resultObject;
     *     assert setValue == resourceValue;
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to set the value of.
     * @param resourceValue
     *            value to set.
     * @param timeout
     *            Timeout for the request.
     * @return The value of the new resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object setResourceValue(@NonNull Resource resource, @Nullable String resourceValue,
                                             @Nullable TimePeriod timeout) throws MbedCloudException {
        return setResourceValue(resource, resourceValue, ResourceValueType.STRING, timeout);
    }

    /**
     * Executes a function on a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String functionName = null;
     *
     *     Future<Object> resultObject = connectApi.executeResourceAsync(deviceId, resourcePath, functionName, false);
     *     String resultValue = (String)resultObject.get();
     *     System.out.println("Result from the function executed: " + resultValue);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param functionName
     *            The function to trigger.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @return A Future from which it is possible to get the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @Deprecated
    @API
    public @Nullable Future<Object>
           executeResourceAsync(@NonNull String deviceId, @NonNull String resourcePath, @Nullable String functionName,
                                @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        checkNotNull(deviceId, TAG_DEVICE_ID);
        checkNotNull(resourcePath, TAG_RESOURCE_PATH);
        return executeResourceAsync(new Resource(deviceId, resourcePath), functionName);
    }

    /**
     * Executes a function on a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String functionName = null;
     *
     *     Future<Object> resultObject = connectApi.executeResourceAsync(resource, functionName, false);
     *     String resultValue = (String)resultObject.get();
     *     System.out.println("Result from the function executed: " + resultValue);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource to execute the function on.
     * @param functionName
     *            The function to trigger.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @return A Future from which it is possible to get the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @Deprecated
    @API
    public @Nullable Future<Object>
           executeResourceAsync(@NonNull Resource resource, @Nullable String functionName,
                                @DefaultValue(value = FALSE) boolean noResponse) throws MbedCloudException {
        return executeResourceAsync(resource, functionName);
    }

    /**
     * Executes a function on a resource.
     * 
     * @param resource
     *            The resource to execute the function on.
     * @param functionName
     *            The function to trigger.
     * @return A Future from which it is possible to get the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Future<Object> executeResourceAsync(@NonNull Resource resource,
                                                         @Nullable String functionName) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        return convertObserverToFuture(createExecuteResourceValueObserver(resource, BackpressureStrategy.BUFFER,
                                                                          functionName, ResourceValueType.STRING));
    }

    /**
     * Executes a function on a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     String functionName = null;
     *
     *     Object resultObject = connectApi.executeResource(deviceId, resourcePath, functionName, false, new TimePeriod(5));
     *     System.out.println("Result from the function executed: " + (String)resultObject);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deviceId
     *            The name/id of the device.
     * @param resourcePath
     *            The resource path to get.
     * @param functionName
     *            The function to trigger.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @Deprecated
    @API
    public @Nullable Object executeResource(@NonNull String deviceId, @NonNull String resourcePath,
                                            @Nullable String functionName,
                                            @DefaultValue(value = FALSE) boolean noResponse,
                                            @Nullable TimePeriod timeout) throws MbedCloudException {
        return executeResource(new Resource(deviceId, resourcePath), functionName, timeout);
    }

    /**
     * Executes a function on a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3201/0/5853";
     *     Resource resource = new Resource(deviceId, resourcePath);
     *     String functionName = null;
     *
     *     Object resultObject = connectApi.executeResource(resource, functionName, false, new TimePeriod(5));
     *     System.out.println("Result from the function executed: " + (String)resultObject);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            The resource path to execute the function on.
     * @param functionName
     *            The function to trigger.
     * @param noResponse
     *            If true, mbed Device Connector will not wait for a response.
     * @param timeout
     *            Timeout for the request.
     * @return the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public @Nullable Object executeResource(@NonNull Resource resource, @Nullable String functionName,
                                            @DefaultValue(value = FALSE) boolean noResponse,
                                            @Nullable TimePeriod timeout) throws MbedCloudException {
        return executeResource(resource, functionName, timeout);
    }

    /**
     * Executes a function on a resource.
     * <p>
     * Note: Waits if necessary for the computation to complete, and then retrieves its result.
     *
     * @param resource
     *            The resource path to execute the function on.
     * @param functionName
     *            The function to trigger.
     * @param timeout
     *            Timeout for the request.
     * @return the value returned from the function executed on the resource.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Object executeResource(@NonNull Resource resource, @Nullable String functionName,
                                            @Nullable TimePeriod timeout) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResource = resource;
        final String function = functionName;
        try {
            return SynchronousMethod.waitForCompletion(this, "executeResource()", new AsynchronousMethod<Object>() {

                @Override
                public Future<Object> submit() throws MbedCloudException {
                    return executeResourceAsync(finalResource, function);
                }
            }, timeout);
        } catch (MbedCloudException exception) {
            logger.throwSdkException(exception);
        }
        return null;
    }

    protected GenericAdapter.MappedObjectRegistry<Presubscription>
              getCurrentPresubscriptionRegistry(String methodName) throws MbedCloudException {
        return CloudCaller.call(this, methodName, PresubscriptionAdapter.getListToRegistryMapper(),
                                new CloudCall<PresubscriptionArray>() {

                                    @Override
                                    public Call<PresubscriptionArray> call() {
                                        return endpoint.getSubscriptions().getPreSubscriptions();
                                    }
                                });
    }

    /**
     * Lists pre-subscription data.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     List<Presubscription> presubscriptions = connectApi.listPresubscriptions();
     *     for (Presubscription presub : presubscriptions) {
     *         System.out.println("Device (" + presub.getDeviceId() + ") has subscriptions to resources: ");
     *         for (String resource : presub.getResourcePaths()) {
     *             System.out.println(resource);
     *         }
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @return the list of pre-subscription data.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable List<Presubscription> listPresubscriptions() throws MbedCloudException {
        final GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("listPresubscriptions()");
        return (presubscriptionRegistry == null) ? null : presubscriptionRegistry.getEntries();
    }

    /**
     * Updates all pre-subscription data.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *
     *     Presubscription presub1 = new Presubscription();
     *     presub1.setDeviceType("default");
     *     List<String> resourceList1 = Arrays.asList("/3201/0/5850", "/3201/0/5853");
     *     presub1.setResourcePaths(resourceList1);
     *
     *     Presubscription presub2 = new Presubscription();
     *     presub2.setDeviceId(deviceId);
     *     List<String> resourceList2 = Arrays.asList("/3200/0/5501");
     *     presub2.setResourcePaths(resourceList2);
     *
     *     List<Presubscription> presubscriptions = Arrays.asList(presub1, presub2);
     *     connectApi.updatePresubscriptions(presubscriptions);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param presubscriptions
     *            The pre-subscription list to update.
     *            <p>
     *            If you send an empty/null array, the pre-subscription data will be removed @see
     *            {@link #deletePresubscriptions()} for similar action.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void updatePresubscriptions(@Nullable List<Presubscription> presubscriptions) throws MbedCloudException {
        final PresubscriptionArray array = PresubscriptionAdapter.reverseMapList(presubscriptions);
        CloudCaller.call(this, "updatePresubscriptions()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getSubscriptions().updatePreSubscriptions(array);
            }
        });
    }

    /**
     * Adds a pre-subscription.
     * <p>
     * Note: for more information about pre-subscriptions, have a look at @link {@link Presubscription} or
     * <a href="https://cloud.mbed.com/docs/current/connecting/presubscriptions.html">corresponding Mbed Cloud
     * documentation</a>
     *
     * @param presubscription
     *            pre-subscription to add
     * @throws MbedCloudException
     *             if an error happened during the process
     */
    @API
    public void addPresubscription(@Nullable Presubscription presubscription) throws MbedCloudException {
        if (presubscription == null) {
            return;
        }
        checkModelValidity(presubscription, TAG_PRESUBSCRIPTION);
        GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("addPresubscription()");
        if (presubscriptionRegistry != null && presubscriptionRegistry.contains(presubscription)) {
            return;
        }
        if (presubscriptionRegistry == null) {
            presubscriptionRegistry = new GenericAdapter.MappedObjectRegistry<>();
        }
        presubscriptionRegistry.addNewEnty(presubscription);
        updatePresubscriptions(presubscriptionRegistry.getEntries());
    }

    /**
     * Adds some pre-subscriptions.
     * <p>
     * Note: for more information about pre-subscriptions, have a look at @link {@link Presubscription} or
     * <a href="https://cloud.mbed.com/docs/current/connecting/presubscriptions.html">corresponding Mbed Cloud
     * documentation</a>
     *
     * @param presubscriptions
     *            pre-subscriptions to add
     * @throws MbedCloudException
     *             if an error happened during the process
     */
    @API
    public void addSomePresubscriptions(@Nullable List<Presubscription> presubscriptions) throws MbedCloudException {
        if (presubscriptions == null) {
            return;
        }
        GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("addSomePresubscriptions()");
        if (presubscriptionRegistry == null) {
            presubscriptionRegistry = new GenericAdapter.MappedObjectRegistry<>();
        }
        boolean requireAddition = false;
        for (final Presubscription presubscription : presubscriptions) {
            checkModelValidity(presubscription, TAG_PRESUBSCRIPTION);
            if (!presubscriptionRegistry.contains(presubscription)) {
                requireAddition = true;
                presubscriptionRegistry.addNewEnty(presubscription);
            }
        }
        if (requireAddition) {
            updatePresubscriptions(presubscriptionRegistry.getEntries());
        }
    }

    /**
     * Gets a pre-subscription.
     *
     * @param presubscriptionId
     *            pre-subscription id
     * @return corresponding pre-subscription
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    public @Nullable Presubscription getPresubscription(@Nullable String presubscriptionId) throws MbedCloudException {
        if (presubscriptionId == null) {
            return null;
        }
        final GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("getPresubscription()");
        return presubscriptionRegistry == null
               || presubscriptionRegistry.isEmpty() ? null : presubscriptionRegistry.getEntry(presubscriptionId);
    }

    /**
     * Deletes a pre-subscription.
     *
     * @param presubscriptionId
     *            pre-subscription id of the pre-subscription to delete
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    public void deletePresubscription(@Nullable String presubscriptionId) throws MbedCloudException {
        if (presubscriptionId == null) {
            return;
        }
        final GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("deletePresubscription()");
        if (presubscriptionRegistry == null || presubscriptionRegistry.isEmpty()
            || !presubscriptionRegistry.contains(presubscriptionId)) {
            return;
        }
        presubscriptionRegistry.removeEntry(presubscriptionId);
        updatePresubscriptions(presubscriptionRegistry.getEntries());
    }

    /**
     * Deletes a pre-subscription.
     *
     * @param presubscription
     *            pre-subscription to delete
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    public void deletePresubscription(@Nullable Presubscription presubscription) throws MbedCloudException {
        if (presubscription == null) {
            return;
        }
        deletePresubscription(presubscription.getId());
    }

    /**
     * Deletes some pre-subscriptions.
     *
     * @param presubscriptions
     *            pre-subscriptions to delete
     * @throws MbedCloudException
     *             if a problem occurred during the process.
     */
    @API
    public void deleteSomePresubscriptions(@Nullable List<Presubscription> presubscriptions) throws MbedCloudException {
        if (presubscriptions == null) {
            return;
        }
        final GenericAdapter.MappedObjectRegistry<Presubscription> presubscriptionRegistry = getCurrentPresubscriptionRegistry("deleteSomePresubscriptions()");

        if (presubscriptionRegistry == null || presubscriptionRegistry.isEmpty()) {
            return;
        }
        boolean requireDeletion = false;
        for (final Presubscription presubscription : presubscriptions) {
            if (presubscriptionRegistry.contains(presubscription.getId())) {
                requireDeletion = true;
            }
            presubscriptionRegistry.removeEntry(presubscription.getId());

        }
        if (requireDeletion) {
            updatePresubscriptions(presubscriptionRegistry.getEntries());
        }
    }

    /**
     * Deletes all pre-subscription data.
     * <p>
     * Note: this method will deregister all subscription callbacks or observers if any.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     connectApi.deletePresubscriptions();
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void deletePresubscriptions() throws MbedCloudException {
        CloudCaller.call(this, "deletePresubscriptions()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getSubscriptions().updatePreSubscriptions(PresubscriptionAdapter.reverseMapList(null));
            }
        });
        deregisterAllResourceSubscriptionObserversOrCallbacks();
    }

    /**
     * Removes all subscriptions.
     * <p>
     * Note: this method will deregister all subscription callbacks or observers if any.
     * <p>
     * Warning: Please note that this operation is potentially really expensive and hence, use wisely.
     * <p>
     * It could be slow for large numbers of connected devices. If possible, explicitly delete subscriptions known to
     * have been created.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     connectApi.deleteSubscriptions();
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void deleteSubscriptions() throws MbedCloudException {
        // The following is a workaround until there is a Mbed Cloud endpoint providing such an action.
        logger.logWarn("deleteSubscriptions() could be slow for large numbers of connected devices. "
                       + "If possible, explicitly delete subscriptions known to have been created.");
        final Paginator<Device> connectedDevices = listAllConnectedDevices(null);
        if (connectedDevices != null) {
            for (final Device connectedDevice : connectedDevices) {
                deleteDeviceSubscriptions(connectedDevice);
            }
        }
        // When such an endpoint is created, use some code similar to below.
        // CloudCaller.call(this, "deleteSubscriptions()", null, new CloudCall<Void>() {
        //
        // @Override
        // public Call<Void> call() {
        // return endpoint.getSubscriptions().v2SubscriptionsDelete();
        // }
        // });
        // deregisterAllResourceSubscriptionObserversOrCallbacks();
    }

    /**
     * Removes all subscriptions.
     * <p>
     * Note: use {@link #deleteSubscriptions()} instead.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     connectApi.deleteSubscribers();
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    @Deprecated
    public void deleteSubscribers() throws MbedCloudException {
        deleteSubscriptions();
    }

    /**
     * Lists all subscriptions.
     * <p>
     * Warning: Please note that this operation is potentially really expensive and hence, use wisely.
     * <p>
     * It could be slow for large numbers of connected devices.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *
     *     List<String> subscriptions = connectApi.listSubscriptions();
     *     for (Subscription subscription : subscriptions) {
     *         System.out.println("subscription: " + subscription);
     *     }
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @return list of subscriptions
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable List<Subscription> listSubscriptions() throws MbedCloudException {
        logger.logWarn("listSubscriptions() could be slow for large numbers of connected devices.");
        final List<Subscription> subscriptions = new LinkedList<>();
        // The following is a workaround until there is a Mbed Cloud endpoint providing such an action.
        final Paginator<Device> connectedDevices = listAllConnectedDevices(null);
        if (connectedDevices != null) {
            for (final Device connectedDevice : connectedDevices) {
                final List<String> deviceSubscriptions = listDeviceSubscriptions(connectedDevice);
                if (deviceSubscriptions != null) {
                    subscriptions.add(new Subscription(connectedDevice.getId(), deviceSubscriptions));
                }
            }
        }
        return subscriptions.isEmpty() ? null : subscriptions;
    }

    /**
     * Deletes a device's subscriptions.
     * <p>
     * Note: this method will deregister all subscription callbacks or observers for this device if any.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Device device = new Device();
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     device.setId(deviceId);
     *     connectApi.deleteDeviceSubscriptions(device);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param device
     *            Device to consider.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void deleteDeviceSubscriptions(@NonNull Device device) throws MbedCloudException {
        checkNotNull(device, TAG_DEVICE);
        checkNotNull(device.getId(), TAG_DEVICE_ID);
        final String finalDeviceId = device.getId();
        CloudCaller.call(this, "deleteDeviceSubscriptions()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getSubscriptions().deleteEndpointSubscriptions(finalDeviceId);
            }
        });
        deregisterAllResourceSubscriptionObserversOrCallbacks(device);
    }

    /**
     * Gets the status of a resource's subscription.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3200/0/5501";
     *     Resource buttonResource = new Resource(deviceId, resourcePath);
     *
     *     boolean subscribed = connectApi.getResourceSubscription(buttonResource);
     *     System.out.println("Is " + deviceId + " subscribed to: " + resourcePath + "? " + (subscribed ? "yes" : "no"));
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            resource
     * @return true if resource is subscribed. false otherwise.
     * @throws MbedCloudException
     *             if a parameter is incorrect
     *
     *
     */
    @API
    public boolean getResourceSubscription(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResource = resource;
        try {
            CloudCaller.call(this, "getResourceSubscription()", null, new CloudCall<Void>() {

                @Override
                public Call<Void> call() {
                    return endpoint.getSubscriptions()
                                   .checkResourceSubscription(finalResource.getDeviceId(),
                                                              ApiUtils.normalisePath(finalResource.getPath()));
                }
            }, true);
            return true;
        } catch (MbedCloudException exception) {
            return false;
        }
    }

    /**
     * Allows notifications (received from a Webhook) to be injected into the notifications system.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *       String deviceId = "015f4ac587f500000000000100100249";
     *       String resourcePath = "/3200/0/5501";
     *       String payload ="Q2hhbmdlIG1lIQ==";
     *
     *       NotificationData notification = new NotificationData();
     *       notification.setEp(deviceId);
     *       notification.setPath(resourcePath);
     *       notification.setPayload(payload);
     *       NotificationMessage notifications = new NotificationMessage();
     *       notifications.addNotificationsItem(notification);
     *       Resource resource = new Resource(deviceId, resourcePath);
     *       api.createResourceSubscriptionObserver(resource, BackpressureStrategy.BUFFER)
     *               .subscribe(new Consumer<Object>() {
     *
     *                   &#64;Override
     *                   public void accept(Object t) throws Exception {
     *                       log("Received notification value", t);
     *                   }
     *               });
     *       api.notify(notifications);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param data
     *            The notification data to inject
     */
    @API
    public void notify(@Nullable NotificationMessage data) {
        handlersStore.notify(data);
    }

    /**
     * Allows notifications expressed as a JSON string to be injected into the notifications system.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *       String deviceId = "015f4ac587f500000000000100100249";
     *       String resourcePath = "/3200/0/5501";
     *       String payload ="Q2hhbmdlIG1lIQ==";
     *       String notifications = "{\"async-responses\":[{\"payload\":\"MQ\u003d\u003d\",\"id\":\"sfjasldfjl\"}],\"notifications\""
     *       +":[{\"path\":\"/3200/0/5501\",\"payload\":\"MQ\u003d\u003d\",\"ep\":\"015f4ac587f500000000000100100249\"}]}";
     *
     *       Resource resource = new Resource(deviceId, resourcePath);
     *       api.createResourceSubscriptionObserver(resource, BackpressureStrategy.BUFFER)
     *               .subscribe(new Consumer<Object>() {
     *
     *                   &#64;Override
     *                   public void accept(Object t) throws Exception {
     *                       log("Received notification value", t);
     *                   }
     *               });
     *       api.notify(notifications);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param dataAsJson
     *            The notification data to inject as JSON String.
     */
    @API
    public void notify(@Nullable String dataAsJson) {
        notify(null, dataAsJson);
    }

    /**
     * Allows a notification to be injected into the notifications system.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *       String deviceId = "015f4ac587f500000000000100100249";
     *       String resourcePath = "/3200/0/5501";
     *       String payload ="Q2hhbmdlIG1lIQ==";
     *       JSONSerialiser jsonSerialiser = new JSONSerialiser();
     *       String notifications = "{\"async-responses\":[{\"payload\":\"MQ\u003d\u003d\",\"id\":\"sfjasldfjl\"}],\"notifications\""
     *       +":[{\"path\":\"/3200/0/5501\",\"payload\":\"MQ\u003d\u003d\",\"ep\":\"015f4ac587f500000000000100100249\"}]}";
     *
     *       Resource resource = new Resource(deviceId, resourcePath);
     *       api.createResourceSubscriptionObserver(resource, BackpressureStrategy.BUFFER)
     *               .subscribe(new Consumer<Object>() {
     *
     *                   &#64;Override
     *                   public void accept(Object t) throws Exception {
     *                       log("Received notification value", t);
     *                   }
     *               });
     *       api.notify(jsonSerialiser, notifications);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param deserialiser
     *            JSON deserialiser to use.
     * @param dataAsJson
     *            The notification data to inject as JSON String.
     */
    @API
    public void notify(@Nullable JsonSerialiser deserialiser, @Nullable String dataAsJson) {
        final JsonSerialiser jsonEngine = (deserialiser == null) ? new JsonSerialiser() : deserialiser;
        notify(jsonEngine.fromJson(dataAsJson, NotificationMessage.class));
    }

    /**
     * Subscribes to a list of resources.
     *
     *
     * @param resources
     *            resources to subscribe to.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void addResourcesSubscription(@Nullable List<Resource> resources) throws MbedCloudException {
        if (resources == null) {
            return;
        }
        for (final Resource resource : resources) {
            addResourceSubscription(resource);
        }
    }

    /**
     * Subscribes to a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3200/0/5501";
     *     Resource buttonResource = new Resource(deviceId, resourcePath);
     *
     *     connectApi.addResourceSubscription(buttonResource);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            resource to subscribe to.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void addResourceSubscription(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResource = resource;
        CloudCaller.call(this, "addResourceSubscription()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getSubscriptions()
                               .addResourceSubscription(finalResource.getDeviceId(),
                                                        ApiUtils.normalisePath(finalResource.getPath()));
            }
        });
    }

    /**
     * Subscribes to a resource and associates callbacks.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3200/0/5501";
     *     Resource buttonResource = new Resource(deviceId, resourcePath);
     *     Callback<Object> callback = new Callback<Object>() {
     *
     *         &#64;Override
     *         public void execute(Object arg) {
     *             System.out.println("Just received a notification from " + device.getId() + " regarding " + path
     *                     + ": " + String.valueOf(arg));
     *
     *         }
     *     };
     *     connectApi.addResourceSubscription(resource,callback, null);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            resource to subscribe to.
     * @param onNotification
     *            callback to execute on notification.
     * @param onFailure
     *            callback to execute on error.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void addResourceSubscription(@NonNull Resource resource, @NonNull Callback<Object> onNotification,
                                        @Nullable Callback<Throwable> onFailure) throws MbedCloudException {
        registerResourceSubscriptionCallback(resource, onNotification, onFailure);
        addResourceSubscription(resource);
    }

    /**
     * Subscribes to a resource and creates a related observer.
     * <p>
     * Note: for more information about observers @see <a href="http://reactivex.io/">Reactive X</a> or
     * <a href="https://github.com/ReactiveX/RxJava">RxJava</a> *
     * <p>
     * Example:
     *
     * <pre>
     * {
     * {@code String resourcePath = "/3200/0/5501";
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     Resource resource = new Resource(deviceId, path);
     *     connectApi.addResourceSubscription(resource, BackpressureStrategy.BUFFER)
     *                   .subscribe(System.out::println);
     * }
     * </pre>
     *
     * @param resource
     *            resource to subscribe to.
     * @param strategy
     *            backpressure strategy to apply @see {@link BackpressureStrategy}
     * @return Observable which can be subscribed to. @see {@link Flowable}
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public @Nullable Flowable<NotificationMessageValue>
           addResourceSubscription(@NonNull Resource resource,
                                   @Nullable @DefaultValue(BUFFER) BackpressureStrategy strategy) throws MbedCloudException {
        try {
            final Flowable<NotificationMessageValue> observer = createResourceSubscriptionObserver(resource, strategy);
            addResourceSubscription(resource);
            return observer;
        } catch (MbedCloudException exception) {
            handlersStore.removeResourceSubscriptionObserver(resource);
            throw exception;
        }

    }

    /**
     * Registers a subscription callback for a resource.
     * <p>
     * Example:
     *
     * <pre>
     * {
     * {@code String resourcePath = "/3200/0/5501";
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     Resource resource = new Resource(deviceId, path);
     *     Callback<Object> callback = new Callback<Object>() {
     *
     *         &#64;Override
     *         public void execute(Object arg) {
     *             System.out.println("Just received a notification from " + device.getId() + " regarding " + path
     *                     + ": " + String.valueOf(arg));
     *
     *         }
     *     };
     *     connectApi.registerResourceSubscriptionCallback(resource, callback, null);
     *     connectApi.addResourceSubscription(resource);
     * }
     * </pre>
     *
     * @param resource
     *            resource to register the callback for.
     * @param onNotification
     *            callback to execute on notification.
     * @param onFailure
     *            callback to execute on error.
     * @throws MbedCloudException
     *             if an error occurred in the process.
     */
    @API
    public void
           registerResourceSubscriptionCallback(@NonNull Resource resource, @NonNull Callback<Object> onNotification,
                                                @Nullable Callback<Throwable> onFailure) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        checkNotNull(onNotification, TAG_ON_NOTIFICATION_CALLBACK);
        handlersStore.registerSubscriptionCallback(resource, onNotification, onFailure);
    }

    /**
     * Deregisters the subscription callback of a resource.
     * <p>
     * Example:
     *
     * <pre>
     *
     * {
     *     &#64;code
     *     String resourcePath = "/3200/0/5501";
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     Resource resource = new Resource(deviceId, path);
     *     connectApi.deregisterResourceSubscriptionCallback(resource);
     * }
     * </pre>
     *
     * @param resource
     *            resource to consider.
     * @throws MbedCloudException
     *             if an error occurred in the process.
     */
    @API
    public void deregisterResourceSubscriptionCallback(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        handlersStore.deregisterNotificationSubscriptionCallback(resource);
    }

    /**
     * Creates an observer for resource subscriptions.
     * <p>
     * Note: for more information about observers @see <a href="http://reactivex.io/">Reactive X</a> or
     * <a href="https://github.com/ReactiveX/RxJava">RxJava</a> *
     * <p>
     * Example:
     *
     * <pre>
     * {
     * {@code String resourcePath = "/3200/0/5501";
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     Resource resource = new Resource(deviceId, path);
     *     connectApi.createResourceSubscriptionObserver(resource, BackpressureStrategy.BUFFER)
     *                   .subscribe(System.out::println);
     *     connectApi.addResourceSubscription(resource);
     * }
     * </pre>
     *
     * @param resource
     *            resource to subscribe to.
     * @param strategy
     *            backpressure strategy to apply @see {@link BackpressureStrategy}
     * @return Observable which can be subscribed to. @see {@link Flowable}
     * @throws MbedCloudException
     *             if an error occurred in the process.
     */
    @API
    public @Nullable Flowable<NotificationMessageValue>
           createResourceSubscriptionObserver(@NonNull Resource resource,
                                              @Nullable @DefaultValue(BUFFER) BackpressureStrategy strategy) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final BackpressureStrategy finalStrategy = (strategy == null) ? BackpressureStrategy.BUFFER : strategy;
        return handlersStore.createResourceSubscriptionObserver(resource, finalStrategy);
    }

    /**
     * Removes the subscription observer of a resource.
     * <p>
     * Example:
     *
     * <pre>
     *
     * {@code String resourcePath = "/3200/0/5501"
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     Resource resource = new Resource(deviceId, path);
     *     connectApi.removeResourceSubscriptionObserver(resource);
     * }
     * </pre>
     *
     * @param resource
     *            resource to consider.
     * @throws MbedCloudException
     *             if an error occurred in the process.
     */
    @API
    public void removeResourceSubscriptionObserver(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        handlersStore.removeResourceSubscriptionObserver(resource);
    }

    /**
     * Deregisters all subscription observers or callbacks for a device.
     * <p>
     * Example:
     *
     * <pre>
     *
     * {@code String deviceId = "015f4ac587f500000000000100100249"
     *     Device device = new Device();
     *     device.setId(deviceId);
     *     connectApi.deregisterAllResourceSubscriptionObserversOrCallbacks(device);
     * }
     * </pre>
     *
     * @param device
     *            device to consider.
     * @throws MbedCloudException
     *             if an error occurred in the process.
     */
    @API
    public void
           deregisterAllResourceSubscriptionObserversOrCallbacks(@NonNull Device device) throws MbedCloudException {
        checkNotNull(device, TAG_DEVICE);
        checkNotNull(device.getId(), TAG_DEVICE_ID);
        handlersStore.deregisterAllResourceSubscriptionObserversOrCallbacks(device.getId());
    }

    /**
     * Deregisters all subscription observers or callbacks.
     * <p>
     * Example:
     *
     * <pre>
     *
     * {@code
     *     connectApi.deregisterAllResourceSubscriptionObserversOrCallbacks();
     * }
     * </pre>
     */
    @API
    public void deregisterAllResourceSubscriptionObserversOrCallbacks() {
        handlersStore.deregisterAllResourceSubscriptionObserversOrCallbacks();
    }

    /**
     * Deletes a resource's subscription.
     * <p>
     * Note: this method will deregister all subscription callbacks or observers for this resource if any.
     * <p>
     *
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     String deviceId = "015f4ac587f500000000000100100249";
     *     String resourcePath = "/3200/0/5501";
     *     Resource buttonResource = new Resource(deviceId, resourcePath);
     *
     *     connectApi.deleteResourceSubscription(buttonResource);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param resource
     *            resource to subscribe to.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void deleteResourceSubscription(@NonNull Resource resource) throws MbedCloudException {
        checkNotNull(resource, TAG_RESOURCE);
        checkModelValidity(resource, TAG_RESOURCE);
        final Resource finalResource = resource;
        CloudCaller.call(this, "deleteResourceSubscription()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getSubscriptions()
                               .deleteResourceSubscription(finalResource.getDeviceId(),
                                                           ApiUtils.normalisePath(finalResource.getPath()));
            }
        });
        deregisterResourceSubscriptionCallback(finalResource);
        removeResourceSubscriptionObserver(finalResource);
    }

    /**
     * Gets the current callback URL if it exists.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Webhook webhook = connectApi.getWebhook();
     *     System.out.println("Webhook URL: " + webhook.getUrl());
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @return the webhook.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public Webhook getWebhook() throws MbedCloudException {
        return CloudCaller.call(this, "getWebhook()", WebhookAdapter.getMapper(),
                                new CloudCall<com.arm.mbed.cloud.sdk.internal.mds.model.Webhook>() {

                                    @Override
                                    public Call<com.arm.mbed.cloud.sdk.internal.mds.model.Webhook> call() {
                                        return endpoint.getNotifications().getWebhook();
                                    }
                                });
    }

    /**
     * Registers new webhook for incoming subscriptions.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     Webhook webhook = new Webhook();
     *     webhook.setUrl("https://goo.gl/testwh");
     *     webhook.addHeader("Auth","token");
     *     connectApi.updateWebhook(webhook);
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @param webhook
     *            Webhook to set.
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void updateWebhook(@NonNull Webhook webhook) throws MbedCloudException {
        checkNotNull(webhook, TAG_WEBHOOK);
        checkModelValidity(webhook, TAG_WEBHOOK);
        if (isForceClear()) {
            stopNotifications();
        }
        final Webhook finalWebhook = webhook;
        CloudCaller.call(this, "updateWebhook()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getNotifications().registerWebhook(WebhookAdapter.reverseMap(finalWebhook));
            }
        });
    }

    /**
     * Deletes the callback data (effectively stopping Arm Mbed Cloud Connect from putting notifications).
     * <p>
     * If no webhook is registered, an exception (404) will be raised.
     * <p>
     * Note that every registered subscription will be deleted as part of deregistering a webhook.
     * <p>
     * Example:
     *
     * <pre>
     * {@code
     * try {
     *     connectApi.deleteWebhook();
     * } catch (MbedCloudException e) {
     *     e.printStackTrace();
     * }
     * }
     * </pre>
     *
     * @throws MbedCloudException
     *             if a problem occurred during request processing.
     */
    @API
    public void deleteWebhook() throws MbedCloudException {
        CloudCaller.call(this, "deleteWebhook()", null, new CloudCall<Void>() {

            @Override
            public Call<Void> call() {
                return endpoint.getNotifications().deregisterWebhook();
            }
        });
    }

    /**
     * States whether any existing notification channel should be cleared before a new one is created.
     *
     * @return True if the channel will be cleared. False otherwise.
     */
    public boolean isForceClear() {
        return endpoint.isForceClear();
    }

    /**
     * Sets whether any existing notification channel should be cleared before a new one is created.
     *
     * @param forceClear
     *            True if the channel will be cleared. False otherwise.
     */
    public void setForceClear(boolean forceClear) {
        endpoint.setForceClear(forceClear);
    }

    /**
     * States whether notification daemon will start automatically when needed.
     * <p>
     * Note: to change this behaviour, use {@link ConnectionOptions#setAutostartDaemon(boolean)} when initialising this
     * API.
     *
     * @return true if daemon will be started automatically. False otherwise.
     */
    public boolean isAutostartDaemon() {
        return endpoint.isAutostartDaemon();
    }

    /**
     * Retrieves module name.
     *
     * @return module name.
     */
    @Override
    public String getModuleName() {
        return "Connect";
    }
}
