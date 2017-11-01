package co.com.eleinco.formularioestaticosimple;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Envio.WebServer;
import Utiles.Utilidades;

public class MainActivity extends AppCompatActivity {

    // Variables RecyclerView para Asistentes, Ausentes e Invitados
    private RecyclerView rvAsist, rvAusent, rvInvit;
    private LinearLayoutManager llmAsist, llmAusen, llmInvit;
    private List<containerAsistentes> asistentes, ausentes, invitados;
    private AdapterAsistentes adapterAsistentes, adapterAusentes, adapterInvitados;
    private ImageButton btnAddAsistentes, btnAddAusentes, btnAddInvitados;
    private ImageButton btnDelAsistentes, btnDelAusentes, btnADelInvitados;

    // Variables RecyclerView para Orden del día y desarrollo de la Reunión
    private RecyclerView rvOrden, rvDllo;
    private LinearLayoutManager llmOrden, llmDesarrollo;
    private List<String> orden, desReunion;
    private AdapterOrden adapterOrden, adapterDllo;
    private ImageButton btnAddOrden, btnAddDllo;
    private ImageButton btndelOrden, btnDelDllo;

    // Variables RecyclerView para Compromisos
    private RecyclerView rvCompr, rvComAdq;
    private LinearLayoutManager llmComp, llmCompAdq;
    private List<containerCompromisos> compromisos, compAdquiridos;
    private AdapterCompromisos adapterCompromisos, adapterCompAdq;
    private ImageButton btnAddComp, btnAddCompAdq;
    private ImageButton btnDelComp, btnDelCompAdq;

    // Variables RecyclerView para Observaciones
    private RecyclerView rvObserva;
    private LinearLayoutManager llmObserva;
    private List<containerCompromisos> observaciones;
    private AdapterCompromisos adapterObservacion;
    private ImageButton btnAddObserva;
    private ImageButton btnDelObserva;


    //Header
    private EditText etActa;
    private EditText etLugOrg;
    private EditText etFecha;
    private EditText etHoraIn;
    private EditText etHoraFin;
    private EditText etLugar;

    private String acta, lugOrg, fecha, horaIn, horaFin, lugarReu;

    //Footer
    private EditText etProxFecha;
    private EditText etProxHour;
    private EditText etPresiName;
    private EditText etPresiCargo;
    private EditText etSecreName;
    private EditText etSecreCargo;
    private EditText etProxLugar;
    private String date2show, hour2show, proxLugar;
    private ImageView firPres, firSec;

    private String firmaPresidente, firmaSecretario;
    private String presiName, presiCargo, secreName, secreCargo;

    private DatePickerDialog pickerDialog;
    private TimePickerDialog timePickerDialog;

    private Context context;
    private WebServer webServer;
    private Utilidades utilidades;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Contexto
        context = MainActivity.this;

        // Variables RecyclerView para Asistentes, Ausentes e Invitados
        rvAsist = (RecyclerView) findViewById(R.id.rvAsistentes);
        rvAusent = (RecyclerView) findViewById(R.id.rvAusentes);
        rvInvit = (RecyclerView) findViewById(R.id.rvInvitados);

        // Variables RecyclerView para Orden del día y desarrollo de la Reunión
        rvOrden = (RecyclerView) findViewById(R.id.rvOrden);
        rvDllo = (RecyclerView) findViewById(R.id.rvDllo);

        // Variables RecyclerView para Compromisos
        rvCompr = (RecyclerView) findViewById(R.id.rvComp);
        rvComAdq = (RecyclerView) findViewById(R.id.rvCompAdq);

        // Variables RecyclerView para Observaciones
        rvObserva = (RecyclerView) findViewById(R.id.rvObservacion);


        // Variables LinearLayoutManager para Asistentes, Ausentes e Invitados
        llmAsist = new LinearLayoutManager(this);
        llmAusen = new LinearLayoutManager(this);
        llmInvit = new LinearLayoutManager(this);

        // Variables LinearLayoutManager para Orden del día y desarrollo de la Reunión
        llmOrden = new LinearLayoutManager(this);
        llmDesarrollo = new LinearLayoutManager(this);

        // Variables LinearLayoutManager para Compromisos
        llmComp = new LinearLayoutManager(this);
        llmCompAdq = new LinearLayoutManager(this);

