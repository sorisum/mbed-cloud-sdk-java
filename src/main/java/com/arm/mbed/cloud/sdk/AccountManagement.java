package com.arm.mbed.cloud.sdk;

import java.io.IOException;

import com.arm.mbed.cloud.sdk.accountmanagement.adapters.AccountAdapter;
import com.arm.mbed.cloud.sdk.accountmanagement.adapters.ApiKeyAdapter;
import com.arm.mbed.cloud.sdk.accountmanagement.adapters.GroupAdapter;
import com.arm.mbed.cloud.sdk.accountmanagement.adapters.UserAdapter;
import com.arm.mbed.cloud.sdk.accountmanagement.model.Account;
import com.arm.mbed.cloud.sdk.accountmanagement.model.ApiKey;
import com.arm.mbed.cloud.sdk.accountmanagement.model.ApiKeyListOptions;
import com.arm.mbed.cloud.sdk.accountmanagement.model.EndPoints;
import com.arm.mbed.cloud.sdk.accountmanagement.model.Group;
import com.arm.mbed.cloud.sdk.accountmanagement.model.User;
import com.arm.mbed.cloud.sdk.accountmanagement.model.UserListOptions;
import com.arm.mbed.cloud.sdk.annotations.API;
import com.arm.mbed.cloud.sdk.annotations.Module;
import com.arm.mbed.cloud.sdk.annotations.NonNull;
import com.arm.mbed.cloud.sdk.annotations.Nullable;
import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.AbstractAPI;
import com.arm.mbed.cloud.sdk.common.ApiUtils;
import com.arm.mbed.cloud.sdk.common.ConnectionOptions;
import com.arm.mbed.cloud.sdk.common.ListOptions;
import com.arm.mbed.cloud.sdk.common.ListResponse;
import com.arm.mbed.cloud.sdk.common.MbedCloudException;
import com.arm.mbed.cloud.sdk.common.PageRequester;
import com.arm.mbed.cloud.sdk.common.Paginator;
import com.arm.mbed.cloud.sdk.internal.model.AccountInfo;
import com.arm.mbed.cloud.sdk.internal.model.ApiKeyInfoResp;
import com.arm.mbed.cloud.sdk.internal.model.ApiKeyInfoRespList;
import com.arm.mbed.cloud.sdk.internal.model.GroupSummary;
import com.arm.mbed.cloud.sdk.internal.model.GroupSummaryList;
import com.arm.mbed.cloud.sdk.internal.model.UserInfoResp;
import com.arm.mbed.cloud.sdk.internal.model.UserInfoRespList;

import retrofit2.Response;

@Preamble(description = "Specifies account management API")
@Module
/**
 * API exposing functionality for creating and managing accounts, users, groups and API keys in the organisation.
 */
public class AccountManagement extends AbstractAPI {

    private static final String TAG_API_KEY = "apiKey";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_API_KEY_ID = "apiKeyId";
    private static final String TAG_USER_ID = "userId";
    private static final String TAG_USER = "user";
    private static final String TAG_GROUP_ID = "groupId";
    private final EndPoints endpoint;

    /**
     * 
     * @param options
     *            connection options
     */
    public AccountManagement(ConnectionOptions options) {
        super(options);
        endpoint = new EndPoints(this.client);
    }

