package com.Automation.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.poi.hwpf.usermodel.DateAndTime;



public class TextUtils {
	public static enum Mode {
		ALPHA, NUMARIC, ALPHANUMARIC
	}

//	TextUtils.generateRandomString(10, Mode.ALPHA)
//	TextUtils.generateRandomString(10, Mode.ALPHANUMARIC)
//	TextUtils.generateRandomString(10, Mode.NUMARIC)
	public static String generateRandomString(int length, Mode mode) {
		StringBuffer buffer = new StringBuffer();
		String characters = "";
		Boolean isNumaricOnly = false;
		String automationTextPrefirefix = "";
		switch (mode) {
		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case ALPHANUMARIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;
		case NUMARIC:
			isNumaricOnly = true;
			characters = "1234567890";
			break;
		}
		int charactersLength = characters.length();
		if (length >= 3 && !isNumaricOnly) {
			length = length - 2;
			automationTextPrefirefix = "AutomationTest";
		}
		for (int i = 0; i < length; i++) {
			double index = Math.random() * charactersLength;
			buffer.append(characters.charAt((int) index));
		}
		if (isNumaricOnly) {
			return String.valueOf(buffer);
		} else {
			return automationTextPrefirefix + buffer.toString();
		}
	}

//	TextUtils.getRandomNumberInBetween(1,100) -->Returns --> any number between 1 and 100
	public static Integer getRandomNumberInBetween(Integer upper, Integer lower) {
		return (int) ((Math.random() * (upper - lower)) + lower);
	}

//	dd/mm/yyyy , 0 -->04/02/2021
//	dd/mm/yyyy , -1 -->03/02/2021
//	dd/mm/yyyy , 1 -->05/02/2021
//	TextUtils.addNumberOfDaysInServerTime("dd/mm/yyyy",0) --> 04/02/2021
	public static String addNumberOfDaysInServerTime(String format, int addNumberOfDays) {
		DateFormat simpleDateFormat = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, addNumberOfDays);
		String fdate = simpleDateFormat.format(c.getTime());
		return fdate;
	}
	
	public static String addNumberOfMonthsInServerTime(String format, int addNumberOfMonths) {
		DateFormat simpleDateFormat = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, addNumberOfMonths);
		String fdate = simpleDateFormat.format(c.getTime());
		return fdate;
	}

	public static void uploadFileUsingExec(String excelFile, String fileNameToUpload) {
		try {
			Thread.sleep(3000);
			String fileName = System.getProperty("user.dir") + "\\Resources\\" + fileNameToUpload;
			String autoITExecutable = System.getProperty("user.dir") + "\\Resources\\" + excelFile + " " + fileName;
			Runtime.getRuntime().exec(autoITExecutable);
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String randomvalue(int length) {
		/* string containing all the alphabets */
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create random string builder
		/* StringBuilder class to append all the characters together. */
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string

		for (int i = 0; i < length; i++) {

			// generate random index number
			/* string containing all the alphabets */
			int index = random.nextInt(alphabet.length());

			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		return randomString;
	}

	public static int randomNumber(int length) {
		int min = 1;
		int max = 100;
		int random_int = (int) (Math.random() * (max - min + 1) + min);
		return random_int;
	}

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String addNumberOfYearsInServerTime(String format, int numberOfYears) {
		DateFormat simpleDateFormat = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, numberOfYears);
		String fdate = simpleDateFormat.format(c.getTime());
		return fdate;
	}
	
	

	
//	public static void uploadFileUsingExec(String excelFile, String fileNameToUpload) {
//		try {
//			Thread.sleep(3000);
//			String fileName = Constants.RESOURCES + "\\" + fileNameToUpload;
//			String autoITExecutable = Constants.RESOURCES + "\\" + excelFile + " " + fileName;
//			Runtime.getRuntime().exec(autoITExecutable);
//			Thread.sleep(3000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
