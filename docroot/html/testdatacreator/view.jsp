<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@page import="com.liferay.portal.kernel.util.Constants" %>
<%@page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@page import="com.liferay.portal.kernel.util.StringPool" %>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.theme.PortletDisplay"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="javax.portlet.ActionRequest"%>
<%@page import="javax.portlet.PortletURL" %>

<portlet:defineObjects />

<portlet:resourceURL var="resourceUrl1">
	<portlet:param name="portletAction" value="createUsers"/> 
	</portlet:resourceURL>
	
	<a href="#" onclick="callServeResource()">Create specified nr of users with specified name</a>
	<script type="text/javascript">
	function callServeResource(){
		AUI().use('aui-io-request', function(A){
			A.io.request('${resourceUrl1}');	 
		});
	}
	</script>
	
	
	<br>-------------------------------<br>
	
	<portlet:resourceURL var="resourceUrl2">
	<portlet:param name="portletAction" value="deleteUsers"/> 
	</portlet:resourceURL>
	
	<a href="#" onclick="callServeResource2()">Delete all non-admin users</a>
	<script type="text/javascript">
	function callServeResource2(){
		AUI().use('aui-io-request', function(A){
			A.io.request('${resourceUrl2}');	 
		});
	}
	</script>
	
	<br>-------------------------------<br>
	