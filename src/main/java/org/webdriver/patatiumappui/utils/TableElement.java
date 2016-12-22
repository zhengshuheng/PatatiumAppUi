package org.webdriver.patatiumappui.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * Models a Table tag, providing helper methods to getCell element and cell Text.
 */
public class TableElement {
	
	private WebElement table;
	private String rowFilter = "";
	private String columnFilter = "";
	
	public TableElement(WebElement e ){
		
		this.table = e;			
	}
	
	/**
	 * For those table would have invisible row or column exist, we can we use xpath filters to exclude them
	 * */
	public void setTableFilter(String rowFilter, String columnFilter)
	{
		this.rowFilter = rowFilter;
		this.columnFilter = columnFilter;			
	}
	
	/**
	 * Get row index from a row name 
	 * Or several strings in row that can determine a unique column
	 * which separated by ","
	 * 
	 * @param  String
	 * Example: getRowIndex("rowName"), getRowIndex("namestring1, namestring2")
	 * 
	 * */
	public int getRowIndex(String rowInfo){
		
		String[] rowkey = rowInfo.split(",");
		StringBuilder sRowXpath = new StringBuilder(".//tr");
		for(int i=0;i<rowkey.length;i++)
		{
			sRowXpath.append("[./td[contains(.,'").append(rowkey[i]).append("')]]");
			
		}
		
		sRowXpath.append(rowFilter);
		return table.findElement(By.xpath(sRowXpath.toString())).findElements(By.xpath("./preceding-sibling::tr" + rowFilter)).size() + 1;
		
	}

	/**
	 * Get column index from a column name 
	 * Or several strings in a cell in first row that can determine a unique column
	 * which separated by ","
	 * @throws Exception 
	 * 
	 * @param  String
	 * Example: getColumnIndex("ColumnName"), getColumnIndex("namestring1, namestring2")
	 * 
	 * */
	public int getColumnIndex(String columnName){
		
		String[] columnkey = columnName.split(",");
		StringBuilder sColumnXpath = new StringBuilder(".//tr/th");
		for(int i=0;i<columnkey.length;i++)
		{
			sColumnXpath.append("[contains(.,'").append(columnkey[i]).append("')]");
		}			
		sColumnXpath.append(columnFilter);
		
		if(table.findElements(By.xpath(".//tr/th")).size()==0)
		{
			throw new NoSuchElementException("column name is not under tag <th>");
		}
		
		return table.findElement(By.xpath(sColumnXpath.toString())).findElements(By.xpath("./preceding-sibling::th" + columnFilter)).size() + 1;
		
	}
	
	public WebElement getCell(int rowIndex, int columnIndex)
	{
		String cellXpath = ".//tr[./td][" + String.valueOf(rowIndex) + "]/td[" + String.valueOf(columnIndex) + "]";
		return table.findElement(By.xpath(cellXpath));
	}

	public String getCellText(int rowIndex, int columnIndex){

		return getCell(rowIndex, columnIndex).getText();
	}
	
	public String getCellText(String rowInfo, int columnIndex){
		
		return getCell(getRowIndex(rowInfo),columnIndex).getText();
	}
	
	public String getCellText(int rowIndex, String columnName){
		
		return getCell(rowIndex,getColumnIndex(columnName)).getText();
	}
	
	public String getCellText(String rowInfo, String columnName){
		
		return getCell(getRowIndex(rowInfo),getColumnIndex(columnName)).getText();
	}
	public String getCellXpath(int rowIndex, int columnIndex)
	{
		String cellXpath = ".//tr[./td][" + String.valueOf(rowIndex) + "]/td[" + String.valueOf(columnIndex) + "]";
		return cellXpath;
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public int getColumnCount(int rowindex)
	{   WebElement rowElement=table.findElement(By.xpath(".//tr[./td]["
			+rowindex
			+ "]"));
		int columncount=rowElement.findElements(By.xpath(".//td")).size();
		return columncount;
	}
	
}
