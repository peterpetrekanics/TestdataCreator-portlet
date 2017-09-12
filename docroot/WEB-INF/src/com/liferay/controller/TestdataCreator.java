//Tested, works on Liferay 6.2 EE SP14
package com.liferay.controller;

import com.liferay.model.JournalArticleModel;
import com.liferay.model.SiteHandlerModel;
import com.liferay.model.UserHandlerModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
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
		
		// Getting the default site's group
		long defGroupId = 0;
		try {
			defGroupId = GroupLocalServiceUtil.getGroup(companyId, "Guest").getGroupId();
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		// Getting ThemeDisplay and ServiceContext
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(
					JournalArticle.class.getName(), resourceRequest);
		} catch (PortalException e1) {
			
			e1.printStackTrace();
		} catch (SystemException e1) {
			
			e1.printStackTrace();
		}
		
		// Initializing the Handlers
		UserHandlerModel userHandler = new UserHandlerModel();
		SiteHandlerModel siteHandler = new SiteHandlerModel();
		JournalArticleModel journalArticleHandler = new JournalArticleModel();

		// Retrieving the action's name that was initiated by the user
		String performAction = ParamUtil.get(resourceRequest, "portletAction", "");

		// Performing the action based on user input
		switch (performAction) {
		case "createUsers":
			String newUserName = ParamUtil.getString(resourceRequest, "newUserName");
			int newUserCount = ParamUtil.getInteger(resourceRequest, "newUserCount");
			if(newUserCount>0) userHandler.createUser(companyId, adminUserId, newUserName, newUserCount, serviceContext);
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
			if(newUserGroupCount>0) userHandler.createUserGroup(companyId, adminUserId, newUserGroupName, newUserGroupCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("Usergroup creation finished");
			break;

		case "assignUsersToUserGroups":
			int assignedUserCount = ParamUtil.getInteger(resourceRequest, "assignedUserCount");
			if(assignedUserCount>0)	userHandler.assignUsersToUserGroups(companyId, assignedUserCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("User assigning finished");
			break;
			
		case "deleteUserGroups":
			userHandler.deleteUserGroups(companyId);
			break;
			
		case "createSites":
			String newSiteName = ParamUtil.getString(resourceRequest, "newSiteName");
			int newSiteCount = ParamUtil.getInteger(resourceRequest, "newSiteCount");
			if(newSiteCount>0) siteHandler.createSite(companyId, adminUserId, newSiteName, newSiteCount);
			resourceResponse.setContentType("text/html");
	        writer = resourceResponse.getWriter();
	        writer.println("Site creation finished");
			break;
			
		case "createArticles":
			String newArticleName = ParamUtil.getString(resourceRequest, "newArticleName");
			int newArticleCount = ParamUtil.getInteger(resourceRequest, "newArticleCount");
			for (int i = 1; i <= newArticleCount; i++) {
				journalArticleHandler.createArticle(companyId, defGroupId, adminUserId, newArticleName+i, newArticleCount, themeDisplay, serviceContext);
			}
			writer = resourceResponse.getWriter();
	        writer.println("Article creation finished");
			break;						
					
		default:
			;
			break;
		}
		System.out.println("The following button was pressed: " + performAction);
		
		System.out.println("serveResource ends..");
	}
}
