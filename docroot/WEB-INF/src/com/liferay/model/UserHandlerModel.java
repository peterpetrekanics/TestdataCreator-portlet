package com.liferay.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.List;
import java.util.Locale;

public class UserHandlerModel {
	public void createUser(long companyId, long adminUserId,
			String newUserName, String newUserCount) {
		System.out.println("companyid: " + companyId);

		int userCount = 0;
		if (!newUserCount.equalsIgnoreCase("")) {
			System.out.println("userCount is valid");
			userCount = Integer.parseInt(newUserCount);

//			long userId;
//			String name;
//			String description;
			ServiceContext serviceContext = null;

			try {
				for (int i = 1; i <= userCount; i++) {
					try {
						UserLocalServiceUtil.addUser(adminUserId, // creatorUserId,
								companyId, // companyId,
								false, // autoPassword,
								"test", // password1,
								"test", // password2,
								true, // autoScreenName,
								null, // screenName,
								newUserName + i + "@liferay.com", // emailAddress,
								0L, // facebookId,
								null, // openId,
								Locale.ENGLISH, // locale,
								"Test", // firstName,
								null, // middleName,
								newUserName + i, // lastName,
								0, // prefixId,
								0, // suffixId,
								true, // male,
								1, // birthdayMonth,
								1, // birthdayDay,
								1977, // birthdayYear,
								null, // jobTitle,
								null, // groupIds,
								null, // organizationIds,
								null, // roleIds,
								null, // userGroupIds,
								false, // sendEmail,
								serviceContext); // serviceContext
						System.out.println("The user: " + newUserName + i
								+ " has been created");

					} catch (Exception e) {
						System.out.println("exception" + e);

						e.printStackTrace();
					}

				}
			} finally {
				try {
					System.out.println("User count after: "
							+ getUserCount());
				} catch (SystemException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public int getUserCount() throws SystemException {
		
		return UserLocalServiceUtil.getUsersCount();
	}

	public void deleteNonAdminUsers(long companyId)  {
		List<User> myUsers = null;
		try {
			myUsers = UserLocalServiceUtil.getUsers(0, getUserCount());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		for (User user : myUsers) {
			if (user.isDefaultUser() || PortalUtil.isOmniadmin(user.getUserId())) {
				System.out.println("Skipping user " + user.getScreenName());
			} else {
				User userToDelete = user;

				System.out.println("Deleting user " + userToDelete.getScreenName());
				try {
					UserLocalServiceUtil.deleteUser(userToDelete);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
