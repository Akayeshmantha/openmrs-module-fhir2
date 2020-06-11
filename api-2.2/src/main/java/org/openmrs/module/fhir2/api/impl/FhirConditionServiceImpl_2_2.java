/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.api.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import ca.uhn.fhir.rest.annotation.Sort;
import ca.uhn.fhir.rest.api.SortSpec;
import ca.uhn.fhir.rest.param.DateRangeParam;
import ca.uhn.fhir.rest.param.QuantityAndListParam;
import ca.uhn.fhir.rest.param.ReferenceAndListParam;
import ca.uhn.fhir.rest.param.TokenAndListParam;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hl7.fhir.r4.model.Condition;
import org.openmrs.annotation.OpenmrsProfile;
import org.openmrs.module.fhir2.api.FhirConditionService;
import org.openmrs.module.fhir2.api.dao.FhirConditionDao;
import org.openmrs.module.fhir2.api.translators.ConditionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component
@Transactional
@Setter(AccessLevel.PACKAGE)
@Getter(AccessLevel.PROTECTED)
@OpenmrsProfile(openmrsPlatformVersion = "2.2.* - 2.*")
public class FhirConditionServiceImpl_2_2 extends BaseFhirService<Condition, org.openmrs.Condition> implements FhirConditionService {
	
	@Autowired
	private FhirConditionDao<org.openmrs.Condition> dao;
	
	@Autowired
	private ConditionTranslator<org.openmrs.Condition> translator;
	
	@Override
	public Collection<Condition> searchConditions(ReferenceAndListParam patientParam, ReferenceAndListParam subjectParam,
	        TokenAndListParam code, TokenAndListParam clinicalStatus, DateRangeParam onsetDate,
	        QuantityAndListParam onsetAge, DateRangeParam recordedDate, @Sort SortSpec sort) {
		return dao.searchForConditions(patientParam, subjectParam, code, clinicalStatus, onsetDate, onsetAge, recordedDate,
		    sort).stream().map(translator::toFhirResource).collect(Collectors.toList());
	}
	
	@Override
	public Condition saveCondition(Condition condition) {
		return translator.toFhirResource(dao.saveCondition(translator.toOpenmrsType(condition)));
	}
}
