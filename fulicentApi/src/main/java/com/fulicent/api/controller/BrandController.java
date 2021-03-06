package com.fulicent.api.controller;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fulicent.api.entity.BrandInfo;
import com.fulicent.api.entity.Brand;
import com.fulicent.api.service.BrandService;
import com.fulicent.common.entity.ApiResponseBody;
import com.fulicent.common.entity.ApiResponseStatus;
import com.fulicent.common.entity.MessageInfo;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
	private BrandService brandService;
	
	@Inject
	public BrandController(BrandService brandService){
		this.brandService=brandService;
	}
	
	//@CrossOrigin
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseBody> Brand(@RequestParam(name = "limit", defaultValue = "0") int limit,
            @RequestParam(name = "skip", defaultValue = "0") int skip,
            @RequestParam(value = "sort", required = false, defaultValue = "CategoryId") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            @RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId){
		List<Brand> brand=brandService.Brand(limit, skip, sort, order,categoryId);
		return new ResponseEntity<>(ApiResponseBody.builder()
				.status(new MessageInfo(ApiResponseStatus.RESOURCE_FOUND))
				.data(new BrandInfo(brand))
				.build(),
				HttpStatus.OK
				);
		
	}
}
