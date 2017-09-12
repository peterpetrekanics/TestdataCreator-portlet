package com.liferay.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class JournalArticleModel {

	public void createArticle(long companyId, long defGroupId,
			long adminUserId, String newArticleName, int newArticleCount,
			ThemeDisplay themeDisplay, ServiceContext serviceContext) {

		// JournalArticleLocalServiceUtil.addArticle(userId, groupId, folderId,
		// classNameId, classPK, articleId,
		// autoArticleId, version, titleMap, descriptionMap, content, type,
		// ddmStructureKey, ddmTemplateKey,
		// layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
		// displayDateHour, displayDateMinute,
		// expirationDateMonth, expirationDateDay, expirationDateYear,
		// expirationDateHour, expirationDateMinute,
		// neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
		// reviewDateHour, reviewDateMinute, neverReview,
		// indexable, smallImage, smallImageURL, smallImageFile, images,
		// articleURL, serviceContext)

		long classNameId = 0;
		long classPK = 0;

		// themeDisplay.getLayout().getLayoutPrototypeUuid(), // layoutUuid

		// Map<Locale, String> titleMap;
		Calendar date = Calendar.getInstance();
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		// titleMap.put(Locale.ENGLISH, "Service");
		titleMap.put(Locale.US, newArticleName);
		// Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		// descriptionMap.put(Locale.GERMAN, "Der Service");

		String createdContentXml = "test1";
		createdContentXml = LocalizationUtil.updateLocalization(
				StringPool.BLANK, "static-content", createdContentXml);

		JournalArticle journal;

		Group group = null;
		try {
			group = GroupLocalServiceUtil.getGroup(defGroupId);
		} catch (PortalException e) {

			e.printStackTrace();
		} catch (SystemException e) {

			e.printStackTrace();
		}

		long groupId = group.getGroupId();

		List<DDMStructure> structures = null;
		DDMStructure myStructure = null;
		try {
			structures = DDMStructureLocalServiceUtil.getStructures(groupId, 0,
					10);
		} catch (SystemException e1) {

			e1.printStackTrace();
		}
		String structureKey = null;
		long structureId;
		String templateKey = null;
		long structureClassNameId = 0;
		for (DDMStructure structure : structures) {
			// System.out.println("lista: " + structure.getUserName());
			if (structure.getUserName().equalsIgnoreCase("test test")) {
				myStructure = structure;
			}
		}
		if (myStructure != null) {
			structureKey = myStructure.getStructureKey();
			structureId = myStructure.getStructureId();
			try {
				structureClassNameId = DDMStructureLocalServiceUtil
						.getDDMStructure(myStructure.getStructureId())
						.getClassNameId();
			} catch (PortalException e2) {

				e2.printStackTrace();
			} catch (SystemException e2) {

				e2.printStackTrace();
			}
			List<DDMTemplate> templates = null;
			try {
				templates = DDMTemplateLocalServiceUtil.getDDMTemplates(0, 20);
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
			for (DDMTemplate template : templates) {
				// System.out.println("lista: " + template.getUserName());
				if (template.getClassPK() == structureId) {
					templateKey = template.getTemplateKey();
				}
			}

		}

		System.out.println("str: " + structureKey + " tpl: " + templateKey);

		try {
			journal = JournalArticleLocalServiceUtil.addArticle(adminUserId,
			// themeDisplay.getLayout().getGroupId(),
			// group.getGroupId(),
					groupId, 0l, // classNameId
					classNameId, // classPK
					classPK, "", //
					true, // autoArticleId
					1D, // version
					titleMap, // hashmap
					null, // descriptionMap
					createdContentXml, // content
					"general", // type
					structureKey, // structureId
					templateKey, // templateId
					themeDisplay.getLayout().getLayoutPrototypeUuid(), // layoutUuid
					date.get(Calendar.MONTH), // |
					date.get(Calendar.DAY_OF_MONTH), // |
					date.get(Calendar.YEAR), // | --> displayDate
					date.get(Calendar.HOUR), // |
					date.get(Calendar.MINUTE), // |
					0, // |
					0, // |
					0, // |--> expireDate
					0, // |
					0, // |
					true, // neverExpire
					0, // |
					0, // |
					0, // |--> ReviewDate
					0, // |
					0, // |
					true, // neverReview
					true, // indexable
					false, // smallImage
					null, // null |
					null, // null | ==> imsages
					null, // null |
					null, // null |
					serviceContext // serviceContext
					);
		} catch (PortalException e) {

			e.printStackTrace();
		} catch (SystemException e) {

			e.printStackTrace();
		}
	}

}
