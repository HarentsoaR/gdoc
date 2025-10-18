package mg.md2i.enmg.tools;

public class TraitementLangue{

   
   
   /*public static String reformatLabel(String text){
  String val=""; 
  if(null!=text){
    int n=text.length();
    int i=0;
    
    while(i<n){
     String s = text.substring(i, i+1);
     if(s.equals("\\")){      
      if((i+5)<n){
       //String s_=text.substring(i+1, i+6).toUpperCase();
    String s_=text.substring(i+1, i+2).toUpperCase();
       String s2=text.substring(i+1, i+4);
       
      
       if(s2.equals("r\n")){
           val+="\n";
           i=i+3;
           
        }else if(s_.equals("U")){
        	  val+= (char)Integer.parseInt(text.substring(i+2, i+6),16);
        	
        	  i+=5;
        }else{
        val+=s;
       }       
       
      }else{
       val+=s;
      }      
     }else{
      val+=s;
     }
     i=i+1;
    }
  }
  return val;
 }*/
   public static String reformatLabel(String text){
		String val="-";	
		if (text != null) {
			StringBuilder sb = new StringBuilder(text);
			int index = sb.indexOf("\\u");
			while (index != -1) {
				String specChar = sb.substring(index + 2, index + 6);
				char c = (char) Integer.parseInt(specChar, 16);
				sb.delete(index, index + 6);
				sb.insert(index, c);
				index = sb.indexOf("\\u");
			}
			val = sb.toString();
		}
		return val;
   }
   
   
   
}
