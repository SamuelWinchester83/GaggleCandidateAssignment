package com.gaggle.assignment.handler;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the possible operations supported.
 */
public enum UserOperation {
    @SerializedName("search_by_id") SEARCH_BY_ID,
    @SerializedName("search_by_name") SEARCH_BY_NAME
}
