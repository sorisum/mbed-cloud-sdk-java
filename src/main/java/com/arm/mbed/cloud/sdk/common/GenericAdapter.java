package com.arm.mbed.cloud.sdk.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.arm.mbed.cloud.sdk.annotations.Internal;
import com.arm.mbed.cloud.sdk.annotations.Nullable;
import com.arm.mbed.cloud.sdk.annotations.Preamble;
import com.arm.mbed.cloud.sdk.common.listing.ListResponse;

@Preamble(description = "Generic adapter")
@Internal
public class GenericAdapter {

    private GenericAdapter() {
        super();
    }

    /**
     * Mapper interface.
     * <p>
     * Utility in charge of converting an object U into an object T.
     *
     * @param <U>
     *            Type of the object to convert from.
     * @param <T>
     *            Type of the object to convert to.
     */
    public interface Mapper<U, T> {
        T map(U toBeMapped);
    }

    /**
     * Gets an Identity mapper.
     *
     * @param <T>
     *            Type of the object to convert from/to.
     * @return a mapper which maps an object to itself.
     */
    public static <T> Mapper<T, T> identityMapper() {
        return new Mapper<T, T>() {

            @Override
            public T map(T toBeMapped) {
                return toBeMapped;
            }
        };
    }

    /**
     * Paginated response from the server.
     * <p>
     * A typical page returned from server when listing objects.
     *
     * @param <U>
     *            type of objects listed
     */
    public interface RespList<U> {
        Boolean getHasMore();

        Integer getTotalCount();

        String getAfter();

        Integer getLimit();

        String getOrder();

        List<U> getData();
    }

    /**
     * Maps a page of objects U into a list of object T.
     *
     * @param respList
     *            page (i.e. paginated response from server)
     * @param mapper
     *            mapper of each object.
     * @param <U>
     *            type of the object to be mapped from
     * @param <T>
     *            type of the object to be mapped to
     * @return mapped list or null if list is null @see ListResponse
     */
    public static <T extends SdkModel, U> ListResponse<T> mapList(RespList<U> respList, Mapper<U, T> mapper) {
        if (respList == null || mapper == null) {
            return null;
        }
        final ListResponse<T> responseList = new ListResponse<>(TranslationUtils.toBool(respList.getHasMore(), false),
                TranslationUtils.toInt(respList.getTotalCount()), respList.getAfter(),
                TranslationUtils.toInt(respList.getLimit()),
                Order.parseOrder(respList.getOrder(), Order.getUnknownEnum()));
        if (respList.getData() == null || respList.getData().isEmpty()) {
            return responseList;
        }
        for (final U resp : respList.getData()) {
            responseList.addData(mapper.map(resp));
        }
        return responseList;

    }

    /**
     * Maps a list of objects U into a list of object T.
     *
     * @param list
     *            to map.
     * @param mapper
     *            mapper of each object.
     * @param <U>
     *            type of the object to be mapped from
     * @param <T>
     *            type of the object to be mapped to
     * @return mapped list or null if list is null
     */
    public static <T, U> List<T> mapList(List<U> list, Mapper<U, T> mapper) {
        if (list == null || mapper == null) {
            return null;
        }
        return mapList(list, new ArrayList<T>(list.size()), mapper);
    }

    /**
     * Maps a list of objects U into a list of object T.
     *
     * @param list
     *            list to map.
     * @param mappedList
     *            container of mapped objects
     * @param mapper
     *            mapper of each object.
     * @param <U>
     *            type of the object to be mapped from
     * @param <T>
     *            type of the object to be mapped to
     * @return mapped list
     */
    public static <T, U> List<T> mapList(List<U> list, List<T> mappedList, Mapper<U, T> mapper) {
        if (list == null || mapper == null) {
            return mappedList;
        }
        for (final U element : list) {
            mappedList.add(mapper.map(element));
        }
        return mappedList;
    }

    public static class MappedObjectRegistry<T> {

        private final Map<Integer, T> registry;

        public MappedObjectRegistry() {
            super();
            registry = new LinkedHashMap<>();
        }

        public <U> MappedObjectRegistry(List<U> list, Mapper<U, T> mapper) {
            this();
            addNewEntries(list, mapper);
        }

        protected Integer generateObjectId(T object) {
            return Integer.valueOf(object.hashCode());
        }

        protected Integer translateStringToKey(String objectId) throws NumberFormatException {
            return Integer.valueOf(Integer.parseInt(objectId.trim()));
        }

        /**
         * Creates an entry into the registry.
         *
         * @param object
         *            an entry
         */
        public void addNewEnty(@Nullable T object) {
            if (object == null) {
                return;
            }
            registry.put(generateObjectId(object), object);
        }

        /**
         * States whether the object is contained in the registry.
         *
         * @param object
         *            an entry
         * @return True if the registry is contained
         */
        public boolean contains(@Nullable T object) {
            return (isEmpty() || object == null) ? false : registry.containsKey(generateObjectId(object));
        }

        /**
         * States whether the object is contained in the registry.
         *
         * @param objectId
         *            object registry id
         * @return True if the registry is contained
         */
        public boolean contains(@Nullable String objectId) {
            try {
                return (isEmpty() || objectId == null) ? false : registry.containsKey(translateStringToKey(objectId));
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        /**
         * Gets a registry entry based on its registry id.
         *
         * @param objectId
         *            object registry id
         * @return corresponding object
         */
        public @Nullable T getEntry(@Nullable String objectId) {
            try {
                return (isEmpty() || objectId == null) ? null : registry.get(translateStringToKey(objectId));
            } catch (NumberFormatException exception) {
                return null;
            }
        }

        /**
         * Removes a registry entry based on its registry id.
         *
         * @param object
         *            an entry
         */
        public @Nullable void removeEntry(@Nullable T object) {
            try {
                if (isEmpty() || object == null) {
                    return;
                }
                registry.remove(generateObjectId(object));
            } catch (NumberFormatException exception) {
                return;
            }
        }

        /**
         * Removes a registry entry.
         *
         * @param objectId
         *            registry id of the object to remove
         */
        public @Nullable void removeEntry(@Nullable String objectId) {
            try {
                if (isEmpty() || objectId == null) {
                    return;
                }
                registry.remove(translateStringToKey(objectId));
            } catch (NumberFormatException exception) {
                return;
            }
        }

        /**
         * Maps a list of objects U and insert them into the registry.
         *
         * @param list
         *            list to map.
         *
         * @param mapper
         *            mapper of each object.
         * @param <U>
         *            type of the object to be mapped from
         *
         * @return mapped map
         */
        public <U> void addNewEntries(@Nullable List<U> list, @Nullable Mapper<U, T> mapper) {
            if (list == null || mapper == null) {
                return;
            }
            for (final U element : list) {
                final T mappedObject = mapper.map(element);
                addNewEnty(mappedObject);
            }
        }

        /**
         * States whether the registry is empty or not.
         *
         * @return true if the registry is empty. false otherwise.
         */
        public boolean isEmpty() {
            return registry == null || registry.isEmpty();
        }

        /**
         * Gets registry entries.
         *
         * @return registry entries (list of mapped objects)
         */
        public @Nullable List<T> getEntries() {
            return isEmpty() ? null : new ArrayList<>(registry.values());
        }
    }
}
