package com.arm.mbed.cloud.sdk.update.model;

import java.net.URL;
import java.util.Date;

import com.arm.mbed.cloud.sdk.annotations.DefaultValue;
import com.arm.mbed.cloud.sdk.annotations.Internal;
import com.arm.mbed.cloud.sdk.annotations.Preamble;

@Preamble(description = "Firmware Image")
public class FirmwareImage {
    /**
     * The ID of the firmware image
     */
    private String id;
    /**
     * The name of the object
     */
    private String name;
    /**
     * The description of the object
     */
    private String description;
    /**
     * The binary file of firmware image
     */
    private DataFile dataFile;

    /**
     * The URL of the firmware image
     */
    private final URL url;
    /**
     * Checksum generated for the datafile
     */
    private final String datafileChecksum;
    /**
     * The time the object was created
     */
    @DefaultValue(value = "now()")
    private final Date createdAt;
    /**
     * The time the object was updated
     */
    @DefaultValue(value = "now()")
    private final Date updatedAt;

    @Internal
    public FirmwareImage(String id, URL url, String datafileChecksum, Date createdAt, Date updatedAt) {
        super();
        setId(id);
        this.url = url;
        this.datafileChecksum = datafileChecksum;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        setDataFile(null);
        setDescription(null);
        setName(null);
    }

    public FirmwareImage() {
        this(null, null, null, new Date(), new Date());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the dataFile
     */
    public DataFile getDataFile() {
        return dataFile;
    }

    /**
     * @param dataFile
     *            the dataFile to set
     */
    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * @return the url
     */
    public URL getUrl() {
        return url;
    }

    /**
     * @return the datafileChecksum
     */
    public String getDatafileChecksum() {
        return datafileChecksum;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

}
