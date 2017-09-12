package com.liferay.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

public class SiteHandlerModel {

	
	public static int getSiteCount() throws SystemException {
		//Use -19 because there are 19 technical sites that we don't want to show in the count.
		return GroupLocalServiceUtil.getGroupsCount()-19;
	}

	
	public void createSite(long companyId, long adminUserId,
			String newSiteName, int newSiteCount) {

		ServiceContext serviceContext = new ServiceContext();
		
		for (int currentSiteNumber = 1; currentSiteNumber <= newSiteCount; currentSiteNumber++) {
			try {
				
				//Add the group
				Group group = GroupLocalServiceUtil.addGroup(adminUserId, GroupConstants.DEFAULT_PARENT_GROUP_ID, 
						Group.class.getName(), 0, GroupConstants.DEFAULT_LIVE_GROUP_ID, newSiteName + currentSiteNumber, "", 
						GroupConstants.TYPE_SITE_OPEN, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, 
						"/" + newSiteName + currentSiteNumber, true, true, serviceContext);
				
				//Add the page
				Layout myPage =  LayoutLocalServiceUtil.addLayout(adminUserId, group.getGroupId(), false, 
				                                        0, "Page1", "Page1", "Page1", 
				                                        LayoutConstants.TYPE_PORTLET, false, "/page1", serviceContext);
				
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			} finally {
				try {
					System.out
							.println("Site count after site creation: "
									+ getSiteCount());
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
