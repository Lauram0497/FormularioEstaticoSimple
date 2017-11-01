package co.com.eleinco.formularioestaticosimple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 08/08/2017.
 */

public class AdapterAsistentes extends RecyclerView.Adapter<AdapterAsistentes.MyviewHolder> {

    private Context context;
    private List<containerAsistentes> asistentes;
    private containerAsistentes asist;



    public AdapterAsistentes(/*Context context*/ List<containerAsistentes> asistentes) {
        /*this.context = context;*/
        this.asistentes = asistentes;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        public EditText etName, etCargo;
        public TextView tvName, tvCargo;

        public MyviewHolder(View itemView){
            super(itemView);

            etName = (EditText) itemView.findViewById(R.id.et_name);
            etCargo = (EditText) itemView.findViewById(R.id.et_cargo);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCargo = (TextView) itemView.findViewById(R.id.tv_cargo);

        }

    }


    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistentes, parent, false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, final int position) {

        asist = asistentes.get(position);

        //holder.etName.setText(asist.getNombre());

        holder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                asist.setNombre(s.toString());
                asistentes.set(position, asist);

            }
        });
        holder.etCargo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                asist.setCargo(editable.toString());
                asistentes.set(position, asist);
            }
        });

    }


    @Override
    public int getItemCount() {
        return asistentes.size();
    }

    public void guardarValores(List<containerAsistentes> listaDatos){

    }

    /*public List<containerAsistentes> getListaAsistentes(){

        return asistentes;

    }*/
}
