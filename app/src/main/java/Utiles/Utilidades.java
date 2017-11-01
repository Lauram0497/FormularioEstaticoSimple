package Utiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by Usuario on 19/09/2017.
 */

public class Utilidades {

    private String Fecha;
    private String Hora;

    public String getFechaCompleta(){
        return getFechaActual() + " " + getHoraActual();
    }

    public String getFechaActual(){
        Calendar cal = Calendar.getInstance();
        int actualYear = cal.get(Calendar.YEAR);
        int actualMonth = cal.get(Calendar.MONTH)+1;
        int actualDay = cal.get(Calendar.DAY_OF_MONTH);

        Fecha = Integer.toString(actualYear)+Integer.toString(actualMonth)+Integer.toString(actualDay);
        String fechaFormatoCR = date2string(actualDay, actualMonth, actualYear);

        return fechaFormatoCR;
    }

    public String getHoraActual(){
        Calendar cal = Calendar.getInstance();
        int actualHour = cal.get(Calendar.HOUR_OF_DAY);
        int actualMinute = cal.get(Calendar.MINUTE);
        int actualSeconds = cal.get(Calendar.SECOND);

        Hora=Integer.toString(actualHour)+Integer.toString(actualMinute)+Integer.toString(actualSeconds);
        String fechaFormatoCR = time2string(actualHour, actualMinute, actualSeconds);

        return fechaFormatoCR;
    }

    private String time2string(int hour, int minute, int sec){
        String sTime;
        String sminute;
        String shour;
        String sseconds;
        if( sec < 10 ){
            sseconds = "0" + Integer.toString(sec);
        }else{
            sseconds = Integer.toString(sec);
        }
        if( minute < 10 ){
            sminute = "0"+Integer.toString(minute);
        }else{
            sminute = Integer.toString(minute);
        }
        if( hour < 10 ){
            shour = "0"+Integer.toString(hour);
        }else{
            shour = Integer.toString(hour);
        }
        sTime = shour + ":" + sminute + ":" + sseconds;
        return sTime;
    }

    private String date2string(int day, int month, int year){
        String sDate;
        String sday;
        String smonth;
        String syear;

        // Convertir a string
        if( day < 10 ){
            sday = "0"+Integer.toString(day);
        }else{
            sday = Integer.toString(day);
        }
        if( month < 10 ){
            smonth = "0"+Integer.toString(month);
        }else{
            smonth = Integer.toString(month);
        }
        syear = Integer.toString(year);
        sDate = syear + "-" + smonth + "-" + sday;
        return sDate;
    }

    public static String encodeToBase64(Bitmap image){
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK",imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input){
        byte[] decodedByte = Base64.decode(input,0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte,0,decodedByte.length);
        return bitmap;
    }

}
