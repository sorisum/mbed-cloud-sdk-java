
# UpdateCampaignPutRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**description** | **String** | An optional description of the campaign | 
**deviceFilter** | **String** | The filter for the devices the campaign will target | 
**name** | **String** | The campaign&#39;s name | 
**object** | **String** | The API resource entity | 
**rootManifestId** | **String** |  | 
**state** | [**StateEnum**](#StateEnum) | The state of the campaign | 
**when** | [**DateTime**](DateTime.md) | The scheduled start time for the update campaign | 


<a name="StateEnum"></a>
## Enum: StateEnum
Name | Value
---- | -----
DRAFT | &quot;draft&quot;
SCHEDULED | &quot;scheduled&quot;
ALLOCATINGQUOTA | &quot;allocatingquota&quot;
ALLOCATEDQUOTA | &quot;allocatedquota&quot;
QUOTAALLOCATIONFAILED | &quot;quotaallocationfailed&quot;
CHECKINGMANIFEST | &quot;checkingmanifest&quot;
CHECKEDMANIFEST | &quot;checkedmanifest&quot;
DEVICEFETCH | &quot;devicefetch&quot;
DEVICECOPY | &quot;devicecopy&quot;
DEVICECHECK | &quot;devicecheck&quot;
PUBLISHING | &quot;publishing&quot;
DEPLOYING | &quot;deploying&quot;
DEPLOYED | &quot;deployed&quot;
MANIFESTREMOVED | &quot;manifestremoved&quot;
EXPIRED | &quot;expired&quot;
STOPPING | &quot;stopping&quot;
AUTOSTOPPED | &quot;autostopped&quot;
USERSTOPPED | &quot;userstopped&quot;
CONFLICT | &quot;conflict&quot;



