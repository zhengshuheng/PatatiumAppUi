package org.webdriver.patatiumappui.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.*;
import org.testng.xml.XmlSuite;
import org.webdriver.patatiumappui.config.Config;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表生成器
 * @author Administrator
 *
 */
public class TestReport implements IReporter{

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory)
	{
		List<ITestResult> list = new ArrayList<ITestResult>();
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult suiteResult : suiteResults.values()) {
				ITestContext testContext = suiteResult.getTestContext();
				IResultMap passedTests = testContext.getPassedTests();
				IResultMap failedTests = testContext.getFailedTests();
				IResultMap skippedTests = testContext.getSkippedTests();
				IResultMap failedConfig = testContext.getFailedConfigurations();
				list.addAll(this.listTestResult(passedTests));
				list.addAll(this.listTestResult(failedTests));
				list.addAll(this.listTestResult(skippedTests));
				//list.addAll(this.listTestResult(failedConfig));
			}
		}
		CopyReportResources reportReource=new CopyReportResources();

		try {
			reportReource.copyResources();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		this.sort(list);
		this.outputResult(list, outputDirectory+"/report.html");


	}

	private ArrayList<ITestResult> listTestResult(IResultMap resultMap){
		Set<ITestResult> results = resultMap.getAllResults();
		return new ArrayList<ITestResult>(results);
	}

	private void sort(List<ITestResult> list){
		Collections.sort(list, new Comparator<ITestResult>() {
			@Override
			public int compare(ITestResult r1, ITestResult r2) {
				if(r1.getStartMillis()>r2.getStartMillis()){
					return 1;
				}else{
					return -1;
				}
			}
		});
	}
	private void outputResult(List<ITestResult> fullResults, String path){
		ArrayList<ITestResult> passArrayList=new ArrayList<ITestResult>();
		ArrayList<ITestResult> failedArrayList=new ArrayList<ITestResult>();
		ArrayList<ITestResult> skipArrayList=new ArrayList<ITestResult>();
		try {
			//BufferedWriter output = new BufferedWriter(new FileWriter(new File(path)));
			StringBuffer sb = new StringBuffer();
			for (ITestResult result : fullResults) {
				if(result.getStatus()==1)
				{
					passArrayList.add(result);
				}
				if(result.getStatus()==2)
				{
					failedArrayList.add(result);
				}
				if(result.getStatus()==3)
				{
					skipArrayList.add(result);
				}
			}
			String reportTitle="";
			try {
				reportTitle=getTestngParametersValue(Config.path,"reportTitle");
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n");
			sb.append("<meta http-equiv=\"Content-Type\"content=\"text/html; charset=utf-8\" />\n");
			sb.append("<title>" +
					reportTitle +
					"</title>\n");
			sb.append("<link href=\"report.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
			sb.append("<link href=\"imageshow.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
			sb.append("<script type=\"text/javascript\" src=\"jquery-1.7.1.min.js\"></script>\n ");
			sb.append("<script type=\"text/javascript\" src=\"loadxmldoc.js\"></script>\n");
			sb.append("<script type=\"text/javascript\" src=\"report.js\"></script>\n");
			sb.append("<script type=\"text/javascript\" src=\"Chart.js\"></script>\n");
			sb.append("<script type=\"text/javascript\">\n")
					.append("function bigimg(i)\n"
							+ "{\nvar zoom = parseInt(i.style.zoom,10)||100;\n"
							+ "zoom += event.wheelDelta / 12;\n"
							+ "if(zoom > 0 )\n"
							+ "i.style.zoom=zoom+'%';\n"
							+ "return false;\n}")
					.append(" $.ajaxSetup ({ cache: false\n //关闭AJAX相应的缓存\n });\n")
					.append(" $(document).ready(function(){"
							+ "$(\".passed_button\").click(function(){\n"
							+ "if($(this).parent().parent().find(\"span\").css(\"display\")==\"none\")\n"
							+ "{\n"
							+"$(this).parent().parent().find(\"div\").load(\"passed.xml\",function(response,status){\n"
							+" if (status==\"success\")\n"
							+"{"
							+"var pass_assert_info_attr=$(this).parent().parent().attr(\"id\");\n"
							+"var str=\"pass_assert_info[method=\'\";\n"
							+ "var str2=\"\']\";\n"
							+ "var str3=str.concat(pass_assert_info_attr).concat(str2);\n"
							+ "  var item=$(response).find(str3).text();\n"
							+ "var div=$(this).parent().parent().find(\"div\");\n"
							+ "div.html(\"<ol></ol>\");\n"
							+ "$(response).find(str3).each(function(){\n"
							+ "var item_text=$(this).text();\n"
							+"if(item_text.indexOf(\"pass\")>0)\n"
							+ "{\n"
							+ "$('<li class=\"passed_info_li\" ></li>').html(item_text).appendTo('ol');\n"
							+ "}\n"
							+ "else\n"
							+ "{$('<li class=\"failed_info_li\"></li>').html(item_text).appendTo('ol');\n"
							+ "}\n"
							+ " });"
							+ " }\n });\n"
							+ "$(this).parent().parent().find(\"span\").show();\n"
							+ "$(this).parent().parent().find(\"div\").show();\n"
							+ " $(this).parent().parent().prevAll().find(\"span\").hide();\n"
							+ " $(this).parent().parent().prevAll().find(\"div\").hide();\n"
							+ "$(this).parent().parent().nextAll().find(\"span\").hide();\n"
							+ "$(this).parent().parent().nextAll().find(\"div\").hide();\n"
							+ "}\n"
							+ "else\n"
							+ "{\n"
							+ "$(this).parent().parent().find(\"span\").hide();\n"
							+ "$(this).parent().parent().find(\"div\").hide();\n"
							+ "}\n"
							+ "}\n"
							+ ");\n"
							+ "$(\".failed_button\").click(function(){\n"
							+ "if($(this).parent().parent().find(\"span\").css(\"display\")==\"none\")"
							+ "{\n"
							+ "	$(this).parent().parent().find(\"div\").load(\"failed.xml\",function(response,status){\n"
							+ "if (status==\"success\")\n"
							+ "{var pass_assert_info_attr=$(this).parent().parent().attr(\"id\");\n"
							+ "var str4=\"err_assert_info[method='\";\n"
							+ "var str5=\"']\";\n"
							+ "var str6=str4.concat(pass_assert_info_attr).concat(str5);\n"
							+ "var item=$(response).find(str6).text();\n"
							+ "var div=$(this).parent().parent().find(\"div\");\n"
							+ "div.html(\"<ol></ol>\");\n"
							+ "$(response).find(str6).each(function(){\n"
							+ "var item_text=$(this).text();\n"
							+"if(item_text.indexOf(\"pass\")>0)\n"
							+ "{\n"
							+ "$('<li class=\"passed_info_li\" ></li>').html(item_text).appendTo('ol');\n"
							+ "}\n"
							+ "else\n"
							+ "{$('<li class=\"failed_info_li\"></li>').html(item_text).appendTo('ol');\n"
							+ "}\n"
							+ " }); }\n"
							+ " });\n"
							+ "$(this).parent().parent().find(\"span\").show();\n"
							+ "$(this).parent().parent().find(\"div\").show();\n"
							+ "$(this).parent().parent().prevAll().find(\"span\").hide();\n"
							+ "$(this).parent().parent().prevAll().find(\"div\").hide();\n"
							+ "$(this).parent().parent().nextAll().find(\"span\").hide();\n"
							+ "$(this).parent().parent().nextAll().find(\"div\").hide();\n"
							+ "}\n"
							+ "else\n"
							+ "{\n"
							+ "$(this).parent().parent().find(\"span\").hide();\n"
							+ "$(this).parent().parent().find(\"div\").hide();\n"
							+ "}\n"
							+ "}\n"
							+ ");\n"
							+ "})\n"
							+ "</script>");
			sb.append("</head>\n");
			sb.append("<body>\n");
			sb.append("<div id=\"content\">\n");

			sb.append("<div id=\"report_title\">\n");
			sb.append("<div id=\"logo\"><img src=\"logo1.png\"></img></div>\n");
			sb.append("<div style=\"clear:both\"><div id=\"time\">\n");
			sb.append("<div style=\"pading-top:20px\">开始时间："
					+ this.formatDate(fullResults.get(0).getStartMillis())
					+ "</div>\n");
			sb.append("<div>结束时间："
					+ this.formatDate(fullResults.get(fullResults.size()-1).getEndMillis())
					+ " </div>\n");
			sb.append("<div>用&nbsp&nbsp&nbsp&nbsp时："
					+formateTime(fullResults.get(fullResults.size()-1).getEndMillis()-fullResults.get(0).getStartMillis())
					+ "</div>\n");
			sb.append("</div>\n");

			sb.append("</div>\n");
			sb.append("</div>");
			sb.append("<div  id=\"header_right\" >\n");
			sb.append("<div id=\"title\">\n");
			sb.append("<div>" +
					reportTitle +
					"</div>\n");
			sb.append("</div>\n");
			sb.append("<div id=\"canvas_div\"><canvas id=\"canvas\" height=\"90\" width=\"90\"></canvas>\n</div>"
					+ "</div>\n");
			sb.append("<div id=\"report_total\">\n");
			sb.append("<table width=\"100%\">\n");
			sb.append("<tr>\n"
					+ "<td width=\"84\"><div align=\"center\">用例总数</div></td>\n");
			sb.append("<td width=\"152\" align=\"center\"><div align=\"center\">通过数（pass）</div></td>\n");
			sb.append("<td width=\"125\" align=\"center\"><div align=\"center\">失败数(failed)</div></td>\n");
			sb.append("<td width=\"96\" align=\"center\"><div align=\"center\">跳过数(skip)</div></td>\n");
			sb.append("<td width=\"91\" align=\"center\"><div align=\"center\">通过率</div></td>\n</tr>\n");
			sb.append(" <tr>\n<td><div align=\"center\">");
			sb.append("<button id=\"total_num\">");
			sb.append(passArrayList.size()+failedArrayList.size()+skipArrayList.size());
			sb.append("</button>");
			sb.append("</div></td>\n<td><div align=\"center\">");
			sb.append("<button id=\"passed_num\""
					+ "value=\""
					+ passArrayList.size()
					+ "\">");
			sb.append(passArrayList.size());
			sb.append("</button>");
			sb.append("</div></td>\n <td><div align=\"center\">");
			sb.append("<button id=\"failed_num\""
					+ "value=\""
					+ failedArrayList.size()
					+ "\">");
			sb.append(failedArrayList.size());
			sb.append("</button>");
			sb.append("</div></td>\n<td><div align=\"center\">");
			sb.append("<button id=\"skiped_num\""
					+ "value=\""
					+skipArrayList.size()
					+ "\">");
			sb.append(skipArrayList.size());
			sb.append("</button>");
			sb.append("</div></td>\n<td><div align=\"center\">");
			DecimalFormat dfDecimalFormat=new DecimalFormat("######0.00");
			float total=passArrayList.size()+failedArrayList.size()+skipArrayList.size();
			float s=passArrayList.size()/total;
			sb.append(dfDecimalFormat.format(s*100));
			sb.append("%");
			sb.append("</div></td>\n</tr>\n</table>\n");
			sb.append("<script type=\"text/javascript\">\n"
					+ "var x= $('#failed_num').attr('value');\n"
					+ "var y=$(\"#passed_num\").attr(\'value\');\n"
					+ "var z=$(\"#skiped_num\").attr(\'value\');\n"
					+ "var passed_num=Number(y);\n"
					+ "var failed_num=Number(x);\n"
					+ "var skiped_num=Number(z);\n"
					+ "var pieData = [\n"
					+ "{value: passed_num,\n"
					+ "color:\"green\"},\n"
					+ "{value : failed_num,\n"
					+ "color : \"red\"}\n,{\n"
					+ "value : skiped_num,\n"
					+ "color : \"yellow\"}\n"
					+ "];\nvar myPie = new Chart(document.getElementById(\"canvas\").getContext(\"2d\")).Pie(pieData);\n"
					+ "</script>\n");
			//--所有测试结果展示--start
			sb.append("<table id=\"total\">\n");
			sb.append("<tr>\n<td colspan=7 align=\"left\" style=\"color:green;font-size:14px;font-weight:bold\">所有测试用例</td>\n</tr>\n");
			sb.append("<tr>\n<td width=\"2%\" class=\"SEQ\">序号</td>\n");
			sb.append("<td width=\"5%\" class=\"case_funtcion_td\">功能模块</td>\n");
			sb.append("<td width=\"20%\" class=\"case_name_td\">用例名称</td>\n");
			sb.append("<td width=\"43%\" colspan=2 align=\"left\">测试结果</td>\n");
			sb.append("<td width=\"20%\" class=\"case_result_beizhu_td\">备注</td>\n");
			sb.append("<td width=\"10%\" class=\"case_time\">耗时</td>\n");
			sb.append("</tr>\n");
			//for (ITestResult fullITestResult : fullResults)
			for (int i = 0; i < fullResults.size(); i++)
			{
				if(sb.length()!=0){
					sb.append("\r\n");
				}
				float t=fullResults.get(i).getEndMillis()-fullResults.get(i).getStartMillis();
				String testMethodFullName=fullResults.get(i).getTestClass().getName()+"."+fullResults.get(i).getMethod().getMethodName();
				if (!fullResults.get(i).getMethod().getDescription().isEmpty())
				{
					if (fullResults.get(i).getParameters().length>0)
					{
						sb.append("<tr id=\""+testMethodFullName+"."+fullResults.get(i).getEndMillis()+"\">\n");
						sb.append("<td class=\"SEQ\">"+(i+1)+"</td>");
						Object[] parameters=fullResults.get(i).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb.append("<td class=\"case_funtcion_td\">"+fullResults.get(i).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+fullResults.get(i).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb.append("<td class=\"case_funtcion_td\">"+fullResults.get(i).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+fullResults.get(i).getMethod().getDescription()+"</td>\n");

						}
					}
					else {
						TestNG testNG=new TestNG();
						testNG.getDefaultTestName();
						sb.append("<tr id=\""+testMethodFullName+"\">\n");
						sb.append("<td class=\"SEQ\">"+(i+1)+"</td>");
						sb.append("<td class=\"case_funtcion_td\">"+fullResults.get(i).getTestContext().getName()+"</td>\n");
						sb.append("<td class=\"case_name_td\">"+fullResults.get(i).getMethod().getDescription()+"</td>\n");
					}
					sb.append(" ")
							.append("<td class=\"case_result_td\">");
					if(this.getStatus(fullResults.get(i).getStatus()).equals("p"))
					{
						sb.append("<button class=\"passed_button\" id=\"fullresult_"+fullResults.get(i).getMethod().getMethodName()+"\">"+this.getStatus(fullResults.get(i).getStatus())+"</button><br />"+"\n");
					}
					else if(this.getStatus(fullResults.get(i).getStatus()).equals("f"))
					{

						sb.append("<button class=\"failed_button\" id=\"fullresult_"+fullResults.get(i).getMethod().getMethodName()+"\">"+this.getStatus(fullResults.get(i).getStatus())+"</button><br />"+"\n");
					}
					sb.append("</td>\n")
							.append("<td  class=\"case_result_info_td\">");
					sb.append("<span class=\"full_info\" id=\"fullresult_"+fullResults.get(i).getMethod().getMethodName()+"_info\" style=\"display:none\" "+">");
                  /*.append("用例基本信息：<br />\n");
                  .append("method:"+testMethodFullName+"<br />\n")
                  .append("start_time:"+this.formatDate(fullResults.get(i).getStartMillis())+"<br />\n")
                  .append("time:"+Float.toString(t)+"毫秒"+"<br />\n");*/
					sb.append("检查点：<br />\n");
					sb.append("</span><div class=\"full_info_div\"></div></td>\n");
					if (fullResults.get(i).getParameters().length>0) {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据："+fullResults.get(i).getParameters()[0]+"</td>\n");
					}
					else {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}
					sb.append("<td class=\"case_time_td\">"+formateTime((long) t)+"</td>\n");
					//点击结果标志按钮，弹出失败信息
					sb.append("<td colspan=\"3\" style=\"display:none\">"+""+"</td>\n")
							.append("</tr>\n");
				}
			}
			if(sb.length()!=0){
				sb.append("\r\n");
			}

			sb.append("</table>\n");
			//--详细测试结果--end

			//--跳过结果展示--start
			sb.append("<table id=\"skiped\">\n<tr>\n");
			sb.append("<tr>\n<td colspan=7 align=\"left\" style=\"color:green;font-size:14px;font-weight:bold\">跳过测试用例</td>\n</tr>\n");
			sb.append("<td width=\"2%\" class=\"SEQ\">序号</td>\n");
			sb.append("<td width=\"5%\" class=\"case_funtcion_td\">功能模块</td>\n");
			sb.append("<td width=\"20%\" class=\"case_name_td\">用例名称</td>\n");
			sb.append("<td width=\"43%\" colspan=2 align=\"left\">测试结果</td>\n");
			sb.append("<td width=\"20%\" class=\"case_result_beizhu_td\">备注</td>\n");
			sb.append("<td width=\"10%\" class=\"case_time\">耗时</td>\n");
			sb.append("<tr>\n");
			//for (ITestResult skipresult : skipArrayList)
			for (int j = 0; j < skipArrayList.size(); j++)
			{
				if(sb.length()!=0){
					sb.append("\r\n");
				}
				float t=skipArrayList.get(j).getEndMillis()-skipArrayList.get(j).getStartMillis();
				String testMethodFullName=skipArrayList.get(j).getTestClass().getName()+"."+skipArrayList.get(j).getMethod().getMethodName();
				if (!skipArrayList.get(j).getMethod().getDescription().isEmpty()) {
					if (skipArrayList.get(j).getParameters().length>0) {
						sb.append("<tr id=\""+testMethodFullName+"."+skipArrayList.get(j).getEndMillis()+"\">\n");
						sb.append("<td class=\"SEQ\">"+(j+1)+"</td>");
						Object[] parameters=skipArrayList.get(j).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb.append("<td class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb.append("<td class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+"</td>\n");
						}

					}
					else {
						sb.append("<tr id=\""+testMethodFullName+"\">\n");
						sb.append("<td class=\"SEQ\">"+(j+1)+"</td>");
						sb.append("<td class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
						sb.append("<td class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+"</td>\n");
					}
					sb.append(" ")
							.append("<td class=\"case_result_info_td\">"+this.getStatus(skipArrayList.get(j).getStatus())+"</td>\n");
					if (skipArrayList.get(j).getParameters().length>0) {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据："+skipArrayList.get(j).getParameters()[0]+"</td>\n");
					}
					else {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}

					sb.append("<td class=\"case_time_td\">"+formateTime((long) t)+"</td>\n")

							//点击跳过按钮，弹出失败信息

							.append("<td colspan=\"3\" style=\"display:none\">"+""+"</td>\n")
							.append("</tr>\n");
				}
			}
			if(sb.length()!=0){
				sb.append("\r\n");
			}

			sb.append("</table>\n");
			//--跳过结果--end
			//成功结果展示--start
			sb.append("<table id=\"passed\">\n");
			sb.append("<tr>\n<td colspan=7 align=\"left\" style=\"color:green;font-size:14px;font-weight:bold\">成功测试用例</td>\n</tr>\n");
			sb.append("<tr>\n<td width=\"2%\" class=\"SEQ\">序号</td>\n");
			sb.append("<td width=\"5%\" class=\"case_funtcion_td\">功能模块</td>\n");
			sb.append("<td width=\"20%\" class=\"case_name_td\">用例名称</td>\n");
			sb.append("<td width=\"43%\" colspan=2 align=\"left\">测试结果</td>\n");
			sb.append("<td width=\"20%\" class=\"case_result_beizhu_td\">备注</td>\n");
			sb.append("<td width=\"10%\" class=\"case_time\">耗时</td>\n");
			sb.append("<tr>\n");

			//for (ITestResult passresult : passArrayList)
			for (int i = 0; i < passArrayList.size(); i++)
			{
				if(sb.length()!=0){
					sb.append("\r\n");
				}
				float t=passArrayList.get(i).getEndMillis()-passArrayList.get(i).getStartMillis();
				String testMethodFullName=passArrayList.get(i).getTestClass().getName()+"."+passArrayList.get(i).getMethod().getMethodName();
				if (!passArrayList.get(i).getMethod().getDescription().isEmpty()) {
					if (passArrayList.get(i).getParameters().length>0) {
						sb.append("<tr id=\""+testMethodFullName+"."+passArrayList.get(i).getEndMillis()+"\">\n");
						sb.append("<td class=\"SEQ\">"+(i+1)+"</td>");
						Object[] parameters=passArrayList.get(i).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb.append("<td class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb.append("<td class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+"</td>\n");
						}
					}
					else {
						sb.append("<tr id=\""+testMethodFullName+"\">\n");
						sb.append("<td class=\"SEQ\">"+(i+1)+"</td>");
						sb.append("<td class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
						sb.append("<td class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+"</td>\n");
					}
					sb.append(" ")
							.append("<td class=\"case_result_td\"><button class=\"passed_button\" id=\"passedresult_"+passArrayList.get(i).getMethod().getMethodName()+"\">"+this.getStatus(passArrayList.get(i).getStatus())+"</button><br /></td>"+"\n")
							.append("<td  class=\"case_result_info_td\">")
							.append("<span class=\"passed_info\" id=\"passedresult_"+passArrayList.get(i).getMethod().getMethodName()+"_info\" style=\"display:none\" "+">")
                /*.append("method:"+testMethodFullName+"<br />\n")
                .append("start_time:"+this.formatDate(passArrayList.get(i).getStartMillis())+"<br />\n")
                .append("time:"+Float.toString(t)+"毫秒"+"<br />\n")*/
							.append("检查点：<br />\n")
							.append("</span>\n<div class=\"passed_info_div\"></div></td>\n");
					if (passArrayList.get(i).getParameters().length>0) {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据："+passArrayList.get(i).getParameters()[0]+"</td>\n");
					}
					else {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}

					sb.append("<td class=\"case_time_td\">"+formateTime((long) t)+"</td>\n");
					//点击成功按钮，弹出成功信息
					sb.append("<td colspan=\"3\" style=\"display:none\">"+""+"</td>\n");
					sb.append("</tr>\n");
				}
			}
			if(sb.length()!=0){
				sb.append("\r\n");
			}
			sb.append("</table>\n");
			//成功结果展示--end
			//失败结果展示--start
			sb.append("<table  id=\"failed\">\n");
			sb.append("<tr>\n<td colspan=7 align=\"left\" style=\"color:red;font-size:14px;font-weight:bold\">失败测试用例</td>\n</tr>\n");
			sb.append("<tr>");
			sb.append("<td width=\"2%\" class=\"SEQ\">序号</td>\n");
			sb.append("<td width=\"5%\" class=\"case_funtcion_td\">功能模块</td>\n");
			sb.append("<td width=\"20%\" class=\"case_name_td\">用例名称</td>\n");
			sb.append("<td width=\"43%\" colspan=2 align=\"left\">测试结果</td>\n");
			sb.append("<td width=\"20%\" class=\"case_result_beizhu_td\">备注</td>\n");
			sb.append("<td width=\"10%\" class=\"case_time\">耗时</td>\n");
			sb.append("</tr>\n");
			for (int j = 0; j < failedArrayList.size(); j++)
			{
				if(sb.length()!=0){
					sb.append("\r\n");
				}
				float t=failedArrayList.get(j).getEndMillis()-failedArrayList.get(j).getStartMillis();
				String testMethodFullName=failedArrayList.get(j).getTestClass().getName()+"."+failedArrayList.get(j).getMethod().getMethodName();
				if (!failedArrayList.get(j).getMethod().getDescription().isEmpty()) {

					if (failedArrayList.get(j).getParameters().length>0) {
						sb.append("<tr id=\""+testMethodFullName+"."+failedArrayList.get(j).getEndMillis()+"\">\n");
						sb.append("<td class=\"SEQ\">"+(j+1)+"</td>");
						Object[] parameters=failedArrayList.get(j).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb.append("<td class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb.append("<td class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb.append("<td class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+"</td>\n");
						}
					}
					else {
						sb.append("<tr id=\""+testMethodFullName+"\">\n");
						sb.append("<td class=\"SEQ\">"+(j+1)+"</td>");
						sb.append("<td class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
						sb.append("<td class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+"</td>\n");
					}
					sb.append(" ")
							.append("<td class=\"case_result_td\" ><button class=\"failed_button\" id=\"failedresult_"+failedArrayList.get(j).getMethod().getMethodName()+"\">"+this.getStatus(failedArrayList.get(j).getStatus())+"</button><br /></td>"+"\n")
							.append("<td  class=\"case_result_info_td\">")
							.append("<span class=\"failed_info\" id=\"failedresult_"+failedArrayList.get(j).getMethod().getMethodName()+"_info\" style=\"display:none\" "+">")
                 /*.append("method:"+testMethodFullName+"<br />\n")
                 .append("start_time:"+this.formatDate(failedArrayList.get(j).getStartMillis())+"<br />\n")
                 .append("time:"+Float.toString(t)+"毫秒"+"<br />\n")*/
							.append("检查点：<br />\n")
							.append("</span><div class=\"failed_info_div\"></div></td>\n");
					if (failedArrayList.get(j).getParameters().length>0) {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据："+failedArrayList.get(j).getParameters()[0]+"</td>\n");
					}
					else {
						sb.append("<td class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}
					sb.append("<td class=\"case_time_td\">"+formateTime((long) t)+"</td>\n");
					//点击失败按钮，弹出失败信息

					sb.append("<td colspan=\"3\" style=\"display:none\">")
							.append("</td>\n")
							.append("</tr>\n");
				}


			}
			//失败结果展示--end
			sb.append("</table>\n</div>\n");
			sb.append("</div>\n"
					+"<span style=\"font-size:14px;margin-top:5px\" >温馨提示：点击按钮展开详情</span>"
					+ "<div id=\"footer\" >技术支持：Copyright © 2014 Webdriver中文社区.Inc</div>");
			sb.append("</body>\n</html>\n");
			/*output.write(sb.toString());
			output.flush();
			output.close();*/
			FileManger fileManger=new FileManger();
			fileManger.writeWithEncode(path,"utf-8",true,sb.toString());



			/**
			 * 发送html邮件
			 */
			String reportUrl=null;//完整报表URL
			String logUrl=null;//日志url
			String Recipients=null; //邮件收件人地址
			try {
				reportUrl=getTestngParametersValue(Config.path, "reportUrl");
				logUrl=getTestngParametersValue(Config.path, "logUrl");
				Recipients=getTestngParametersValue(Config.path, "recipients");
			} catch (DocumentException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println("报表URL"+reportUrl);
			System.out.println("日志URL"+logUrl);
			System.out.println("收件人地址"+Recipients);
			StringBuffer sb2=new StringBuffer();
			sb2.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			sb2.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n");
			sb2.append("<meta http-equiv=\"Content-Type\"content=\"text/html; charset=gbk\" />\n");
			sb2.append("<title>" +
					reportTitle +
					"</title>\n");
			sb2.append("</head>\n");
			sb2.append("<body>\n");
			sb2.append("<div id=\"content\">\n");
			sb2.append("<div id=\"report_title\">\n");
			sb2.append("<div id=\"logo\"><img src=\"http://www.webdriver.org/template/time_6th_travel/src/logo.png\"></img></div>\n");
			sb2.append("<div style=\"clear:both\"><div id=\"time\">\n");
			sb2.append("<div style=\"pading-top:20px\">开始时间："
					+ this.formatDate(fullResults.get(0).getStartMillis())
					+ "</div>\n");
			sb2.append("<div>结束时间："
					+ this.formatDate(fullResults.get(fullResults.size()-1).getEndMillis())
					+ " </div>\n");
			sb2.append("<div>用&nbsp&nbsp&nbsp&nbsp时："
					+formateTime(fullResults.get(fullResults.size()-1).getEndMillis()-fullResults.get(0).getStartMillis())
					+ "</div>\n");
			sb2.append("</div>\n");

			sb2.append("</div>\n");
			sb2.append("</div>");
			sb2.append("<div  id=\"header_right\" >\n");
			sb2.append("<div id=\"title\">\n");
			sb2.append("<div style=\"text-align:center\">" +
					reportTitle +
					"</div>\n");
			sb2.append("</div>\n");
			sb2.append("</div>\n");
			sb2.append("<div id=\"report_total\">\n");
			sb2.append("<div>(<span style=\"color:green\">绿色字体pass用例</span><span style=\"color:red\">红色字体failed用例</span><span style=\"color:gray\">灰色字体skip用例</span>)<span style=\" margin-left:20px\"><a href=\" "
					+reportUrl
					+ "\">点击查看完整报表</a></span><span style=\"margin-left:14px\"><a href=\""
					+logUrl
					+ "\"></a></span></div>\n");
			sb2.append("<table width=\"100%\" style=\"width:100%;text-align:left;border-collapse:collapse;border-spacing:0;font-size:12px;\" >\n");
			sb2.append("<tr>\n"
					+ "<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"18%\"><div align=\"center\">用例总数</div></td>\n");
			sb2.append("<td   style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"22%\" align=\"center\"><div align=\"center\">通过数（pass）</div></td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  width=\"21%\" align=\"center\"><div align=\"center\">失败数(failed)</div></td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  width=\"21%\" align=\"center\"><div align=\"center\">跳过数(skip)</div></td>\n");
			sb2.append("<td   style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"18%\" align=\"center\"><div align=\"center\">通过率</div></td>\n</tr>\n");
			sb2.append(" <tr >\n<td style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" ><div align=\"center\">");
			sb2.append("<label style=\"color:blue\" id=\"total_num\">");
			sb2.append(passArrayList.size()+failedArrayList.size()+skipArrayList.size());
			sb2.append("</label>");
			sb2.append("</div></td>\n<td style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"><div align=\"center\">");
			sb2.append("<label style=\"color:green\" id=\"passed_num\""
					+ "value=\""
					+ passArrayList.size()
					+ "\">");
			sb2.append(passArrayList.size());
			sb2.append("</label>");
			sb2.append("</div></td>\n <td style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"><div align=\"center\">");
			sb2.append("<label style=\"color:red\" id=\"failed_num\""
					+ "value=\""
					+ failedArrayList.size()
					+ "\">");
			sb2.append(failedArrayList.size());
			sb2.append("</label>");
			sb2.append("</div></td>\n<td style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"><div align=\"center\">");
			sb2.append("<label style=\"color:gray\" id=\"skiped_num\""
					+ "value=\""
					+skipArrayList.size()
					+ "\">");
			sb2.append(skipArrayList.size());
			sb2.append("</label>");
			sb2.append("</div></td>\n<td style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"><div align=\"center\">");
			DecimalFormat dfDecimalFormat1=new DecimalFormat("######0.00");
			float total1=passArrayList.size()+failedArrayList.size()+skipArrayList.size();
			float s1=passArrayList.size()/total1;
			sb2.append(dfDecimalFormat1.format(s1*100));
			sb2.append("%");
			sb2.append("</div></td>\n</tr>\n</table>\n");

			//报表标题
			/**
			 * 报表标题
			 */
			sb2.append("<table style=\"width:100%;text-align:left;border-collapse:collapse;border-spacing:0;font-size:10px;\">\n");
			sb2.append("<tr>\n<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"2%\" class=\"SEQ\">序号</td>\n");
			sb2.append("<td   style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"17%\" class=\"case_funtcion_td\">功能模块</td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"22%\" class=\"case_name_td\">用例名称</td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  width=\"3%\"  align=\"left\">测试结果</td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  width=\"28%\" class=\"case_result_beizhu_td\">备注</td>\n");
			sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" width=\"28%\" class=\"case_time\">耗时</td>\n");
			sb2.append("</tr>\n");

			/**
			 *  失败测试用例展示
			 */
			for (int j = 0; j < failedArrayList.size(); j++)
			{
				if(sb.length()!=0){
					sb2.append("\r\n");
				}
				float t=failedArrayList.get(j).getEndMillis()-failedArrayList.get(j).getStartMillis();
				String testMethodFullName=failedArrayList.get(j).getTestClass().getName()+"."+failedArrayList.get(j).getMethod().getMethodName();
				if (!failedArrayList.get(j).getMethod().getDescription().isEmpty()) {

					if (failedArrayList.get(j).getParameters().length>0) {
						sb2.append("<tr  style=\"color:red\" id=\""+testMethodFullName+"."+failedArrayList.get(j).getEndMillis()+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  class=\"SEQ\">"+(j+1)+"</td>");
						Object[] parameters=failedArrayList.get(j).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+"</td>\n");
						}
					}
					else {
						sb2.append("<tr  style=\"color:red\" id=\""+testMethodFullName+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"SEQ\">"+(j+1)+"</td>");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\"  class=\"case_funtcion_td\">"+failedArrayList.get(j).getTestContext().getName()+"</td>\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+failedArrayList.get(j).getMethod().getDescription()+"</td>\n");
					}
					sb2.append(" ")
							.append("<td class=\"case_result_td\" ><label class=\"failed_label\" id=\"failedresult_"+failedArrayList.get(j).getMethod().getMethodName()+"\">"+this.getStatus(failedArrayList.get(j).getStatus())+"</label><br /></td>"+"\n");
					if (failedArrayList.get(j).getParameters().length>0) {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据："+failedArrayList.get(j).getParameters()[0]+"</td>\n");
					}
					else {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}
					sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_time_td\">"+formateTime((long) t)+"</td>\n");
					//点击失败按钮，弹出失败信息

					sb2.append("<td colspan=\"3\" style=\"display:none\">")
							.append("</td>\n")
							.append("</tr>\n");
				}


			}
			//失败结果展示--end
			//成功结果展示--start
			/**
			 * 成功结果展示
			 */
			for (int i =0; i <passArrayList.size(); i++)
			{
				if(sb.length()!=0){
					sb2.append("\r\n");
				}
				float t=passArrayList.get(i).getEndMillis()-passArrayList.get(i).getStartMillis();
				String testMethodFullName=passArrayList.get(i).getTestClass().getName()+"."+passArrayList.get(i).getMethod().getMethodName();
				if (!passArrayList.get(i).getMethod().getDescription().isEmpty()) {
					if (passArrayList.get(i).getParameters().length>0) {
						sb2.append("<tr style=\"color:green\" id=\""+testMethodFullName+"."+passArrayList.get(i).getEndMillis()+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"SEQ\">"+(i+failedArrayList.size()+1)+"</td>");
						Object[] parameters=passArrayList.get(i).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+"</td>\n");
						}
					}
					else {
						sb2.append("<tr  style=\"color:green\"  id=\""+testMethodFullName+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"SEQ\">"+(i+failedArrayList.size()+1)+"</td>");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+passArrayList.get(i).getTestContext().getName()+"</td>\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+passArrayList.get(i).getMethod().getDescription()+"</td>\n");
					}
					sb2.append(" ")
							.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_td\"><label class=\"passed_label\" id=\"passedresult_"+passArrayList.get(i).getMethod().getMethodName()+"\">"+this.getStatus(passArrayList.get(i).getStatus())+"</label><br /></td>"+"\n");

					if (passArrayList.get(i).getParameters().length>0) {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据："+passArrayList.get(i).getParameters()[0]+"</td>\n");
					}
					else {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}

					sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_time_td\">"+formateTime((long) t)+"</td>\n");
					sb2.append("</tr>\n");
				}
			}
			if(sb.length()!=0){
				sb2.append("\r\n");
			}
          /* sb2.append("</table>\n");*/
			//成功结果展示--end

			//--跳过结果展示--start
			/**
			 * 跳过结果展示
			 */
			for (int j =  0; j < skipArrayList.size(); j++)
			{
				if(sb.length()!=0){
					sb2.append("\r\n");
				}
				float t=skipArrayList.get(j).getEndMillis()-skipArrayList.get(j).getStartMillis();
				String testMethodFullName=skipArrayList.get(j).getTestClass().getName()+"."+skipArrayList.get(j).getMethod().getMethodName();
				if (!skipArrayList.get(j).getMethod().getDescription().isEmpty()) {
					if (skipArrayList.get(j).getParameters().length>0) {
						sb2.append("<tr style=\"color:gray\" id=\""+testMethodFullName+"."+skipArrayList.get(j).getEndMillis()+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"SEQ\">"+(j+failedArrayList.size()+passArrayList.size()+1)+"</td>");
						Object[] parameters=skipArrayList.get(j).getParameters();
						if (parameters[0].toString().contains("院")||parameters[0].toString().contains("所")||parameters[0].toString().contains("医")
								||parameters[0].toString().contains("病")||parameters[0].toString().contains("治")) {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+":"+parameters[0].toString()+"</td>\n");
						}
						else {
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
							sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+"</td>\n");
						}

					}
					else {
						sb2.append("<tr style=\"color:gray\" id=\""+testMethodFullName+"\">\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"SEQ\">"+(j+fullResults.size()+passArrayList.size()+1)+"</td>");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_funtcion_td\">"+skipArrayList.get(j).getTestContext().getName()+"</td>\n");
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_name_td\">"+skipArrayList.get(j).getMethod().getDescription()+"</td>\n");
					}
					sb2.append(" ");
					if (skipArrayList.get(j).getParameters().length>0) {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据："+skipArrayList.get(j).getParameters()[0]+"</td>\n");
					}
					else {
						sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_result_beizhu_td\">"+"输入数据：无"+"</td>\n");
					}

					sb2.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" class=\"case_time_td\">"+formateTime((long) t)+"</td>\n")
							.append("<td  style=\"border:1.5px;border-color:gray; word-break:break-all;border-style: solid;height:20px;\" colspan=\"3\" style=\"display:none\">"+""+"</td>\n")
							.append("</tr>\n");
				}
			}
			if(sb.length()!=0){
				sb2.append("\r\n");
			}

			sb2.append("</table>\n");
			//--跳过结果--end
			sb2.append("</table>\n</div>\n");
			sb2.append("</div>\n"
					+ "<div id=\"footer\" style=\"font-size:14px\" >技术支持：Copyright © 2014 Webdriver中文社区.Inc</div>");
			sb2.append("</body>\n</html>\n");
			System.out.println("收件人地址："+Recipients);
			SendMail sendMail=new SendMail();
			String smtpUserName="";
			String smtpPassWord="";
			String smtpHost="";
			String smtpPort="";
			String mailTitle="";
			try {
				smtpUserName=getTestngParametersValue(Config.path,"smtpUserName");
				smtpPassWord=getTestngParametersValue(Config.path,"smtpPassWord");
				smtpHost=getTestngParametersValue(Config.path,"smtpHost");
				smtpHost=getTestngParametersValue(Config.path,"smtpHost");
				mailTitle=getTestngParametersValue(Config.path,"mailTitle");
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			sendMail.sendmessage(smtpUserName,smtpPassWord, smtpHost, smtpPort, smtpUserName,Recipients,mailTitle, sb2.toString());
			//--发送html报表邮件--结束




		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private String getStatus(int status){
		String statusString = null;
		switch (status) {
			case 1:
				statusString = "p";
				break;
			case 2:
				statusString = "f";
				break;
			case 3:
				statusString = "s";
				break;
			default:
				break;
		}
		return statusString;
	}

	private String formatDate(long date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}
	public static String formatDate(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmssSSS");
		return formatter.format(date).toString();
	}
	public static String formateTime(long ms)
	{
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		StringBuffer sb = new StringBuffer();
		if(day > 0) {
			sb.append(day+"天");
		}
		if(hour > 0) {
			sb.append(hour+"小时");
		}
		if(minute > 0) {
			sb.append(minute+"分");
		}
		if(second > 0) {
			sb.append(second+"秒");
		}
		if(milliSecond > 0) {
			sb.append(milliSecond+"毫秒");
		}
		return sb.toString();

	}
	private String getTestngParametersValue(String path,String ParametersName) throws DocumentException, IOException
	{
		File file = new File(path);
		if (!file.exists()) {
			throw new IOException("Can't find " + path);

		}
		String value=null;
		SAXReader reader = new SAXReader();
		Document  document = reader.read(file);
		Element root = document.getRootElement();
		for (Iterator<?> i = root.elementIterator(); i.hasNext();)
		{
			Element page = (Element) i.next();
			if(page.attributeCount()>0)
			{
				if (page.attribute(0).getValue().equalsIgnoreCase(ParametersName))
				{
					value=page.attribute(1).getValue();
					//System.out.println(page.attribute(1).getValue());
				}
				continue;
			}
			//continue;
		}
		return value;

	}




}