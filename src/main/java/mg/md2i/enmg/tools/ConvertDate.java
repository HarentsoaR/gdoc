
package mg.md2i.enmg.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.zkoss.util.resource.Labels;

public class ConvertDate {
	
	private static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
	private static final String DD_MM_YYYY_DASH = "dd-MM-yyyy";
	private static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
	private static final String YYYY_MM_DD_DASH = "yyyy-MM-dd";
	private static final String HH_MM = "HH:mm";
	private static final String HH_MM_SS = "HH:mm:ss";
	
	public static java.sql.Date  UtilDateToSqlDate(java.util.Date utilDate) {
		java.sql.Date sqlDate = null;
		  if (utilDate != null)  sqlDate = new java.sql.Date(utilDate.getTime());
		  return sqlDate;
	}

	public static java.util.Date SqlDateToUtilDate(java.sql.Date sqlDate) {
		  java.util.Date utilDate = null;
		  if (sqlDate != null)  utilDate = new java.util.Date(sqlDate.getTime());
		  return utilDate;
	}
	public static String getMoisReduit(int j){
		String val="";
			if(j==1){
				val="Jan";
			}
			
			if(j==2){
				val="Fev";
			}
			
			if(j==3){
				val="Mar";
			}
			
			if(j==4){
				val="Avr";
			}
			
			if(j==5){
				val="Mai";
			}
			
			if(j==6){
				val="Jun";
			}
			
			if(j==7){
				val="Jul";
			}
			
			if(j==8){
				val="Aou";
			}
			
			if(j==9){
				val="Sep";
			}
			
			if(j==10){
				val="Oct";
			}
			
			if(j==11){
				val="Nov";
			}
			
			if(j==12){
				val="Dec";
			}
		return val; 
	}
	
	public static String getMois(int j){
		String val="";
			if(j==1){
				val="janvier";
			}
			
			if(j==2){
				val="fevrier";
			}
			
			if(j==3){
				val="mars";
			}
			
			if(j==4){
				val="avril";
			}
			
			if(j==5){
				val="mai";
			}
			
			if(j==6){
				val="juin";
			}
			
			if(j==7){
				val="juillet";
			}
			
			if(j==8){
				val="aout";
			}
			
			if(j==9){
				val="septembre";
			}
			
			if(j==10){
				val="octobre";
			}
			
			if(j==11){
				val="novembre";
			}
			
			if(j==12){
				val="decembre";
			}
		return TraitementLangue.reformatLabel(Labels.getLabel(val)); 
	}
	/*public String getFormatDate(){
		String val="";
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd")	;
		val=sf.
	}*/
	
	public static int getNombreJourMois(int j, int annee){
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,annee);
		System.out.println("lllll "+j+"  "+annee);
		
