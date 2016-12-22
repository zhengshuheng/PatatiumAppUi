package org.webdriver.patatiumappui.utils;
/**
 * This is for element library
 * 
 * @author zhengshuheng
 *
 */
public class Locator {
  private String value;
  private int timout;
  private String locatorName;
  private ByType type;
  /**
   * create a enum variable for By
   * 
   * @author zhengshuheng
   *
   */
  public enum ByType {
    xpath, id, linkText, name, className, cssSelector, partialLinkText, tagName
  }
  public Locator() {
  }
  /**
   * defaut Locator ,use Xpath
   * 
   * @author zhengshuheng
   * @param element
   */
  public Locator(String element) {
    this.value = element;
    this.timout = 3;
    this.type = ByType.xpath;
  }
  public Locator(String element, int waitSec) {
    this.timout = waitSec;
    this.value = element;
    this.type = ByType.xpath;
  }
  public Locator(String element, int waitSec, ByType byType) {
    this.timout = waitSec;
    this.value = element;
    this.type = byType;
  }
  public Locator(String element, int waitSec, ByType byType,String locatorName) {
	    this.timout = waitSec;
	    this.value = element;
	    this.type = byType;
	    this.locatorName=locatorName;
	  }
  public String getElement() {
    return value;
  }
  public int getWaitSec() {
    return timout;
  }
  public ByType getType() {
    return type;
  }
  public void setType(ByType byType) {
    this.type = byType;
  }
  public String getLocalorName()
  {
	  return locatorName;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getTimout() {
    return timout;
  }

  public void setTimout(int timout) {
    this.timout = timout;
  }

  public String getLocatorName() {
    return locatorName;
  }

  public void setLocatorName(String locatorName) {
    this.locatorName = locatorName;
  }
}