    /**
     * Gets details of account associated with current API key
     * 
     * @return account details
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Account getAccount() throws MbedCloudException {
        try {
            Response<AccountInfo> response = endpoint.getDeveloper().getMyAccountInfo("limits, policies").execute();
            return (response == null) ? null : AccountAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Updates details of account associated with current API key
     * 
     * @param account
     *            The account object to update
     * @return updated account
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Account updateAccount(@NonNull Account account) throws MbedCloudException {
        ApiUtils.checkNotNull(account, TAG_ACCOUNT);
        try {
            Response<AccountInfo> response = endpoint.getAdmin().updateMyAccount(AccountAdapter.reverseMap(account))
                    .execute();
            return (response == null) ? null : AccountAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Lists all API keys according to filter options
     * 
     * @param options
     *            filter options
     * @return The list of API keys corresponding to filter options (One page)
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ListResponse<ApiKey> listApiKeys(@Nullable ApiKeyListOptions options) throws MbedCloudException {
        final ApiKeyListOptions finalOptions = (options == null) ? new ApiKeyListOptions() : options;
        try {
            Response<ApiKeyInfoRespList> response = this.endpoint.getDeveloper()
                    .getAllApiKeys(finalOptions.getLimit(), finalOptions.getAfter(), finalOptions.getOrder().toString(),
                            finalOptions.encodeInclude(), finalOptions.encodeFilter(ApiKeyListOptions.OWNER_ID_FILTER))
                    .execute();
            return (response == null) ? null : ApiKeyAdapter.mapList(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Gets an iterator over all API keys according to filter options
     * 
     * @param options
     *            filter options
     * @return paginator for the list of API keys corresponding to filter options
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Paginator<ApiKey> listAllApiKeys(@Nullable ApiKeyListOptions options) throws MbedCloudException {
        final ApiKeyListOptions finalOptions = options;
        return new Paginator<>(new PageRequester<ApiKey>() {

            @Override
            public ListResponse<ApiKey> requestNewPage() throws MbedCloudException {
                return listApiKeys(finalOptions);
            }
        });
    }

    /**
     * Get details of an API key
     * 
     * @param apiKeyId
     *            The API key ID (if not specified, returns current API key)
     * @return the API key
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ApiKey getApiKey(@NonNull String apiKeyId) throws MbedCloudException {
        ApiUtils.checkNotNull(apiKeyId, TAG_API_KEY_ID);
        try {
            Response<ApiKeyInfoResp> response = (apiKeyId == null || apiKeyId.isEmpty())
                    ? endpoint.getDeveloper().getMyApiKey().execute()
                    : endpoint.getDeveloper().getApiKey(apiKeyId).execute();
            return (response == null) ? null : ApiKeyAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Adds an API key
     * 
     * @param apiKey
     *            The API key to add
     * @return added API Key
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @NonNull ApiKey addApiKey(@NonNull ApiKey apiKey) throws MbedCloudException {
        ApiUtils.checkNotNull(apiKey, TAG_API_KEY);
        try {
            Response<ApiKeyInfoResp> response = endpoint.getDeveloper().createApiKey(ApiKeyAdapter.addMap(apiKey))
                    .execute();
            return (response == null) ? null : ApiKeyAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return apiKey;
    }

    /**
     * Updates an API key
     * 
     * @param apiKey
     *            The API key to update
     * @return updated API key
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ApiKey updateApiKey(@NonNull ApiKey apiKey) throws MbedCloudException {
        ApiUtils.checkNotNull(apiKey, TAG_USER);
        ApiUtils.checkNotNull(apiKey.getId(), "apiKey UUID");
        try {
            Response<ApiKeyInfoResp> response = endpoint.getDeveloper()
                    .updateApiKey(apiKey.getId(), ApiKeyAdapter.updateMap(apiKey)).execute();
            return (response == null) ? null : ApiKeyAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Deletes an API key
     * 
     * @param apiKeyId
     *            The API key ID
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public void deleteApiKey(@NonNull String apiKeyId) throws MbedCloudException {
        ApiUtils.checkNotNull(apiKeyId, TAG_API_KEY_ID);
        try {
            this.endpoint.getDeveloper().deleteApiKey(apiKeyId).execute();
        } catch (IOException e) {
            throwSDKException(e);
        }
    }

    /**
     * Lists users according to filter options
     * 
     * @param options
     *            filter options
     * @return list of users (One page)
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ListResponse<User> listUsers(@Nullable UserListOptions options) throws MbedCloudException {
        final UserListOptions finalOptions = (options == null) ? new UserListOptions() : options;
        try {
            Response<UserInfoRespList> response = this.endpoint.getAdmin()
                    .getAllUsers(finalOptions.getLimit(), finalOptions.getAfter(), finalOptions.getOrder().toString(),
                            finalOptions.encodeInclude(), finalOptions.encodeFilter(UserListOptions.STATUS_FILTER))
                    .execute();
            return (response == null) ? null : UserAdapter.mapList(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Gets an iterator over all users according to filter options
     * 
     * @param options
     *            filter options
     * @return paginator for the list of users corresponding to filter options
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Paginator<User> listAllApiKeys(@Nullable UserListOptions options) throws MbedCloudException {
        final UserListOptions finalOptions = options;
        return new Paginator<>(new PageRequester<User>() {

            @Override
            public ListResponse<User> requestNewPage() throws MbedCloudException {
                return listUsers(finalOptions);
            }
        });
    }

    /**
     * Gets details about a user
     * 
     * @param userId
     *            The user ID
     * @return a user
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable User getUser(@NonNull String userId) throws MbedCloudException {
        ApiUtils.checkNotNull(userId, TAG_USER_ID);
        try {
            Response<UserInfoResp> response = endpoint.getAdmin().getUser(userId).execute();
            return (response == null) ? null : UserAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Adds a user
     * 
     * @param user
     *            User to add
     * @return added user
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @NonNull User addUser(@NonNull User user) throws MbedCloudException {
        ApiUtils.checkNotNull(user, TAG_USER);
        try {
            Response<UserInfoResp> response = endpoint.getAdmin().createUser(UserAdapter.addMap(user), "create")
                    .execute();
            return (response == null) ? null : UserAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return user;
    }

    /**
     * Updates a user
     * 
     * @param user
     *            User to update
     * @return updated user
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable User updateUser(@NonNull User user) throws MbedCloudException {
        ApiUtils.checkNotNull(user, TAG_USER);
        ApiUtils.checkNotNull(user.getId(), "user UUID");
        try {
            Response<UserInfoResp> response = endpoint.getAdmin().updateUser(user.getId(), UserAdapter.updateMap(user))
                    .execute();
            return (response == null) ? null : UserAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Deletes a user
     * 
     * @param userId
     *            The user ID of the user to delete
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public void deleteUser(@NonNull String userId) throws MbedCloudException {
        ApiUtils.checkNotNull(userId, TAG_USER_ID);
        try {
            endpoint.getAdmin().deleteUser(userId).execute();
        } catch (IOException e) {
            throwSDKException(e);
        }
    }

    /**
     * Lists available groups depending on filter options
     * 
     * @param options
     *            filter options
     * @return The list of groups corresponding to filter options (One page)
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ListResponse<Group> listGroups(@Nullable ListOptions options) throws MbedCloudException {
        final ListOptions finalOptions = (options == null) ? new ListOptions() : options;
        try {
            Response<GroupSummaryList> response = endpoint.getDeveloper().getAllGroups(finalOptions.getLimit(),
                    finalOptions.getAfter(), finalOptions.getOrder().toString(), finalOptions.encodeInclude())
                    .execute();
            return (response == null) ? null : GroupAdapter.mapList(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Gets an iterator over all available groups depending on filter options
     * 
     * @param options
     *            filter options
     * @return paginator for the groups corresponding to filter options
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Paginator<Group> listAllGroups(@Nullable ListOptions options) throws MbedCloudException {
        final ListOptions finalOptions = options;
        return new Paginator<>(new PageRequester<Group>() {

            @Override
            public ListResponse<Group> requestNewPage() throws MbedCloudException {
                return listGroups(finalOptions);
            }
        });
    }

    /**
     * Get details of a group
     * 
     * @param groupId
     *            The group ID to look for
     * @return corresponding group
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Group getGroup(@NonNull String groupId) throws MbedCloudException {
        ApiUtils.checkNotNull(groupId, TAG_GROUP_ID);
        try {
            Response<GroupSummary> response = endpoint.getDeveloper().getGroupSummary(groupId).execute();
            return (response == null) ? null : GroupAdapter.map(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Lists users of a group
     *
     * @param groupId
     *            The group ID
     * @param options
     *            filter options
     * @return The list of users corresponding to groupId and filter options (One page)
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ListResponse<User> listGroupUsers(@NonNull String groupId, @Nullable ListOptions options)
            throws MbedCloudException {
        ApiUtils.checkNotNull(groupId, TAG_GROUP_ID);
        final ListOptions finalOptions = (options == null) ? new ListOptions() : options;
        try {
            Response<UserInfoRespList> response = endpoint.getAdmin().getUsersOfGroup(groupId, finalOptions.getLimit(),
                    finalOptions.getAfter(), finalOptions.getOrder().toString(), finalOptions.encodeInclude())
                    .execute();
            return (response == null) ? null : UserAdapter.mapList(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Gets an iterator over all users of a group
     *
     * @param groupId
     *            The group ID of the group
     * @param options
     *            filter options
     * @return paginator for the list of users corresponding to groupId and filter options
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Paginator<User> listAllGroupUsers(@NonNull String groupId, @Nullable ListOptions options)
            throws MbedCloudException {
        final ListOptions finalOptions = options;
        final String finalGroupId = groupId;
        return new Paginator<>(new PageRequester<User>() {

            @Override
            public ListResponse<User> requestNewPage() throws MbedCloudException {
                return listGroupUsers(finalGroupId, finalOptions);
            }
        });
    }

    /**
     * Lists API keys of a group
     * 
     * @param groupId
     *            The group ID of the group
     * @param options
     *            filter options
     * @return The list of API keys corresponding to groupId and filter options (One page)
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable ListResponse<ApiKey> listGroupApiKeys(@NonNull String groupId, @Nullable ListOptions options)
            throws MbedCloudException {
        ApiUtils.checkNotNull(groupId, TAG_GROUP_ID);
        final ListOptions finalOptions = (options == null) ? new ListOptions() : options;
        try {
            Response<ApiKeyInfoRespList> response = endpoint.getDeveloper()
                    .getApiKeysOfGroup(groupId, finalOptions.getLimit(), finalOptions.getAfter(),
                            finalOptions.getOrder().toString(), finalOptions.encodeInclude())
                    .execute();
            return (response == null) ? null : ApiKeyAdapter.mapList(response.body());
        } catch (IOException e) {
            throwSDKException(e);
        }
        return null;
    }

    /**
     * Gets an iterator over all API keys of a group
     *
     * @param groupId
     *            The group ID of the group
     * @param options
     *            filter options
     * @return paginator for the list of API keys corresponding to groupId and filter options
     * @throws MbedCloudException
     *             if a problem occurred during request processing
     */
    @API
    public @Nullable Paginator<ApiKey> listAllGroupApiKeys(@NonNull String groupId, @Nullable ListOptions options)
            throws MbedCloudException {
        final ListOptions finalOptions = options;
        final String finalGroupId = groupId;
        return new Paginator<>(new PageRequester<ApiKey>() {

            @Override
            public ListResponse<ApiKey> requestNewPage() throws MbedCloudException {
                return listGroupApiKeys(finalGroupId, finalOptions);
            }
        });
    }

}
