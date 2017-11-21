package mbed_cloud_sdk;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import com.arm.mbed.cloud.sdk.DeviceDirectory;
import com.arm.mbed.cloud.sdk.Update;
import com.arm.mbed.cloud.sdk.common.ConnectionOptions;
import com.arm.mbed.cloud.sdk.common.MbedCloudException;
import com.arm.mbed.cloud.sdk.common.Order;
import com.arm.mbed.cloud.sdk.common.listing.ListResponse;
import com.arm.mbed.cloud.sdk.common.listing.Paginator;
import com.arm.mbed.cloud.sdk.devicedirectory.model.Query;
import com.arm.mbed.cloud.sdk.devicedirectory.model.QueryListOptions;
import com.arm.mbed.cloud.sdk.update.model.Campaign;
import com.arm.mbed.cloud.sdk.update.model.CampaignDeviceState;
import com.arm.mbed.cloud.sdk.update.model.CampaignDevicesStatesListOptions;
import com.arm.mbed.cloud.sdk.update.model.CampaignListOptions;
import com.arm.mbed.cloud.sdk.update.model.FirmwareImage;
import com.arm.mbed.cloud.sdk.update.model.FirmwareImageListOptions;
import com.arm.mbed.cloud.sdk.update.model.FirmwareManifest;
import com.arm.mbed.cloud.sdk.update.model.FirmwareManifestListOptions;

import utils.AbstractExample;
import utils.Configuration;
import utils.Example;

public class UpdateExamples extends AbstractExample {
    /**
     * Lists the first 5 update campaigns.
     */
    @SuppressWarnings("boxing")
    @Example
    public void manageCampaigns() {
        ConnectionOptions config = Configuration.get();
        Update api = new Update(config);
        DeviceDirectory api2 = new DeviceDirectory(config);
        String campaignId = null;
        try {
            // Getting a manifest file.
            FirmwareManifestListOptions options = new FirmwareManifestListOptions();
            options.setLimit(1);
            FirmwareManifest manifest = null;
            Paginator<FirmwareManifest> manifests = api.listAllFirmwareManifests(options);
            if (!manifests.hasNext()) {
                fail("Could not get any manifest files from Mbed Cloud");
            }
            manifest = manifests.next();
            log("Campaign manifest", manifest);
            // Getting a query.
            QueryListOptions options2 = new QueryListOptions();
            options.setLimit(1);
            options.setOrder(Order.DESC);
            Query query = null;
            Paginator<Query> queries = api2.listAllQueries(options2);
            if (!queries.hasNext()) {
                fail("Could not fetch any queries from Mbed Cloud");
            }
            query = queries.next();
            log("Campaign query", query);
            // Creating an update campaign.
            String campaignName = "campaign-" + UUID.randomUUID().toString();
            log("Campaign name", campaignName);
            Campaign myCampaign = new Campaign(campaignName, manifest, query);
            myCampaign.setScheduledAt(new Date());
            myCampaign = api.addCampaign(myCampaign);
            log("Created campaign", myCampaign);
            campaignId = myCampaign.getId();
            // Updating campaign.
            campaignName = "campaign-" + UUID.randomUUID().toString();
            log("Updated campaign name", campaignName);
            myCampaign.setName(campaignName);
            myCampaign = api.updateCampaign(myCampaign);
            log("Updated campaign", myCampaign);
            campaignId = myCampaign.getId();
            // Starting the campaign.
            myCampaign = api.startCampaign(myCampaign);
            log("Started campaign", myCampaign);
            campaignId = myCampaign.getId();
            // Printing device states
            CampaignDevicesStatesListOptions deviceStateOptions = new CampaignDevicesStatesListOptions();
            deviceStateOptions.setLimit(20);
            for (int countdown = 10; countdown >= 0; countdown--) {
                log("Device states at countdown", countdown);
                Paginator<CampaignDeviceState> states = api.listAllCampaignDeviceStates(campaignId, deviceStateOptions);
                while (states.hasNext()) {
                    CampaignDeviceState state = states.next();
                    log("Device " + state.getDeviceId() + " state", state.getState());
                }
            }
            // Stopping campaign.
            myCampaign = api.stopCampaign(myCampaign);
            log("Stopped campaign", myCampaign);
            campaignId = myCampaign.getId();
            // Deleting campaign.
            deleteCreatedCampaign(campaignId, api);
        } catch (Exception e) {
            logError("last API Metadata", api.getLastApiMetadata());
            deleteCreatedCampaign(campaignId, api);
            fail(e.getMessage());
        }
    }

    private void deleteCreatedCampaign(String campaignId, Update api) {
        try {
            if (campaignId != null) {
                api.deleteCampaign(campaignId);
            }
        } catch (MbedCloudException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Lists the first 5 update campaigns.
     */
    @SuppressWarnings("boxing")
    @Example
    public void listCampaigns() {
        ConnectionOptions config = Configuration.get();
        Update api = new Update(config);
        try {
            // Defining query options.
            CampaignListOptions options = new CampaignListOptions();
            options.setLimit(5);
            // Listing campaigns.
            ListResponse<Campaign> campaigns = api.listCampaigns(options);
            for (Campaign campaign : campaigns.getData()) {
                log("Update campaign", campaign);
            }
        } catch (Exception e) {
            logError("last API Metadata", api.getLastApiMetadata());
            fail(e.getMessage());
        }
    }

    /**
     * Lists the first 5 firmware manifests.
     */
    @SuppressWarnings("boxing")
    @Example
    public void listFirmwareManifests() {
        ConnectionOptions config = Configuration.get();
        Update api = new Update(config);
        try {
            // Defining query options.
            FirmwareManifestListOptions options = new FirmwareManifestListOptions();
            options.setLimit(5);
            // Listing firmware manifests.
            ListResponse<FirmwareManifest> manifests = api.listFirmwareManifests(options);
            for (FirmwareManifest manifest : manifests.getData()) {
                log("Firmware manifest", manifest);
            }
        } catch (Exception e) {
            logError("last API Metadata", api.getLastApiMetadata());
            fail(e.getMessage());
        }
    }

    /**
     * Lists the first 5 firmware images.
     */
    @SuppressWarnings("boxing")
    @Example
    public void listFirmwareImages() {
        ConnectionOptions config = Configuration.get();
        Update api = new Update(config);
        try {
            // Defining query options.
            FirmwareImageListOptions options = new FirmwareImageListOptions();
            options.setLimit(5);
            // Listing firmware images.
            ListResponse<FirmwareImage> images = api.listFirmwareImages(options);
            for (FirmwareImage image : images.getData()) {
                log("Firmware image", image);
            }
        } catch (Exception e) {
            logError("last API Metadata", api.getLastApiMetadata());
            fail(e.getMessage());
        }
    }
}
