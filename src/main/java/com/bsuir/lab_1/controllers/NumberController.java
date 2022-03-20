package com.bsuir.lab_1.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.bsuir.lab_1.DTO.NumberDTO;
import com.bsuir.lab_1.counter.RequestCounter;
import com.bsuir.lab_1.models.CustomNumberModel;
import com.bsuir.lab_1.services.NumberService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@ControllerAdvice
public class NumberController {
	private static final Logger logger = LogManager.getLogger(NumberController.class);

	RequestCounter counter = new RequestCounter(0);

	@Autowired
  private NumberService numberService;

	@GetMapping(value = "/check/{id}")
	public @ResponseBody CustomNumberModel getNumber(@Valid @PathVariable("id") int processId) throws IllegalArgumentException {
		return numberService.getProcessedNumber(processId);
	}

	@PostMapping("/asyncCheck")
	public ResponseEntity<Map<String, Integer>> asyncCheckNumber(@Valid @RequestBody NumberDTO body) throws IllegalArgumentException {
		Integer processId = numberService.asyncProcessNumber(body.number);

		Map<String, Integer> result = new HashMap<>();

		result.put("recordId", processId);

		return ResponseEntity.ok(result);	
	}

	@PostMapping("/check")
	public ResponseEntity<Map<String, Boolean>> checkNumber(@Valid @RequestBody NumberDTO body) throws IllegalArgumentException {
		counter.increment();

		Map<String, Boolean> result = numberService.processNumber(body.number);

		logger.info("POST /check - Result: " + result);
		logger.info("Current number of requests:" + counter.getNumber());

		counter.decrement();

		return ResponseEntity.ok(result);	
	}

	@PostMapping("/bulkCheck")
	public ResponseEntity<Map<String, Object>> bulkCheckNumber(@Valid @RequestBody List<NumberDTO> bodyList) throws IllegalArgumentException {
		Supplier<Stream<Double>> numberStreamSupplier = () -> bodyList.stream().map(body -> body.number);

		Stream<Map<String, Boolean>> resultListStream = numberService.processNumberStream(numberStreamSupplier.get());

		List<Map<String, Boolean>> resultList = resultListStream.collect(Collectors.toList());

		logger.info("POST /bulkCheck - Response: " + resultList);

		Map<String, Object> result = new HashMap<>();

		result.put("data", resultList);
		result.put("minNumber", numberService.getMinimumNumber(numberStreamSupplier.get()));
		result.put("maxNumber", numberService.getMaximumNumber(numberStreamSupplier.get()));
		result.put("averageNumber", numberService.getAverageNumber(numberStreamSupplier.get()));

		return ResponseEntity.ok(result);	
	}
}

