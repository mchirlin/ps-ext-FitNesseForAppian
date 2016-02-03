package com.appiancorp.ps.automatedtest.common;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Metadata {
    private static final Logger LOG = Logger.getLogger(Metadata.class);
    private static List<AppianVersion> appianVersions;
    private static List<Version> allVersions;

    @JsonCreator
    public Metadata(
          @JsonProperty("appianVersions") List<AppianVersion> appianVersions) {
        Metadata.appianVersions = appianVersions;
        allVersions = new ArrayList<Version>();
        for (AppianVersion av : appianVersions) {
            allVersions.add(av.getVersion());
        }
    }

    public static List<AppianVersion> getAppianVersions() {
        return appianVersions;
    }

    public static AppianVersion getByConstant(String constant, String version) {
        Version v = new Version(TempoObject.getVersion());
        Integer bestMatch = Version.getBestIndexFromList(v, allVersions);
        AppianVersion appianVersion = appianVersions.get(bestMatch);
        
        if (appianVersion.getVersion().match(v) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", version));
        }
        
        return appianVersion;
    }
    
    public static void initialize() {
        InputStream in = null;
        try {
            in = Metadata.class.getResource("/metadata.json").openStream();
            new ObjectMapper().readValue(in, Metadata.class);
        } catch (Exception e) {
            LOG.error("Error processing metadata.json resource", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
