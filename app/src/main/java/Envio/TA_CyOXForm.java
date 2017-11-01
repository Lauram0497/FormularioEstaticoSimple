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

public class TA_CyOXForm extends AsyncTask<Void /*entrada doInBackground*/,Void /*onProgressUpdate*/,
        String /*retornado por doInBackground()*/> {

    private String URL;
    private String servicioWeb = "/WsAppNFC.asmx";

    private Context context;
    private String tipo;
    private String idFomrulario;
    private List<String> compObs;

    private WebServer webServer;
    private Snackbar snackbar;

    private final String NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ACTION = "http://tempuri.org/";
    private final String methodName = "CyOXFormIsvimed";

    public TA_CyOXForm(Context context, String tipo, List<String> compObs, String idFomrulario) {

        URL = "http://" + context.getString(R.string.socketWS) + servicioWeb;
        webServer = new WebServer();

        this.context = context;
        this.tipo = tipo;
        this.idFomrulario = idFomrulario;
        this.compObs = compObs;
    }

    @Override
    protected String doInBackground(Void... voids) {

        SoapObject request = new SoapObject(NAMESPACE, methodName);
        request.addProperty("Tipo", tipo);
        request.addProperty("IdForm", idFomrulario);
        String d = compObs.get(Integer.parseInt(tipo) - 1);
        request.addProperty("Campos", "[" + compObs.get(Integer.parseInt(tipo)) + "]"); //TODO Nombre de campo

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
        } else if (s.contains("ok")){
            int tipoInt = Integer.parseInt(tipo) + 1;
            if (tipoInt <= compObs.size()) {
                webServer.enviarPersonas(context, String.valueOf(tipoInt), compObs, idFomrulario);
            }
        }
    }
}
