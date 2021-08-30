package com.dxc.eproc.estimate.mocks;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.dxc.eproc.client.user.dto.ResponseDTODepartmentDTO;
import com.dxc.eproc.estimate.integration.TestUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class UserServiceMock {

	public static void setUpMockGetDepartmentUsingGET(WireMockServer mockService, Integer deptId,
			ResponseDTODepartmentDTO responseDTODepartmentDTO) throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/department/" + deptId))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(responseDTODepartmentDTO))));
	}
}
