package com.dxc.eproc.estimate.mocks;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.dxc.eproc.client.sor.dto.AreaWeightageMasterDTO;
import com.dxc.eproc.client.sor.dto.FinancialYearsDTO;
import com.dxc.eproc.client.sor.dto.MaterialChargesDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterLoadUnloadDetailsDTO;
import com.dxc.eproc.client.sor.dto.MaterialMasterRoyaltyChargesDetailsDTO;
import com.dxc.eproc.client.sor.dto.NotesMasterDTO;
import com.dxc.eproc.client.sor.dto.SorCategoryDTO;
import com.dxc.eproc.client.sor.dto.WorkItemDTO;
import com.dxc.eproc.estimate.integration.TestUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class SorServiceMock {

	public static void setUpMockGetWorkItemUsingGET(WireMockServer mockService, Long catWorkSorItemId,
			WorkItemDTO workItemDTO) throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/item/" + catWorkSorItemId))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(workItemDTO))));
	}

	public static void setUpMockGetSorCategoryUsingGET(WireMockServer mockService, Long sorCategoryId,
			SorCategoryDTO sorCategoryDTO) throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/category/" + sorCategoryId))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(sorCategoryDTO))));
	}

	public static void setUpMockGetFinancialYearByCategoryUsingGET(WireMockServer mockService, Long sorCategoryParentId,
			FinancialYearsDTO financialYearsDTO) throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/fy/sor-category/" + sorCategoryParentId))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(financialYearsDTO))));
	}

	public static void setUpMockGetMaterialDetailsByWorkItemUsingGET(WireMockServer mockService, Long catWorkSorItemId,
			List<MaterialMasterDetailsDTO> materialMasterDetailsList) throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/materials/workItem/" + catWorkSorItemId))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(materialMasterDetailsList))));
	}

	public static void setUpMockGetAreaWeightageMastersforCategoryUsingGET(WireMockServer mockService,
			Long sorCategoryParentId, List<AreaWeightageMasterDTO> areaWeightageMasterList) throws IOException {

		mockService
				.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/areaWeightages/sor-category/" + sorCategoryParentId))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(areaWeightageMasterList))));
	}
	
	public static void setUpMockGetAreaWeightageMasterUsingGET(WireMockServer mockService,
			Long areaWeightageMasterId, AreaWeightageMasterDTO areaWeightageDTO) throws IOException {

		mockService
				.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/areaWeightage/" + areaWeightageMasterId))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(areaWeightageDTO))));
	}

	public static void setUpMockGetLeadOrLiftChargesForWorkItemUsingGET(WireMockServer mockService,
			Long catWorkSorItemId, String chargeType, List<MaterialMasterDetailsDTO> materialMasterDetailsList)
			throws IOException {

		mockService.stubFor(WireMock
				.get(WireMock
						.urlEqualTo("/v1/api/materials/workItem/" + catWorkSorItemId + "/charge-type/" + chargeType))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(materialMasterDetailsList))));
	}

	public static void setUpMockGetLeadChargesForMaterialUsingPOST(WireMockServer mockService, Long catWorkSorItemId,
			MaterialChargesDTO materialChargesDTO) throws IOException {

		mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/api/material/" + catWorkSorItemId + "/lead/"))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody(TestUtil.convertObjectToJsonBytes(materialChargesDTO))));
	}

	public static void setUpMockGetLiftChargesForMaterialUsingGET(WireMockServer mockService, Long materialId,
			BigDecimal liftDistanceSlabValue, MaterialChargesDTO materialChargesDTO) throws IOException {

		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/v1/api/material/" + materialId + "/lift/" + liftDistanceSlabValue))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(materialChargesDTO))));
	}

	public static void setUpMockGetLoadUnloadforMaterialDetailsByWorkItemUsingGET(WireMockServer mockService,
			Long catWorkSorItemId, List<MaterialMasterLoadUnloadDetailsDTO> materialMasterLdUdDetailsDTOList)
			throws IOException {

		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/v1/api/materials/workItem/" + catWorkSorItemId + "/load-unload"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(materialMasterLdUdDetailsDTOList))));
	}

	public static void setUpMockGetRoyaltyforMaterialDetailsByWorkItemUsingGET(WireMockServer mockService,
			Long catWorkSorItemId, List<MaterialMasterRoyaltyChargesDetailsDTO> materialMasterRoyaltyChargesDetailsList)
			throws IOException {

		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/v1/api/materials/workItem/" + catWorkSorItemId + "/royalty"))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(materialMasterRoyaltyChargesDetailsList))));
	}
	
	public static void setUpMockGetNotesMasterByItemAndChargeTypeUsingGET(WireMockServer mockService,
			Long catWorkSorItemId, String chargeType, List<NotesMasterDTO> notesMasterDTOList)
			throws IOException {

		mockService.stubFor(
				WireMock.get(WireMock.urlEqualTo("/v1/api/item/" + catWorkSorItemId + "/notes-masters/charge-type/" + chargeType))
						.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody(TestUtil.convertObjectToJsonBytes(notesMasterDTOList))));
	}
}
