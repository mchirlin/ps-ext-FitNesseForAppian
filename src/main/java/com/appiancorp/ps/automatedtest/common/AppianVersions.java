package com.appiancorp.ps.automatedtest.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.appiancorp.ps.automatedtest.object.TempoObject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppianVersions {
    private static final Logger LOG = Logger.getLogger(AppianVersions.class);
    private static List<AppianVersion> appianVersions;
    private static List<Version> allVersions;

    @JsonCreator
    public AppianVersions(
          @JsonProperty("appianVersions") List<AppianVersion> appianVersions) {
        AppianVersions.appianVersions = appianVersions;
        allVersions = new ArrayList<Version>();
        for (AppianVersion av : appianVersions) {
            allVersions.add(av.getVersion());
        }
    }

    public static List<AppianVersion> getAppianVersions() {
        return appianVersions;
    }

    public static String getByConstant(String constant) {
        Integer index = Version.getBestIndexFromList(TempoObject.getVersion(), allVersions);
        AppianVersion appianVersion = appianVersions.get(index);
        String byConstant;
        
        if (appianVersion.getVersion().match(TempoObject.getVersion()) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", TempoObject.getVersion()));
        }
        
        while (index >= 0) {
            byConstant = appianVersion.getByConstant(constant);
            if (byConstant != null) return byConstant;
            index--;
            appianVersion = appianVersions.get(index);
        }
        
        return null;
    }
    
    public static void initialize() {
        InputStream in = null;
        try {
            in = AppianVersions.class.getResource("/versions.json").openStream();
            new ObjectMapper().readValue(in, AppianVersions.class);
        } catch (Exception e) {
            LOG.error("Error processing metadata.json resource", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