        // Variables LinearLayoutManager para Observaciones
        llmObserva = new LinearLayoutManager(this);

        // Orientación LinearLayoutManager para Asistentes, Ausentes e Invitados
        llmAsist.setOrientation(LinearLayoutManager.VERTICAL);
        llmAusen.setOrientation(LinearLayoutManager.VERTICAL);
        llmInvit.setOrientation(LinearLayoutManager.VERTICAL);



        // Relación RecyclerView-LinearLayoutManager para Asistentes, Ausentes e Invitados
        rvAsist.setLayoutManager(llmAsist);
        rvAusent.setLayoutManager(llmAusen);
        rvInvit.setLayoutManager(llmInvit);

        // Relación RecyclerView-LinearLayoutManager para Orden del día y desarrollo de la Reunión
        rvOrden.setLayoutManager(llmOrden);
        rvDllo.setLayoutManager(llmDesarrollo);

        // Relación RecyclerView-LinearLayoutManager para Compromisos
        rvCompr.setLayoutManager(llmComp);
        rvComAdq.setLayoutManager(llmCompAdq);

        // Relación RecyclerView-LinearLayoutManager para Observaciones
        rvObserva.setLayoutManager(llmObserva);

        inicializarAdapter();

        //Definición Header
        etActa = (EditText) findViewById(R.id.et_acta);
        etLugOrg = (EditText) findViewById(R.id.et_lug_org);
        etFecha = (EditText) findViewById(R.id.et_fechaReu);
        etHoraIn = (EditText) findViewById(R.id.et_hora_in);
        etHoraFin = (EditText) findViewById(R.id.et_hora_fin);
        etLugar = (EditText) findViewById(R.id.et_lugar);

        // Definición Botones para agregar y eliminar para Asistentes, Ausentes e Invitados
        btnAddAsistentes = (ImageButton) findViewById(R.id.addAsist);
        btnDelAsistentes = (ImageButton) findViewById(R.id.delAsist);
        btnAddAusentes = (ImageButton) findViewById(R.id.addAusent);
        btnDelAusentes = (ImageButton) findViewById(R.id.delAusent);
        btnAddInvitados = (ImageButton) findViewById(R.id.addInvit);
        btnADelInvitados = (ImageButton) findViewById(R.id.delInvit);

        // Definición Botones para agregar y eliminar para Orden del día y desarrollo de la Reunión
        btnAddOrden = (ImageButton) findViewById(R.id.addOrden);
        btndelOrden = (ImageButton) findViewById(R.id.delOrden);
        btnAddDllo = (ImageButton) findViewById(R.id.addDllo);
        btnDelDllo = (ImageButton) findViewById(R.id.delDllo);

        // Definición Botones para y eliminar agregar para Compromisos
        btnAddComp = (ImageButton) findViewById(R.id.addComp);
        btnDelComp = (ImageButton) findViewById(R.id.delComp);
        btnAddCompAdq = (ImageButton) findViewById(R.id.addCompAdq);
        btnDelCompAdq = (ImageButton) findViewById(R.id.delCompAdq);

        // Definición Botones para y eliminar agregar para Observaciones
        btnAddObserva = (ImageButton) findViewById(R.id.addObser);
        btnDelObserva = (ImageButton) findViewById(R.id.delObser);

        //Definición Foter
        etProxFecha = (EditText) findViewById(R.id.et_proxf);
        etProxHour = (EditText) findViewById(R.id.et_proxh);
        etProxLugar = (EditText) findViewById(R.id.et_proxl);
        etPresiName = (EditText) findViewById(R.id.et_presiName);
        etPresiCargo = (EditText) findViewById(R.id.et_presiCargo);
        etSecreName = (EditText) findViewById(R.id.et_secreName);
        etSecreCargo = (EditText) findViewById(R.id.et_secreCargo);
        firPres = (ImageView) findViewById(R.id.firmaPresi);
        firSec = (ImageView) findViewById(R.id.firmaSecre);




