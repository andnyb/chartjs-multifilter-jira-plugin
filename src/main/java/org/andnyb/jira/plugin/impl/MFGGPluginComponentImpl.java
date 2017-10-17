package org.andnyb.jira.plugin.impl;

import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.ApplicationProperties;
import org.andnyb.jira.plugin.api.MFGGPluginComponent;

import javax.inject.Inject;
import javax.inject.Named;

@ExportAsService({ MFGGPluginComponent.class})
@Named ("MFGGPluginComponent")
public class MFGGPluginComponentImpl implements MFGGPluginComponent
{
    @ComponentImport
    private final ApplicationProperties applicationProperties;

    @Inject
    public MFGGPluginComponentImpl(final ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public String getName()
    {
        if(null != applicationProperties)
        {
            return "MFGGComponent:" + applicationProperties.getDisplayName();
        }
        
        return "MFGGComponent";
    }
}