		int maxDay=30;
			
			
			if(j==1){
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==2){
				cal.set(Calendar.MONTH, Calendar.FEBRUARY);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==3){
				cal.set(Calendar.MONTH, Calendar.MARCH);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==4){
				cal.set(Calendar.MONTH, Calendar.APRIL);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==5){
				cal.set(Calendar.MONTH, Calendar.MAY);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==6){
				cal.set(Calendar.MONTH, Calendar.JUNE);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==7){
				cal.set(Calendar.MONTH, Calendar.JULY);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==8){
				cal.set(Calendar.MONTH, Calendar.AUGUST);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==9){
				cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==10){
				cal.set(Calendar.MONTH, Calendar.OCTOBER);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==11){
				cal.set(Calendar.MONTH, Calendar.NOVEMBER);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			if(j==12){
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			

		return maxDay; 
	}
	
	public static List<Date> getListeDesDates (Date dateDebut, Date dateFin){
		GregorianCalendar cal = new GregorianCalendar();
		GregorianCalendar cal2 = new GregorianCalendar();
		cal.setTime(dateDebut);
		cal2.setTime(dateFin);
		List<Date> listD=new LinkedList<Date>();
 
		while (cal.before(cal2)){
			listD.add(cal.getTime());
			cal.add(GregorianCalendar.DATE,1);
		}	
		return listD;
	}
	
	public static int getNombreJour(Date debut,Date fin){
		int val=0;
		System.out.println(debut+" gggggggggggggggggggggggggg  "+fin);
		if(fin.getTime()<debut.getTime()){
			return val;
		}else{
			double d=fin.getTime()-debut.getTime();
			d=d/(24*3600*1000);
			val=(int)(d);
			
		}
		System.out.println( " vvvvvvvvvv   "+val);
		return val;
	}
	
	public static int getNombreMois(Date debut,Date fin){
		int val=0;
		System.out.println(debut+" gggggggggggggg  "+fin);
		debut.setDate(1);
		fin.setDate(30);
		if(fin.getTime()<debut.getTime()){
			return val;
		}else{
			double d=fin.getTime()-debut.getTime();
			d=d/(24*3600*1000);
			d=d/30;
			val=(int)(d);
			
		}
		System.out.println( " vvvvvvvvvv   "+val);
		return val-1;
	}
	
	public static int getNombreHeureMemeJour(Date debut,Date fin){
		int val=0;
		if(fin.getTime()<debut.getTime()){
			return val;
		}else if(debut.getDate()==fin.getDate()){
			double d=fin.getTime()-debut.getTime();
			d=d/(3600*1000);
			val=(int)(d);
			
		}
		
		return val;
	}
	public static Double getNombreHeure(Date debut,Date fin){
		Double val=0.0;
		if(fin.getTime()<debut.getTime()){
			return val;
		}else if(debut.getDate()==fin.getDate()){
			double d=fin.getTime()-debut.getTime();
			d=d/(3600*1000);
			val=(Double)(d);
			
		}
		
		return val;
	}
	
	public static String getEcartDate(Date debut,Date fin){
		Long val=(fin.getTime()-debut.getTime())/1000;
		Integer nbSec=(int)((val%3600)%60);
		Integer nbMin=(int)((val%3600)/60);
		Integer nbHeure=(int)((val/3600));
		Integer nbJour=(int)((val/(3600*24)));
		
		String val2=nbHeure +" h "+nbMin+" min "+nbSec+" sec ";
		if(nbHeure>=24){
			
			val2=nbJour+ " Jours";
		}
		
		return val2;
	}
	
	public static Date getDateDebut() throws ParseException{
		 SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		 //ProjetGestion pro
		 Date deb=formatter.parse("17/08/2012");
		 return deb;
	}
	
	public static Date getDateFormatter(String date) throws ParseException{
		 SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		 //ProjetGestion pro
		 Date deb=formatter.parse(date);
		 return deb;
	}
	
	public static Date getDateFin() throws ParseException{
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		 Date deb=formatter.parse("16/05/2014");
		 return deb;
	}
	public static String getDate(String dateString){
		String val="";
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		 try {
			Date deb=formatter.parse(dateString);
			val=getDateFormatter(deb);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return val;
	}
	
	
	//1 dimanche
	public static int getNumeroDuJourSemaine(Date date){
		int today=0;
		GregorianCalendar calendar =new GregorianCalendar();
		calendar.setTime(date);
		today =calendar.get(calendar.DAY_OF_WEEK);
		return today;
	}
	
	@SuppressWarnings("deprecation")
	public static int getNombreSemaine(Date date){
		int resultat=0;
		//date = date lundi 1er du mois
		
		//nombre du lundi o� le lundi dernier mois
		
		//r�cup�rer la date du dernier Lundi du dernier mois
		
		Date dateRepere=new Date();
		
		int year=date.getYear();
		
		int m=date.getMonth()+2;
		dateRepere.setMonth(m);
		System.out.println("ttttttttttt "+dateRepere.getMonth());
		int mm=dateRepere.getMonth();
		
		
		
		if(date.getMonth()<=9){
			dateRepere.setYear(year);
		}else{
			dateRepere.setYear(year+1);
		}
		
		int nbJour=ConvertDate.getNombreJourMois(mm+1, dateRepere.getYear());
		
		dateRepere.setDate(nbJour);
		System.out.println("nbJournbJour "+nbJour);
		int numeroJourSemaine=(ConvertDate.getNumeroDuJourSemaine(dateRepere));
		int delta=numeroJourSemaine-2;
		
		if(delta>=0){
			dateRepere.setDate(nbJour-delta);
		}else{//si fin du mois = dimanche
			dateRepere.setDate(nbJour-6);
		}
		System.out.println("taona "+dateRepere.getYear()+"taona taloha  "+date.getYear()+"volana "+dateRepere.getMonth()+"volana taloha "+date.getMonth());
		System.out.println("daty taloha "+date+" daty farany "+dateRepere);
		resultat=(ConvertDate.getNombreJour(date, dateRepere)/7)+1;
		
		//traiter le premier Lundi
		
		
		return resultat;
	}
//	
	
	public static List<Date> daysOfWeek(Date day) {
		Calendar date = Calendar.getInstance();
		date.setTime(day);
		List<Date> results = new LinkedList<>();
		int delta = -date.get(GregorianCalendar.DAY_OF_WEEK) + 2;
		date.add(Calendar.DAY_OF_MONTH, delta);
		for (int i = 0; i < 7; i++) {
			results.add(date.getTime());
			date.add(Calendar.DAY_OF_MONTH, 1);
		}
		return results;
	}
	
	public static int getNbrJourFerie(Date debut, Date fin){
		List<Date> list= getListeDesDates(debut, fin);
		int nbjourFerie=0;
		for (Date date : list) {
			Calendar cal= Calendar.getInstance();
			cal.setTime(date);
			if(cal.get(Calendar.DAY_OF_WEEK)==1 || cal.get(Calendar.DAY_OF_WEEK)==6){
				nbjourFerie++;
			}	
		}
		return nbjourFerie;
	}
	public static int getNbrJourTravail(Date debut, Date fin){
	
		int nbjourFerie=getNbrJourFerie(debut, fin);
		
		return getNombreJour(debut, fin)-nbjourFerie;
	}
	public static Date getDemain(Date date){  
	    Calendar aujourdhuiDate= Calendar.getInstance();
	    aujourdhuiDate.setTime(date);
	    Date demain=new Date();
	    if(aujourdhuiDate != null)
	    	aujourdhuiDate.add(Calendar.DAY_OF_MONTH, 1);
	    	demain=aujourdhuiDate.getTime();
	   return demain;
	 } 

	
	@SuppressWarnings("deprecation")
	public static int getNombreSemaineEntreDate(Date dateDebut,Date dateFin){
		int resultat=0;
		//date = date lundi 1er du mois
		
		//nombre du lundi o� le lundi dernier mois
		
		//r�cup�rer la date du dernier Lundi du dernier mois
		
		Date dateRepere=new Date();
		
		int year=dateDebut.getYear();
		
		System.out.println("dateFindateFindateFin "+dateFin);
		
		int m=dateDebut.getMonth()+getNombreMois(dateDebut,dateFin);
		dateRepere.setMonth(m);
		System.out.println("tttttttttttttt "+dateRepere.getMonth());
		int mm=dateRepere.getMonth();
		
		
		
		if(dateDebut.getMonth()<=11){
			dateRepere.setYear(year);
		}else{
			dateRepere.setYear(year+1);
		}
		
		int nbJour=ConvertDate.getNombreJourMois(mm+1, dateRepere.getYear());
		
		dateRepere.setDate(nbJour);
		System.out.println("nbJournbJour "+nbJour);
		int numeroJourSemaine=(ConvertDate.getNumeroDuJourSemaine(dateRepere));
		int delta=numeroJourSemaine-2;
		
		if(delta>=0){
			dateRepere.setDate(nbJour-delta);
		}else{//si fin du mois = dimanche
			dateRepere.setDate(nbJour-6);
		}
		System.out.println("taona "+dateRepere.getYear()+"taona taloha  "+dateDebut.getYear()+"volana "+dateRepere.getMonth()+"volana taloha "+dateDebut.getMonth());
		System.out.println("daty taloha "+dateDebut+" daty farany "+dateRepere);
		resultat=(ConvertDate.getNombreJour(dateDebut, dateRepere)/7)+1;
		
		//traiter le premier Lundi
		
		
		return resultat;
	}
	
	
	
	/*
	GregorianCalendar calendar =new GregorianCalendar();
	calendar.setTime(new Date());
	int today =calendar.get(calendar.DAY_OF_WEEK);
	//TODAY = 1 / sunday
	//TODAY = 2 / monday
	
	System.out.println("gggggggggjkbkjkjbk "+today);
	
	*/
	
	
	public static List<String> getAllMonth(String pattern) {
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		list.add(getMonth(cal, Calendar.JANUARY, pattern));
		list.add(getMonth(cal, Calendar.FEBRUARY, pattern));
		list.add(getMonth(cal, Calendar.MARCH, pattern));
		list.add(getMonth(cal, Calendar.APRIL, pattern));
		list.add(getMonth(cal, Calendar.MAY, pattern));
		list.add(getMonth(cal, Calendar.JUNE, pattern));
		list.add(getMonth(cal, Calendar.JULY, pattern));
		list.add(getMonth(cal, Calendar.AUGUST, pattern));
		list.add(getMonth(cal, Calendar.SEPTEMBER, pattern));
		list.add(getMonth(cal, Calendar.OCTOBER, pattern));
		list.add(getMonth(cal, Calendar.NOVEMBER, pattern));
		list.add(getMonth(cal, Calendar.DECEMBER, pattern));
		return list;
	}
	
	private static String getMonth(Calendar cal, int month, String pattern) {
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, month);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}
	
	public static Integer getMonth(
			String value, String pattern) throws ParseException {
		Date date = new SimpleDateFormat(pattern).parse(value);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}
	
	public static String parse(Date valDate)  {
		SimpleDateFormat formatter=new SimpleDateFormat(YYYY_MM_DD_DASH);
		String val=formatter.format(valDate);
		return val;
	}
	
	public static Date parse(String strDate) throws ParseException {
		Date date = null;
		if (strDate != null) {
			StringBuilder pattern = new StringBuilder();
			String[] splitted = strDate.split(" ");
			String space = "";
			int index = strDate.indexOf("/");
			if (index == -1) {
				index = strDate.indexOf("-");
			}
			for (String data:splitted) {
				switch(data.length()) {
				case 5: pattern.append(space).append(HH_MM);
					break;
				case 8:
					if (data.contains(":")) {
						pattern.append(space).append(HH_MM_SS);
					} else {
						continue;
					}
					break;
				case 10:
					if (data.contains("/")) {
						pattern.append(space).append(
								index == 2?DD_MM_YYYY_SLASH:YYYY_MM_DD_SLASH);
					} else if (data.contains("-")) {
						pattern.append(space).append(
								index == 2?DD_MM_YYYY_DASH:YYYY_MM_DD_DASH);
					} else {
						continue;
					}
					break;
				default: continue;
				}
				space = " ";
			}
			if (new String(pattern).length() > 0) {
				date = new SimpleDateFormat(
						new String(pattern)).parse(strDate);
			} 
		}
		return date;
	}
	
	public static Date getPremierDuMois(Date maDate){
		Calendar c = Calendar.getInstance();
		 
		// on se place � la date utilis�e comme base de calcul
		c.setTime(maDate);
		 
		// on se place au premier jour du mois en cours
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date debutMois = c.getTime();
		 
		// premier jour du mois en cours moins un jour = dernier jour du mois pr�c�dent
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date finMoisPrecedent = c.getTime();
		 
		// on �tait au dernier jour du mois pr�c�dent, on se place maintenant au premier jour du mois pr�c�dent
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date debutMoisPrecedent = c.getTime();
		return debutMois;
	}
	
	public static Date getFinDuMois(Date maDate){
		Calendar c = Calendar.getInstance();
		 
		// on se place � la date utilis�e comme base de calcul
		c.setTime(maDate);
		 
		// on se place au premier jour du mois en cours
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date finMois = c.getTime();
	
		
		// premier jour du mois en cours moins un jour = dernier jour du mois pr�c�dent
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date finMoisPrecedent = c.getTime();
		 
		// on �tait au dernier jour du mois pr�c�dent, on se place maintenant au premier jour du mois pr�c�dent
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date debutMoisPrecedent = c.getTime();
		return finMois;
	}
	
	public static String getDateFormatter(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		
		return formatter.format(date);
	}
	public static String getDateFormatterTirerSql(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		
		return formatter.format(date);
	}
	
	public static String getDateFormatterSql(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy:MM:dd");
		
		return formatter.format(date);
	}
	
	public static String getDateFormatterHMS(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		return formatter.format(date);
	}
	public static String getDateFormatterSqlHMS(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
		
		return formatter.format(date);
	}
	
	public static String getDateFormatterSqlHMS3(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return formatter.format(date);
	}
	
	public static String getDateFormatterSql2HMS(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		
		return formatter.format(date);
	}
	
	public static String getDateFormatterHM(Date date){
		SimpleDateFormat formatter=new SimpleDateFormat("HH:mm");
		
		return formatter.format(date);
	}
	
	
	 public static Date addDaysToDate(Date date, int nbDays){
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, nbDays);
	        return cal.getTime();
	    }
	 
	 public static Date formatterDate(Date date){
		 String temp=getDateFormatter(date);
		 SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		 Date val=new Date();
		 try {
			val= formatter.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return val;
	 }
	 public static Date formatterDateAnnee(Date date){
		 String temp=getDateFormatter(date);
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyy");
		 Date val=new Date();
		 try {
			val= formatter.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return val;
	 }

	public static Date getDebutMois(Date date) {
		// TODO Auto-generated method stub
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
		cal.set(year, month, 1);
		return cal.getTime();
	}
	
	public static Date getFinMois(Date date) {
		// TODO Auto-generated method stub
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, maxDay);
		return cal.getTime();
	}
	
	public static Date getDateMoisEnlever(Date date,int mois){
		int moisD=date.getMonth();
		date.setMonth(moisD-mois);
		return date;
	}
	
	@SuppressWarnings("deprecation")
	public static Date fusionnerDateHeure(Date date, Date heure) {
		Date result = new Date(date.getYear(), date.getMonth(), date.getDate(), heure.getHours(), heure.getMinutes());
		return result;
	}
	
	
	public static void main(String args[]){
		System.out.println(getDateMoisEnlever(new Date(), 24));
	}

}