        //Botones para agregar para Asistentes, Ausentes e Invitados
        //Agregar asistente
        btnAddAsistentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!asistentes.get(asistentes.size()-1).getNombre().isEmpty() &&
                        !asistentes.get(asistentes.size()-1).getCargo().isEmpty()){
                    asistentes.add(new containerAsistentes("", ""));
                    adapterAsistentes.notifyItemInserted(asistentes.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //Eliminar asistente
        btnDelAsistentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!asistentes.isEmpty()) {
                    asistentes.remove(asistentes.size() - 1);
                    adapterAsistentes.notifyItemRemoved(asistentes.size());
                }
            }
        });

        //Agregar Ausente
        btnAddAusentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ausentes.get(ausentes.size()-1).getNombre().isEmpty() &&
                        !ausentes.get(ausentes.size()-1).getCargo().isEmpty()){
                    ausentes.add(new containerAsistentes("", ""));
                    adapterAusentes.notifyItemInserted(ausentes.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        //Eliminar Ausente
        btnDelAusentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!ausentes.isEmpty()) {
                    ausentes.remove(ausentes.size() - 1);
                    adapterAusentes.notifyItemRemoved(ausentes.size());
                }
            }
        });

        //Agregar invitado
        btnAddInvitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!invitados.get(invitados.size()-1).getNombre().isEmpty() &&
                        !invitados.get(invitados.size()-1).getCargo().isEmpty()){
                    invitados.add(new containerAsistentes("", ""));
                    adapterInvitados.notifyItemInserted(invitados.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //eliminar invitado
        btnADelInvitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!invitados.isEmpty()) {
                    invitados.remove(invitados.size() - 1);
                    adapterInvitados.notifyItemRemoved(invitados.size());
                }
            }
        });

        //Botones para agregar para Orden del día y desarrollo de la Reunión
        //Agregar Orden del día
        btnAddOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!orden.get(orden.size()-1).isEmpty() &&
                        !orden.get(orden.size()-1).isEmpty()){
                    orden.add(new String(""));
                    adapterOrden.notifyItemInserted(orden.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //Eliminar Orden del día
        btndelOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!orden.isEmpty()){
                    orden.remove(orden.size() - 1);
                    adapterOrden.notifyItemRemoved(orden.size());
                }
            }
        });

        //Agregar Desarrollo de la reunión
        btnAddDllo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!desReunion.get(desReunion.size()-1).isEmpty() &&
                        !desReunion.get(desReunion.size()-1).isEmpty()){
                    desReunion.add(new String(""));
                    adapterDllo.notifyItemInserted(desReunion.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //Eliminar desarrollo de la reunión
        btnDelDllo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!desReunion.isEmpty()){
                    desReunion.remove(desReunion.size() -1);
                    adapterDllo.notifyItemRemoved(desReunion.size());
                }
            }
        });

        //Botones para agregar para Compromisos
        //Agregar Compromiso Verificación
        btnAddComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compromisos.get(compromisos.size()-1).getCompromiso().isEmpty() &&
                        !compromisos.get(compromisos.size()-1).getResponsable().isEmpty() &&
                        !compromisos.get(compromisos.size()-1).getEstado().isEmpty()){
                    compromisos.add(new containerCompromisos("", "", ""));
                    adapterCompromisos.notifyItemInserted(compromisos.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        //Eliminar Compromiso Verificación
        btnDelComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!compromisos.isEmpty()){
                    compromisos.remove(compromisos.size() - 1);
                    adapterCompromisos.notifyItemRemoved(compromisos.size());
                }
            }
        });

        //Agregar Compromiso adquirido
        btnAddCompAdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compAdquiridos.get(compAdquiridos.size()-1).getCompromiso().isEmpty() &&
                        !compAdquiridos.get(compAdquiridos.size()-1).getResponsable().isEmpty() &&
                        !compAdquiridos.get(compAdquiridos.size()-1).getEstado().isEmpty()){
                    compAdquiridos.add(new containerCompromisos("", "", ""));
                    adapterCompAdq.notifyItemInserted(compAdquiridos.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //Eliminar Compromiso adquirido
        btnDelCompAdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!compAdquiridos.isEmpty()){
                    compAdquiridos.remove(compAdquiridos.size() -1);
                    adapterCompAdq.notifyItemRemoved(compAdquiridos.size());
                }
            }
        });

        //Botones para agregar para Observaciones
        //Agregar Observaciones
        btnAddObserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!observaciones.get(observaciones.size()-1).getCompromiso().isEmpty() &&
                        !observaciones.get(observaciones.size()-1).getResponsable().isEmpty()){
                    observaciones.add(new containerCompromisos("", "", ""));
                    adapterObservacion.notifyItemInserted(observaciones.size());
                } else {
                    snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });
        //Eliminar Observaciones
        btnDelObserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar que la lista no permita eliminar si no hay nada en la lista
                if (!observaciones.isEmpty()){
                    observaciones.remove(observaciones.size() - 1);
                    adapterObservacion.notifyItemRemoved(observaciones.size());
                }
            }
        });



        firPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder firma = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.firmar, null);

                final SignaturePad campoFirma = (SignaturePad) v.findViewById(R.id.firma_sinature);
                Button btnLimpia = (Button) v.findViewById(R.id.firma_limpia);

                btnLimpia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        campoFirma.clear();
                    }
                });
                firma.setView(v).setTitle(R.string.inFirma).setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bitmap firmado = campoFirma.getSignatureBitmap();
                        String firma64 = utilidades.encodeToBase64(firmado);
                        firmaPresidente = firma64;
                        firPres.setImageBitmap(firmado);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                firma.create().show();
            }
        });
        firSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder firma = new AlertDialog.Builder(context);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.firmar, null);

                final SignaturePad campoFirma = (SignaturePad) v.findViewById(R.id.firma_sinature);
                Button btnLimpia = (Button) v.findViewById(R.id.firma_limpia);

                btnLimpia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        campoFirma.clear();
                    }
                });
                firma.setView(v).setTitle(R.string.inFirma).setPositiveButton(R.string.acept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bitmap firmado = campoFirma.getSignatureBitmap();
                        String firma64 = utilidades.encodeToBase64(firmado);
                        firmaSecretario = firma64;
                        firSec.setImageBitmap(firmado);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                firma.create().show();
            }
        });




        etProxFecha.setFocusable(false);
        etProxFecha.setClickable(true);
        etProxFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int sday = calendar.get(Calendar.DAY_OF_MONTH);
                final int smonth = calendar.get(Calendar.MONTH);
                final int syear = calendar.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //TODO Usar el simpledateformat
                        date2show = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etProxFecha.setText(date2show);
                    }
                }, syear, smonth, sday);
                pickerDialog.show();
            }
        });

        etProxHour.setFocusable(false);
        etProxHour.setClickable(true);
        etProxHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int shour = calendar.get(Calendar.HOUR_OF_DAY);
                final int sminute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //TODO Usar el simpledateformat
                        hour2show = " " + hourOfDay + ":" + minute;
                        etProxHour.setText(hour2show);
                    }
                }, shour, sminute, true);
                timePickerDialog.show();
            }
        });

        etFecha.setFocusable(false);
        etFecha.setClickable(true);
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int sday = calendar.get(Calendar.DAY_OF_MONTH);
                final int smonth = calendar.get(Calendar.MONTH);
                final int syear = calendar.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //TODO Usar el simpledateformat
                        fecha = dayOfMonth + "-" + (month + 1) + "-" + year;
                        etFecha.setText(fecha);
                    }
                }, syear, smonth, sday);
                pickerDialog.show();
            }
        });

        etHoraIn.setFocusable(false);
        etHoraIn.setClickable(true);
        etHoraIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int shour = calendar.get(Calendar.HOUR_OF_DAY);
                final int sminute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horaIn = " " + hourOfDay + ":" + minute;
                        etHoraIn.setText(horaIn);
                    }
                }, shour, sminute, true);
                timePickerDialog.show();
            }
        });

        etHoraFin.setFocusable(false);
        etHoraFin.setClickable(true);
        etHoraFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final int shour = calendar.get(Calendar.HOUR_OF_DAY);
                final int sminute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //TODO Usar el simpledateformat
                        horaFin = " " + hourOfDay + ":" + minute;
                        etHoraFin.setText(horaFin);
                    }
                }, shour, sminute, true);
                timePickerDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if (id == R.id.action_save){
            Log.v("save", "Saving...");



            getHeader();
            getFooter();
            if (checkHeader() && checkFooter()){

                webServer = new WebServer();
                utilidades = new Utilidades();
                List<String> personas = getPersonas();
                List<String> ordenDllo = getOD();
                List<String> compObs = getCompObs();
                webServer.enviarFormulario(context, acta, lugOrg, fecha, horaIn, horaFin,
                        lugarReu, date2show + " " + hour2show + "*" + proxLugar, firmaPresidente,
                        presiName, presiCargo, firmaSecretario, secreName, secreCargo,
                        utilidades.getFechaCompleta(), personas, ordenDllo, compObs);

                String data = "";
                data += "Acta N° " + acta + "\n" +
                        "Lugar Origen: " + lugOrg + "\n" +
                        "Fecha Reunión: " + fecha + "\n" +
                        "Hora Inicio: " + horaIn + "\n" +
                        "Hora Finalización: " + horaFin + "\n" +
                        "Lugar Reunión: " + lugarReu + "\n";

                data += getListas();

                data += "Próxima Reunión: " + "\n" +
                        date2show + " " + hour2show + "-" + proxLugar + "\n";

                data += "Nombre Presidente: " + presiName + "\n" +
                        "Cargo Presidente: " + presiCargo + "\n" +
                        "Nombre Secretari@: " + secreName + "\n" +
                        "Cargo SecretariQ: " + secreCargo + "\n";

                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Datos almacenados");
                alertDialog.setMessage(data);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            } else {
                snackbar = Snackbar.make(findViewById(R.id.main), R.string.fillFields, Snackbar.LENGTH_LONG);
                snackbar.show();
            }



            return true;
        }

        return true;
    }


    private void getHeader(){
        acta = etActa.getText().toString();
        lugOrg = etLugOrg.getText().toString();
        fecha = etFecha.getText().toString();
        horaIn = etHoraIn.getText().toString();
        horaFin = etHoraFin.getText().toString();
        lugarReu = etLugar.getText().toString();
    }
    private void getFooter(){
        presiName = etPresiName.getText().toString();
        presiCargo = etPresiCargo.getText().toString();
        secreName = etSecreName.getText().toString();
        secreCargo = etSecreCargo.getText().toString();
        proxLugar = etProxLugar.getText().toString();
    }

    private boolean checkHeader(){

        return (!acta.isEmpty()  &&  !lugOrg.isEmpty()  &&  !fecha.isEmpty() &&
        !horaIn.isEmpty()  &&  !horaFin.isEmpty()  &&  !lugarReu.isEmpty());
    }

    private boolean checkFooter(){

        return (!presiName.isEmpty() && !presiCargo.isEmpty() && !secreName.isEmpty() && !proxLugar.isEmpty()
                && !secreCargo.isEmpty() && !firmaPresidente.isEmpty() && !firmaSecretario.isEmpty());
    }

    private List<String> getPersonas(){

        String value = "";
        List<String> listas = new ArrayList<>();
        for (int i = 0; i < asistentes.size(); i++){
            value+=asistentes.get(i).getNombre() + "*" + asistentes.get(i).getCargo()  + "#";
        }
        listas.add(value);
        value = "";
        for (int i = 0; i < ausentes.size(); i++){
            value+=ausentes.get(i).getNombre() + "*" + ausentes.get(i).getCargo()  + "#";
        }
        listas.add(value);
        value = "";
        for (int i = 0; i < invitados.size(); i++){
            value+=invitados.get(i).getNombre() + "*" + invitados.get(i).getCargo()  + "#";
        }
        listas.add(value);
        return listas;

    }

    private List<String> getOD(){

        String value = "";
        List<String> ordenDesllo = new ArrayList<>();
        for (int i = 0; i < orden.size(); i++){
            value+= i+1 + "*" + orden.get(i).toString() + "#";
        }
        ordenDesllo.add(value);
        value = "";
        for (int i = 0; i < desReunion.size(); i++){
            value+= i+1 + "*" + desReunion.get(i).toString() + "#";
        }
        ordenDesllo.add(value);
        return ordenDesllo;

    }

    private List<String> getCompObs(){

        String value = "";
        List<String> compObs = new ArrayList<>();
        for (int i = 0; i < compromisos.size(); i++){
            value+=compromisos.get(i).getCompromiso() + "*" + compromisos.get(i).getResponsable()
                    + "*" + compromisos.get(i).getEstado() + "#";
        }
        compObs.add(value);
        value = "";
        for (int i = 0; i < compAdquiridos.size(); i++){
            value+=compAdquiridos.get(i).getCompromiso() + "*" + compAdquiridos.get(i).getResponsable()
                    + "*" + compAdquiridos.get(i).getEstado() + "#";
        }
        compObs.add(value);
        value = "";
        for (int i = 0; i < observaciones.size(); i++){
            value+=observaciones.get(i).getCompromiso() + "*" + observaciones.get(i).getResponsable()
                    + "*" + " " + "#";
        }
        compObs.add(value);
        return compObs;

    }

    private String getListas(){

        String value = "";
        value += "\n" + "Asistentes: " + "\n";
        for (int i = 0; i < asistentes.size(); i++){
            value += "\t ~ " + asistentes.get(i).getNombre() + " - " + asistentes.get(i).getCargo() + "\n";
        }
        value += "\n" + "Ausentes: " + "\n";
        for (int i = 0; i < ausentes.size(); i++){
            value += "\t ~ " + ausentes.get(i).getNombre() + " - " + ausentes.get(i).getCargo() + "\n";
        }
        value += "\n" + "Invitados: " + "\n";
        for (int i = 0; i < invitados.size(); i++){
            value += "\t ~ " + invitados.get(i).getNombre() + " - " + invitados.get(i).getCargo() + "\n";
        }

        value += "\n" + "Orden: " + "\n";
        for (int i = 0; i < orden.size(); i++){
            value += "\t" + (i+1) + " " + orden.get(i).toString() + "\n";
        }
        value += "\n" + "Desarrollo de la reunión: " + "\n";
        for (int i = 0; i < desReunion.size(); i++){
            value += "\t" + (i+1) + " " + desReunion.get(i).toString() + "\n";
        }

        value += "\n" + "Compromisos: " + "\n";
        for (int i = 0; i < compromisos.size(); i++){
            value += "\t ~ " + compromisos.get(i).getCompromiso() + " - " + compromisos.get(i).getResponsable()
                    + " - " + compromisos.get(i).getEstado() + "\n";
        }
        value += "\n" + "Compromisos Adquiridos: " + "\n";
        for (int i = 0; i < compAdquiridos.size(); i++){
            value += "\t ~ " + compAdquiridos.get(i).getCompromiso() + " - " + compAdquiridos.get(i).getResponsable()
                    + " - " + compAdquiridos.get(i).getEstado() + "\n";
        }

        value += "\n" + "Observaciones: " + "\n";
        for (int i = 0; i < observaciones.size(); i++){
            value += "\t ~ " + observaciones.get(i).getCompromiso() + " - " + observaciones.get(i).getResponsable() + "\n";
        }

        return value;
    }

    private boolean checkLists(){

        boolean as, au, in, or, des, com, cad, ob;
        return  true;

    }

    public void inicializarAdapter(){
        //Definir listas
        asistentes = new ArrayList<>();
        ausentes = new ArrayList<>();
        invitados = new ArrayList<>();
        orden = new ArrayList<>();
        desReunion = new ArrayList<>();
        compromisos = new ArrayList<>();
        compAdquiridos = new ArrayList<>();
        observaciones = new ArrayList<>();

        //Inicializar listas
        asistentes.add(new containerAsistentes("", ""));
        ausentes.add(new containerAsistentes("", ""));
        invitados.add(new containerAsistentes("", ""));

        orden.add(new String(""));
        desReunion.add(new String(""));

        compromisos.add(new containerCompromisos("", "", ""));
        compAdquiridos.add(new containerCompromisos("", "", ""));

        observaciones.add(new containerCompromisos("", "", ""));

        //Definir e inicializar Adaptadores
        adapterAsistentes = new AdapterAsistentes(asistentes);
        adapterAusentes = new AdapterAsistentes(ausentes);
        adapterInvitados = new AdapterAsistentes(invitados);

        adapterOrden = new AdapterOrden(orden, 0);
        adapterDllo = new AdapterOrden(desReunion, 1);

        adapterCompromisos = new AdapterCompromisos(compromisos, 0);
        adapterCompAdq = new AdapterCompromisos(compAdquiridos, 1);

        adapterObservacion = new AdapterCompromisos(observaciones, 2);

        //Establecer Adaptadores a RecyclerView
        rvAsist.setAdapter(adapterAsistentes);
        rvAusent.setAdapter(adapterAusentes);
        rvInvit.setAdapter(adapterInvitados);

        rvOrden.setAdapter(adapterOrden);
        rvDllo.setAdapter(adapterDllo);

        rvCompr.setAdapter(adapterCompromisos);
        rvComAdq.setAdapter(adapterCompAdq);

        rvObserva.setAdapter(adapterObservacion);

    }


}
