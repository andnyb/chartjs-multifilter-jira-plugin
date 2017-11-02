# Chart.JS Multi Filter JIRA Plugin
[![Build Status](https://travis-ci.org/andnyb/chartjs-multifilter-jira-plugin.svg)](https://travis-ci.org/andnyb/chartjs-multifilter-jira-plugin)

**Developed and tested for:** *JIRA Server 7.5.X (will not work on 6.x JIRA versions)*

JIRA Dashboard Gadget for rendering interactive Chart.JS based graphs including one or more JIRA search filters (multiple filters are not supported by JIRA as per default). The gadget is highly configurable both in what to display/categorize as well as what kind of graph to display. 

<p align="center"><img src="https://github.com/andnyb/chartjs-multifilter-jira-plugin/raw/master/src/main/resources/images/chartjs-multifilter-thumb.png" 
alt="thumbnail" 
width="400"/></p>

## Supported data and categorizations
* Week / Month / Year
* Created / Updated / Resolved
* Multiple JIRA search filters by id

## Supported graphs and customizations
* Graph types
  * Bar
  * Horizontal bar
  * Stacked bar
  * Line
  * Doughnut
  * Pie
* Customization
  * Pre defined themes
  
## Examples

* Issue creation over time with for JIRA search filters

Displaying creation over time of issues in multiple projects and how they relate to each other is pretty straight forward, Define a JIRA search filter for 
each of the project to include and select the granularity of the graph. The plugin will group the issues according to their created date (year/month/week) 
and then render a chart with separated data for each project (search filter).  

<p align="center"><img src="https://github.com/andnyb/chartjs-multifilter-jira-plugin/raw/master/src/main/resources/images/screenshot-line.png" 
alt="Line chart" 
width="70%"/></p>

The same data can be displayed in different type charts, below is an example for a stacked bar chart. 

<p align="center"><img src="https://github.com/andnyb/chartjs-multifilter-jira-plugin/raw/master/src/main/resources/images/screenshot-stackedbar.png" 
alt="Stacked bar chart" 
width="70%"/></p>

## Development

Here are the SDK commands you'll use immediately:

* atlas-run   -- installs this plugin into the product and starts it on localhost
* atlas-debug -- same as atlas-run, but allows a debugger to attach at port 5005
* atlas-cli   -- after atlas-run or atlas-debug, opens a Maven command line window:
                 - 'pi' reinstalls the plugin into the running product instance
* atlas-help  -- prints description for all commands in the SDK

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK
