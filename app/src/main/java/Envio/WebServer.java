package Envio;

import android.content.Context;

import java.util.List;

/**
 * Created by Usuario on 19/09/2017.
 */

public class WebServer {

    public void enviarFormulario(Context context, String acta, String lugarOrig, String fecha, String
                              horaI, String horaF, String lugarR, String convocatoria, String firmaP,
                                 String nombreP, String cargoP, String firmaS, String nombreS,
                                 String cargoS, String fechaGps, List<String> personas,
                                 List<String> ordenDllo, List<String> compObs){
        new TA_EnviarFormulario(context, acta, lugarOrig, fecha, horaI, horaF, lugarR, convocatoria,
                firmaP, nombreP, cargoP, firmaS, nombreS, cargoS, fechaGps, personas, ordenDllo,
                compObs).execute();
    }

    public void enviarPersonas (Context context, String tipo, List<String> personas, String idForm){
        new TA_PersonasXForm(context, tipo, personas, idForm).execute();
    }

    public void enviarOrdenyDllo(Context context, String tipo, List<String> ordenDllo, String idForm){
        new TA_ODXForm(context, tipo, ordenDllo, idForm).execute();
    }

    public void enviarComyObs(Context context, String tipo, List<String> compObs, String idForm){
        new TA_CyOXForm(context, tipo, compObs, idForm).execute();
    }
}
