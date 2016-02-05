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

public class Metadata {
    private static final Logger LOG = Logger.getLogger(Metadata.class);
    private static List<AppianVersion> appianVersions;
    private static List<Version> allVersions;
    private static List<AppianLocale> appianLocales;

    @JsonCreator
    public Metadata(
          @JsonProperty("appianVersions") List<AppianVersion> appianVersions,
          @JsonProperty("appianLocales") List<AppianLocale> appianLocales) {
        Metadata.appianVersions = appianVersions;
        Metadata.appianLocales = appianLocales;
        allVersions = new ArrayList<Version>();
        for (AppianVersion av : appianVersions) {
            allVersions.add(av.getVersion());
        }
    }

    public static List<AppianVersion> getAppianVersions() {
        return appianVersions;
    }
    
    public static List<AppianLocale> getAppianLocales() {
        return appianLocales;
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
            in = Metadata.class.getResource("/metadata.json").openStream();
            new ObjectMapper().readValue(in, Metadata.class);
        } catch (Exception e) {
            LOG.error("Error processing metadata.json resource", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
