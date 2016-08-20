package com.liferay.controller;

import com.liferay.model.UserHandlerModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * Portlet implementation class TestdataCreator
 */
public class TestdataCreator extends MVCPortlet {
	static long companyId;
	static User adminUser;
	long adminUserId;
	PrintWriter writer;

	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {
		System.out.println("processAction starts..");

		System.out.println("processAction ends..");
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		// TODO Auto-generated method stub
		// super.serveResource(resourceRequest, resourceResponse);
		System.out.println("serveResource starts..");

		// Getting the value for companyId
		Company company = null;
		try {
			company = CompanyLocalServiceUtil.getCompanies().iterator().next();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		companyId = company.getCompanyId();

		// Getting the id of the admin user
		try {
			adminUser = UserLocalServiceUtil.getUserByEmailAddress(companyId, "test@liferay.com");
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		adminUserId = adminUser.getUserId();
		
		UserHandlerModel userHandler = new UserHandlerModel();

		
		String performAction = ParamUtil.get(resourceRequest, "portletAction", "");

		switch (performAction) {
		case "createUsers":
			String newUserName = ParamUtil.getString(resourceRequest, "newUserName");
			int newUserCount = ParamUtil.getInteger(resourceRequest, "newUserCount");
			userHandler.createUser(companyId, adminUserId, newUserName, newUserCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("User creation finished");
			break;
			
		case "deleteUsers":
			userHandler.deleteNonAdminUsers(companyId);
			break;
			
		case "createUserGroups":
			String newUserGroupName = ParamUtil.getString(resourceRequest, "newUserGroupName");
			int newUserGroupCount = ParamUtil.getInteger(resourceRequest, "newUserGroupCount");
			userHandler.createUserGroup(companyId, adminUserId, newUserGroupName, newUserGroupCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("Usergroup creation finished");
			break;

		case "assignUsersToUserGroups":
			int assignedUserCount = ParamUtil.getInteger(resourceRequest, "assignedUserCount");
			System.out.println("auc: " + assignedUserCount);
			userHandler.assignUsersToUserGroups(companyId, assignedUserCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("User assigning finished");
			break;

		default:
			;
			break;
		}
		System.out.println("The following button was pressed: " + performAction);

		
		
		
		String myUserGroupName = "testUserGroup";
		long userGroupId = 0;
		try {
			UserGroup myUserGroup = UserGroupLocalServiceUtil.getUserGroup(companyId, myUserGroupName);
			userGroupId = myUserGroup.getUserGroupId();
		} catch (PortalException e) {
		} catch (SystemException e) {
		}
		// System.out.println(userGroupId);

		System.out.println("serveResource ends..");
	}

	

	
	public void addEmployee(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
			 
			       System.out.println("addEmpl");
			 
			       
			}


}
