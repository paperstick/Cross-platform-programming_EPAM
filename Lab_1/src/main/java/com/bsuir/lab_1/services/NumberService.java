package com.bsuir.lab_1.services;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import com.bsuir.lab_1.CustomNumber;
import com.bsuir.lab_1.models.CustomNumberModel;
import com.bsuir.lab_1.CustomNumberCache;
import com.bsuir.lab_1.repositories.NumberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NumberService {
  @Autowired
  private CustomNumberCache numberCache;

  @Autowired
  private NumberRepository numberRepository;

  public CustomNumberModel getProcessedNumber (int numberId) {
    return numberRepository.findByProcessId(numberId).get();
  }

  @Async
  public Integer asyncProcessNumber (Double number) {
    Integer id = new Random().nextInt();

    this.processNumber(number, id);

    return id;
  }

  public Integer processNumber (Double number, Integer processId) {
    Optional<CustomNumberModel> foundNumber = numberRepository.findByNumber(number);

    Map<String, Boolean> result = new HashMap<>();

    if (foundNumber.isPresent()) {
      result.put("isEven", foundNumber.get().getIsEven());
		  result.put("isPrime", foundNumber.get().getIsPrime());

      numberCache.cacheResult(number, result);
      
      return processId;
    }

    CustomNumber customNumber = new CustomNumber(number);

		result.put("isEven", customNumber.isEven());
		result.put("isPrime", customNumber.isPrime());

    CustomNumberModel numberModel = new CustomNumberModel();

    numberModel.setProcessId(processId);
    numberModel.setNumberInfo(number, result.get("isEven"), result.get("isPrime"));
    numberRepository.save(numberModel);

    numberCache.cacheResult(number, result);

    return processId;
  }

  public Map<String, Boolean> processNumber (Double number) {
    if (numberCache.isContain(number)) {
			Map<String, Boolean> cachedResult = numberCache.getCachedResult(number);

			return cachedResult;
		}

    Optional<CustomNumberModel> foundNumber = numberRepository.findByNumber(number);

    Map<String, Boolean> result = new HashMap<>();

    if (foundNumber.isPresent()) {
      result.put("isEven", foundNumber.get().getIsEven());
		  result.put("isPrime", foundNumber.get().getIsPrime());

      numberCache.cacheResult(number, result);
      
      return result;
    }

    CustomNumber customNumber = new CustomNumber(number);

		result.put("isEven", customNumber.isEven());
		result.put("isPrime", customNumber.isPrime());

    CustomNumberModel numberModel = new CustomNumberModel();

    numberModel.setNumberInfo(number, result.get("isEven"), result.get("isPrime"));
    numberRepository.save(numberModel);

    numberCache.cacheResult(number, result);

		return result;
  }

  public Stream<Map<String, Boolean>> processNumberStream (Stream<Double> numberStream) {
    return numberStream.parallel().map(this::processNumber);
  }

  public Double getMinimumNumber (Stream<Double> numberStream) {
    return numberStream.mapToDouble(number -> number).min().orElseThrow(NoSuchElementException::new);
  }

  public Double getMaximumNumber (Stream<Double> numberStream) {
    return numberStream.mapToDouble(number -> number).max().orElseThrow(NoSuchElementException::new);
  }

  public Double getAverageNumber (Stream<Double> numberStream) {
    return numberStream.mapToDouble(number -> number).average().orElseThrow(NoSuchElementException::new);
  }
}
