<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<title>设备通道列表</title>
</head>


<body>
<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="13">
    <em>设备通道列表(<s:property value="#request.deviceName" />)</em>
    </th>
  </tr>
  <tr class="title">
  	<td>防区编号 </td>
	<td>防区名称</td>
	<td>防区类型</td>
	<td>防区描述</td>
    <td>联系电话 </td>
    <td>防区联系人</td>
    <td>防区地理位置</td>
    <td>布撤防状态</td>
    <td>电压值</td>
    <td>张力值</td>
    <td>阈值</td>
    <td>防区style</td>
    <td>脉冲防区参数</td>

  </tr>
 <s:if test="null!=zones&&zones.size()>0">
 <s:iterator value="zones" id="zone">
  <tr>
    <td>${zone.zoneNo}</td>
    <td>${zone.zoneName}</td>
    <td>${zone.typeName}</td>
    <td>${zone.zoneDesc}</td>
    <td>${zone.zonePhone}</td>
    <td>${zone.zoneContactor}</td>
    <td>${zone.zoneLoc}</td>
    <td>${zone.stateName}</td>
    
    <td>${zone.zoneVmp}</td>
    <td>${zone.zoneStrain}</td>
    <td>${zone.zoneStrainVpt}</td>
    <td>${zone.styleName}</td>
    <td>${zone.zoneParam}</td>
  </tr>
  </s:iterator>
  </s:if>
</table>
</form>
</body>

</html>
