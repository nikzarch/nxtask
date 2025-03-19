package com.example.nxtask;

import com.example.nxtask.generator.CDRGenerator;
import com.example.nxtask.model.Subscriber;

import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		CDRGenerator cdrGenerator = new CDRGenerator(CDRGenerator.getRandomSubscribers(20));
		cdrGenerator.generateCDRRecords(24).forEach(cdrRecord -> {
			System.out.println(cdrRecord.toString());
		});
	}



}

