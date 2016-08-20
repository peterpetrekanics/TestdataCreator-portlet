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

TODO: Display current user count

<portlet:defineObjects />

<portlet:resourceURL var="resourceUrl1">
	<portlet:param name="portletAction" value="createUsers"/> 
	</portlet:resourceURL>

<form name="newUserCreatorForm" id="newusers">
Enter a name for the new user(s):
<input type="text" name="newUserName"/> <br>
Enter how many users should be created
<input type="number" name="newUserCount" min="1" value="5">
<br/>
<input type="button" value="Submit" onclick="callServeResource1()">
</form>
	
<script type="text/javascript">
function callServeResource1(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl1.toString()%>', {
               method: 'post',
               form: {
                   id: 'newusers'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                    }
               }
            });
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


<portlet:resourceURL var="resourceUrl3">
	<portlet:param name="portletAction" value="createUserGroups"/> 
	</portlet:resourceURL>

<form name="newUserGroupCreatorForm" id="newUserGroups">
Enter a name for the new userGroup(s):
<input type="text" name="newUserGroupName"/> <br>
Enter how many userGroups should be created
<input type="number" name="newUserGroupCount" min="1" value="5">
<br/>
<input type="button" value="Submit" onclick="callServeResource3()">
</form>
	
<script type="text/javascript">
function callServeResource3(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl3.toString()%>', {
               method: 'post',
               form: {
                   id: 'newUserGroups'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                    }
               }
            });
    });
}
</script>
	
	<br>-------------------------------<br>

<portlet:resourceURL var="resourceUrl4">
	<portlet:param name="portletAction" value="assignUsersToUserGroups"/> 
	</portlet:resourceURL>

<form name="UserAssignerForm" id="userAssigner">
Enter how many users should be assigned to each userGroup: <br>
<input type="number" name="assignedUserCount" min="1" value="5">
<br/>
<input type="button" value="Submit" onclick="callServeResource4()">
</form>
	
<script type="text/javascript">
function callServeResource4(){
    AUI().use('aui-io-request', function(A){
        A.io.request('<%=resourceUrl4.toString()%>', {
               method: 'post',
               form: {
                   id: 'userAssigner'
               },
               on: {
                    success: function() {
                     alert(this.get('responseData'));
                    }
               }
            });
    });
}
</script>

