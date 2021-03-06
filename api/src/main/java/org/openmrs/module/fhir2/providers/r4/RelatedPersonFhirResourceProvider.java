/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.fhir2.providers.r4;

import javax.validation.constraints.NotNull;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.Setter;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.RelatedPerson;
import org.openmrs.module.fhir2.api.FhirRelatedPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("relatedPersonFhirR4ResourceProvider")
@Qualifier("fhirResources")
@Setter(AccessLevel.PACKAGE)
public class RelatedPersonFhirResourceProvider implements IResourceProvider {
	
	@Autowired
	private FhirRelatedPersonService relatedPersonService;
	
	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return RelatedPerson.class;
	}
	
	@Read
	public RelatedPerson getRelatedPersonById(@IdParam @NotNull IdType id) {
		RelatedPerson relatedPerson = relatedPersonService.get(id.getIdPart());
		if (relatedPerson == null) {
			throw new ResourceNotFoundException("Could not find related person with Id " + id.getIdPart());
		}
		return relatedPerson;
	}
}
