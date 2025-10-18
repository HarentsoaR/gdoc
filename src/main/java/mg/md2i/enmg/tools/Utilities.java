package mg.md2i.enmg.tools;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.text.NumberFormatter;

import mg.md2i.gedi.config.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.zkoss.zul.Comboitem;


public class Utilities {
	private static final char UNDERSCORE = '_';
	private static final Logger LOGGER = LoggerFactory.getLogger(Utilities.class);
	public static String createLabel(String...args) {
		StringBuilder sb = new StringBuilder();
		String pipe = "";
		for (String string:args) {
			sb.append(pipe).append(string);
			pipe = " | ";
		}
		return new String(sb);
	}
	
	public static String getJavaName(String sqlName) {
		String result = formatToJava(sqlName);
		String firstChar = String.valueOf(result.charAt(0));
		if (isTable(sqlName)) {
			result = result.replaceFirst(firstChar, firstChar.toUpperCase());
		}
		return result;
	}
	
	public static String getJavaNameGestion(String sqlName) {
		return getJavaName(sqlName)+"Gestion";		 
	}
	
	
	public static String firstToUpperCase(String val){
		String firstChar = String.valueOf(val.charAt(0));
		
		return val.replaceFirst(firstChar, firstChar.toUpperCase());
	}
	
	public static String getFieldName(String sqlName) {
		//if (sqlName.startsWith("id_")) {
//		if (sqlName.endsWith("_id")) {
//			sqlName=sqlName.substring(3);
//			sqlName=removeUnderscore(sqlName);
//		}else{
//			sqlName=removeUnderscore(sqlName);
//		}
		if (sqlName.endsWith("_id")) {
			sqlName=removeUnderscore(sqlName);
		}else{
			sqlName=removeUnderscore(sqlName.substring(sqlName.indexOf(UNDERSCORE) + 1));
		}
		return sqlName;
	}
	
	public static String getPK(String table) {
		String pk = getJavaName(table);
		String firstChar = String.valueOf(pk.charAt(0));
		pk = pk.replaceFirst(firstChar, firstChar.toLowerCase());
		return new StringBuilder(addUnderscore(pk)).append("_id").toString().trim();
	}
	
