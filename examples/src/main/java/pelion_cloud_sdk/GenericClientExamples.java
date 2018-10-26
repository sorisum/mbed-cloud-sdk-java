package pelion_cloud_sdk;

import static org.junit.Assert.fail;

import java.util.List;

import com.arm.mbed.cloud.sdk.accountmanagement.model.User;
import com.arm.mbed.cloud.sdk.common.CloudRequest;
import com.arm.mbed.cloud.sdk.common.CloudRequest.CloudCall;
import com.arm.mbed.cloud.sdk.common.GenericClient;
import com.arm.mbed.cloud.sdk.common.MbedCloudException;
import com.arm.mbed.cloud.sdk.common.listing.ListOptions;
import com.arm.mbed.cloud.sdk.common.listing.ListResponse;
import com.arm.mbed.cloud.sdk.common.listing.Paginator;

import retrofit2.Call;
import retrofit2.http.GET;
import utils.AbstractExample;
import utils.Configuration;
import utils.Example;

public class GenericClientExamples extends AbstractExample {

    /**
     * Uses the generic client to list users.
     */

    @Example
    public void useTheGenericClient() {
        // example: custom api call
        // Create a generic client
        GenericClient client = GenericClient.newClient(Configuration.get());
        // Define how to generate the request based on a set of parameters
        CloudRequest<UserListResponse, PelionApi> requestDefinition = new CloudRequest<UserListResponse, PelionApi>() {

            @Override
            public com.arm.mbed.cloud.sdk.common.CloudRequest.CloudCall<UserListResponse>
                   defineCall(PelionApi service, Object... parameters) throws MbedCloudException {

                return new CloudCall<GenericClientExamples.UserListResponse>() {

                    @Override
                    public Call<UserListResponse> call() {
                        return service.listAllUsers((Integer) parameters[0]);
                    }

                };
            }

        };
        // cloak
        try {

            @SuppressWarnings("boxing")
            // uncloak
            // Make the call with the following set of parameters. here, limit = 2.
            UserListResponse response = client.callApi(requestDefinition, 2);
            for (User user : response.getData()) {
                // Do something with users
                System.out.println(user);
            }
            // cloak
        } catch (Exception e) {
            // Logs information about the last API call.
            logError("last API Metadata", client.getLastApiMetadata());
            fail(e.getMessage());
        }
        // uncloak
    }

    /**
     * Definition of the REST endpoint.
     * <p>
     * See the documentation from <a href="https://square.github.io/retrofit/">Retrofit</a> to see how to defined a
     * service.
     */
    private interface PelionApi {
        @GET("v3/users")
        Call<UserListResponse> listAllUsers(@retrofit2.http.Query("limit") Integer limit);
    }

    /**
     * Definition of the response object.
     */
    private static class UserListResponse {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        List<User> data;

        public List<User> getData() {
            return data;
        }

        public void setData(List<User> data) {
            this.data = data;
        }

    }
    // end of example

    /**
     * Uses the generic client to list users and pagination.
     */

    @Example
    public void useGenericClientWithPagination() {
        // example: custom paginated api call
        // Create a generic client
        GenericClient client = GenericClient.newClient(Configuration.get());
        // Define how to generate the request based on a set of parameters
        CloudRequest.CloudListRequest<User, PelionListApi,
                                      ListOptions> requestDefinition = new CloudRequest.CloudListRequest<User,
                                                                                                         PelionListApi, ListOptions>() {

                                          @Override
                                          public CloudCall<ListResponse<User>>
                                                 defineCall(PelionListApi service, ListOptions options,
                                                            Object... extraParameters) throws MbedCloudException {
                                              return new CloudCall<ListResponse<User>>() {

                                                  @Override
                                                  public Call<ListResponse<User>> call() {
                                                      return service.listAllUsers(options.getPageSize());
                                                  }
                                              };
                                          }

                                      };
        // cloak
        try {
            // uncloak
            // Make the call with the following set of parameters. here, pageSize = 2.
            Paginator<User> paginatedResponse = client.callPaginatedApi(requestDefinition,
                                                                        new ListOptions().pageSize(2));
            // Do something with users
            paginatedResponse.forEach(System.out::println);
            // cloak
        } catch (Exception e) {
            // Logs information about the last API call.
            logError("last API Metadata", client.getLastApiMetadata());
            fail(e.getMessage());
        }
        // uncloak
    }

    /**
     * Definition of the REST endpoint.
     * <p>
     * See the documentation from <a href="https://square.github.io/retrofit/">Retrofit</a> to see how to defined a
     * service.
     * <p>
     * Note: the response object has to be an page ie {@link ListResponse}.
     */
    private interface PelionListApi {
        @GET("v3/users")
        Call<ListResponse<User>> listAllUsers(@retrofit2.http.Query("limit") Integer limit);
    }

    // end of example
}
