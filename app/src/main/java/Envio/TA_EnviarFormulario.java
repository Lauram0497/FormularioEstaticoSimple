package Envio;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import co.com.eleinco.formularioestaticosimple.R;


/**
 * Created by Usuario on 18/09/2017.
 */

public class TA_EnviarFormulario extends AsyncTask <Void /*entrada doInBackground*/,Void /*onProgressUpdate*/,
        String /*retornado por doInBackground()*/> {

    private String URL;
    private String servicioWeb = "/WsAppNFC.asmx";

    private Context context;
    private String acta;
    private String lugarOrig;
    private String fecha;
    private String horaI;
    private String horaF;
    private String lugarR;
    private String convocatoriaPR;
    private String firmaP;
    private String nombreP;
    private String cargoP;
    private String firmaS;
    private String nombreS;
    private String cargoS;
    private String fechaGps;
    private String idFromulario;

    private List<String> personas;
    private List<String> ordenDllo;
    private List<String> compObs;

    private WebServer webServer;
    private Snackbar snackbar;

    private final String NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ACTION = "http://tempuri.org/";
    private final String methodName = "EnviarFormularioIsvimed";

    public TA_EnviarFormulario(Context context, String acta, String lugarOrig, String fecha, String
            horaI, String horaF, String lugarR, String convocatoriaPR, String firmaP, String nombreP,
                               String cargoP, String firmaS, String nombreS, String cargoS,
                               String fechaGps, List<String> personas, List<String> ordenDllo,
                               List<String> compObs) {
        this.context = context;
        this.acta = acta;
        this.lugarOrig = lugarOrig;
        this.fecha = fecha;
        this.horaI = horaI;
        this.horaF = horaF;
        this.lugarR = lugarR;
        this.convocatoriaPR = convocatoriaPR;
        this.firmaP = firmaP;
        this.nombreP = nombreP;
        this.cargoP = cargoP;
        this.firmaS = firmaS;
        this.nombreS = nombreS;
        this.cargoS = cargoS;
        this.fechaGps = fechaGps;
        this.personas = personas;
        this.ordenDllo = ordenDllo;
        this.compObs = compObs;

        webServer = new WebServer();
    }

    @Override
    protected String doInBackground(Void... voids) {
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("ActaNro", acta);
        request.addProperty("LugarO", lugarOrig);
        request.addProperty("Fecha", fecha);
        request.addProperty("HoraI", horaI);
        request.addProperty("HoraF", horaF);
        request.addProperty("LugarR", lugarR);
        request.addProperty("ConvocatoriaPR", convocatoriaPR);
        request.addProperty("FirmaP", firmaP);
        request.addProperty("NombreP", nombreP);
        request.addProperty("CargoP", cargoP);
        request.addProperty("FirmaS", firmaS);
        request.addProperty("NombreS", nombreS);
        request.addProperty("CargoS", cargoS);
        request.addProperty("FechaGps", fechaGps);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = (true);
        envelope.setOutputSoapObject(request);

        HttpTransportSE transport = new HttpTransportSE(URL);

        try {
            transport.call(SOAP_ACTION + methodName, envelope);
            SoapPrimitive resultamdo_xml = (SoapPrimitive) envelope.getResponse();
            return resultamdo_xml.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s == null){
            snackbar = Snackbar.make(((Activity) context).findViewById(R.id.main),
                    R.string.errorConection, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.rojo));
            snackbar.show();
        } else {
            idFromulario = s;
            webServer.enviarPersonas(context, "1", personas, idFromulario);
            webServer.enviarOrdenyDllo(context, "1", ordenDllo, idFromulario);
            webServer.enviarComyObs(context, "1", compObs, idFromulario);
            /*for (int i = 0; i < ; i++){
                webServer.enviarPersonas(context, "1", );
            }

            webServer.enviarOrdenyDllo(context, "1",);
            webServer.enviarComyObs(context, "1",);*/
        }
    }
}