	private static String addUnderscore(String value) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i < value.length();i ++) {
			char current = value.charAt(i);
			if (Character.isUpperCase(current)) {
				sb.append(UNDERSCORE).append(Character.toLowerCase(current));
			} else {
				sb.append(current);
			}
		}
		return new String(sb);
	}
	
	private static String removeUnderscore(String value) {
		char previous = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i < value.length();i ++) {
			char current = value.charAt(i);
			if (current == UNDERSCORE) {
				
			} else if (previous == UNDERSCORE) {
				sb.append(String.valueOf(current).toUpperCase());
			} else {
				sb.append(current);
			}
			previous = current;
		}
		return new String(sb).trim();
	}
	
	public static String formatToJava(String value) {
		String result;
		//if (value.startsWith("id_")) {
		if (value.endsWith("_id")) {
			//result = removeUnderscore(value);
			result = removeUnderscore(value);
		} else {
			if(isTable(value)){
				//result = removeUnderscore(value.substring(value.indexOf(UNDERSCORE) + 1));
				//result = removeUnderscore(value);
			}else{
				//result = removeUnderscore(value);
			}
			result = removeUnderscore(value.substring(value.indexOf(UNDERSCORE) + 1));
			
		}
		return result;
	}
	
	public static String formatToField(String value) {
		String result;
		if (value.endsWith("_id")) {
			//result = removeUnderscore(value);
			result=value.substring(value.indexOf(UNDERSCORE) + 1);
		} else {
			if(isTable(value)){
				result = removeUnderscore(
						value.substring(value.indexOf(UNDERSCORE) + 1));
			}else{
				result = removeUnderscore(value);
			}
			
			
		}
		return result;
	}
	
	
	public static String formatToField2(String value) {
		String result;
		if (value.endsWith("_id")||value.startsWith("code_")) {
			result = removeUnderscore(value);
			result=value.substring(0,value.indexOf(UNDERSCORE));
			//result = removeUnderscore(value.substring(value.indexOf(UNDERSCORE) + 1));
					
		} else {
			if(isTable(value)){
				result = removeUnderscore(
						value.substring(value.indexOf(UNDERSCORE) + 1));
			}else{
				
				result = removeUnderscore(value.substring(value.indexOf(UNDERSCORE) + 1));
			}
			
		}
		return firstToUpperCase(result);
	}
	
	public static String formatToField3(String value) {
		String result;
		if (value.endsWith("_id")||value.startsWith("code_")) {
			//result = removeUnderscore(value);
			result=value.substring(value.indexOf(UNDERSCORE) + 1);
		} else {
			if(isTable(value)){
				result = removeUnderscore(
						value.substring(value.indexOf(UNDERSCORE) + 1));
			}else{
				result = removeUnderscore(
						value.substring(value.indexOf(UNDERSCORE) + 1));
				//result = removeUnderscore(value);
			}
			
		}
		return firstToUpperCase(result);
	}
	
	public static String formatToFieldCode(String value) {
		String result;
		if (value.startsWith("code_")) {
			//result = removeUnderscore(value);
			result=value.substring(value.indexOf(UNDERSCORE) + 1);
		} else {
			if(isTable(value)){
				result = removeUnderscore(
						value.substring(value.indexOf(UNDERSCORE) + 1));
			}else{
				result = removeUnderscore(value);
			}
			
		}
		return firstToUpperCase(result);
	}
	
	private static boolean isTable(String value) {
		List<String> prefixes =new ArrayList<String>();
		List<String> prefixes2 = Arrays.asList("a_", "e_", "p_", "t_", "v_","s_","jacl2_","jlx_","_tab");
		for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
			prefixes.add(alphabet+"_");
		}
		for (String string : prefixes2) {
			prefixes.add(string);
		}
		boolean isTable = false;
		boolean isTable2 = false;
		for (String prefix:prefixes) {
			isTable = value.startsWith(prefix);
			isTable2=value.endsWith(prefix);
			if (isTable || isTable2) {
				isTable=true;
				break;
			}
		}
		return isTable;
	}
	
	public static String getter(String field) {
		return getter(field, false);
	}
	
	public static String getter(String field, boolean isBoolean) {
		String javaField = getJavaName(field);
		String firstChar = String.valueOf(javaField.charAt(0));
		return (isBoolean?"is":"get") + javaField.replaceFirst(
				firstChar, firstChar.toUpperCase());
	}
	
	public static String setter(String field) {
		String javaField = getJavaName(field);
		String firstChar = String.valueOf(javaField.charAt(0));
		return "set" + javaField.replaceFirst(
				firstChar, firstChar.toUpperCase());
	}
	
	public static String getString(String key, Object...args) {
		ObjectFactory factory = ObjectFactory.getInstance();
		MessageSource messageSource = ObjectFactory.getBean(MessageSource.class);
		return messageSource.getMessage(key, args, factory.getLocale());
	}
	
	public static String generatePK(int lines) {
		StringBuilder sb = new StringBuilder();
		int random = Double.valueOf(Math.random()*10).intValue();
		sb.append(lines + 1).append(String.valueOf(
				new Date().getTime())).append(random);
		return new String(sb);
	}
	
	
	public static NumberFormatter getFormatter() {
		return getFormatter(true, 0);
	}
	
	public static NumberFormatter getFormatter(int fractionDigits) {
		return getFormatter(false, fractionDigits);
	}
	
	private static NumberFormatter getFormatter(boolean integer, int fractionDigits) {
		Locale locale = ObjectFactory.getInstance().getLocale();
		NumberFormat format = integer?NumberFormat.getIntegerInstance(locale)
				:(DecimalFormat)NumberFormat.getNumberInstance(locale);
		format.setMaximumFractionDigits(fractionDigits);
		format.setMinimumFractionDigits(Math.min(2, fractionDigits));
		format.setGroupingUsed(true);
		NumberFormatter formatter = new NumberFormatter(format);
		return formatter;
	}
	
	public static int getNiveau(String code) {
		int niveau = -1;
		if (code != null && !"".equals(code.trim())) {
			StringBuilder reverse = new StringBuilder(code).reverse();
			niveau += code.length() - reverse.lastIndexOf("0");
		}
		return niveau;
	}
	
	public static String getParent(String code) {
		StringBuilder parent = new StringBuilder();
		if (code != null && !"".equals(code.trim())) {
			NumberFormat nf = NumberFormat.getIntegerInstance();
			nf.setGroupingUsed(false);
			int firstZero = code.indexOf("0"); 
			if (firstZero > 1) {
				nf.setMinimumIntegerDigits(code.length() - firstZero + 1);
				parent.append(code.substring(0, firstZero - 1)).append(nf.format(0));
			}
		}
		return new String(parent);
	}
	
	public static String format(String input, int number) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		String result = input;
		if (number > input.length()) {
			nf.setMinimumIntegerDigits(number - input.length());
			nf.setGroupingUsed(false);
			StringBuilder formatted = new StringBuilder(
					input).append(nf.format(0));
			result = new String(formatted);
		} else if (number < input.length()) {
			result = input.substring(0, number);
		}
		return result;
	}
	
	public static String generateBetween(String prec, String next) {
		int max = Math.max(prec.length(), next.length());
		StringBuilder sb = new StringBuilder();
		for (int i = 0;i < max;i ++) {
			String subPrec = prec.substring(0, Math.min(i + 1, prec.length()));
			String subNext = next.substring(0, Math.min(i + 1, next.length()));
			int c1 = i < prec.length()?Integer.valueOf(
					String.valueOf(prec.charAt(i))):0;
			int c2 = i < next.length()?Integer.valueOf(
					String.valueOf(next.charAt(i))):0;
			if (subPrec.equals(subNext)) {
				sb.append(c1);
			} else if (i >= next.length()) {
				if (c1 == 9) {
					sb.append(c1);
				} else {
					sb.append(c1 + 1);
					break;
				}
			} else if (i >= prec.length()) {
				if (c2 == 0) {
					sb.append(c2);
				} else if (c2 == 1) {
					sb.append("09");
					break;
				} else {
					sb.append(c2 - 1);
					break;
				}
			} else if (c1 + 1 == c2) {
				sb.append(c1);
				if (i < prec.length() - 1) {
					sb.append(prec.substring(i + 1));
				} 
				sb.append('1');
				break;
			} else {
				sb.append(c1 + 1);
				break;
			}
		}
		if (prec.startsWith(sb.toString())) {
			sb.append('1');
		} 
		return new String(sb);
	}
	
	public static void main(String...args) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(generateBetween("11", "111"));
			LOGGER.debug(generateBetween("2", "3"));
			LOGGER.debug(generateBetween("19","2"));
			LOGGER.debug(generateBetween("4", "7"));
		}
		System.out.println(formatToField2("menu_id"));
	}
	
	public static Date createDate(boolean timestamp) {
		long current = new Date().getTime();
		return timestamp?new Timestamp(current):new java.sql.Date(current);
	}
	
	public static boolean isIncluded(
			Dimension dimension, Dimension min, Dimension max) {
		boolean heightIncluded = dimension.height <= max.height 
				&& dimension.height >= min.height;
		boolean widthIncluded = dimension.width <= max.width 
				&& dimension.width >= min.width;
		return heightIncluded && widthIncluded;
	}
	
	public static String getDureeMois(Date debut, Date fin) {
		String mois = "";
		if (debut != null && fin != null) {
			Long millis = fin.getTime() - debut.getTime();
			double duree = millis.doubleValue()*12d/(1000d*3600d*24d*365d);
			mois = new Double(Math.ceil(duree)).intValue() 
					+ " " + getString("mois");
		}
		return mois;
	}
	
	public static <T> SortedMap<String, T> getMapDataByKeyPrefix(NavigableMap<String, T> map, String prefix){
		if (map == null) {
			return new TreeMap<>();
		}
		return map.subMap(prefix + ";", prefix + Character.MAX_VALUE);
	}
	
	public static String joinArray(String separator, String[] arrays) {
		if (null == arrays || 0 == arrays.length)
			return "";

		StringBuilder sb = new StringBuilder(256);
		sb.append(arrays[0]);

		for (int i = 1; i < arrays.length; i++)
			sb.append(separator).append(arrays[i]);

		return sb.toString();
	}

	
	public static Map<Integer, String> getJourOuvrableSemaine(Locale locale){
		DateFormatSymbols dfs = new DateFormatSymbols(locale);		
		String[] jours = dfs.getWeekdays();		
		Map<Integer, String> results = new TreeMap<>();
		for (int i = 0; i < jours.length; i++) {
			switch (jours[i]) {
			case "dimanche":
			case "samedi":
			case "":
				break;
			default:
				results.put(i, Utilities.firstToUpperCase(jours[i]));
				break;
			}
		}
		return results;
	}
}
