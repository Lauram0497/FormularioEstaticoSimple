package co.com.eleinco.formularioestaticosimple;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Usuario on 10/08/2017.
 */

public class AdapterCompromisos extends RecyclerView.Adapter<AdapterCompromisos.CompromisoHolder> {

    private List<containerCompromisos> itemComp;
    private int id_Identificador;
    private containerCompromisos comps;

    public AdapterCompromisos(List<containerCompromisos> itemComp, int id_Identificador) {
        this.itemComp = itemComp;
        this.id_Identificador = id_Identificador;
    }


    public class CompromisoHolder extends RecyclerView.ViewHolder {

        public TextView tv_comrpomiso;
        public EditText et_compromiso, et_responsable, et_estado;

        public CompromisoHolder(View itemView) {
            super(itemView);

            tv_comrpomiso = (TextView) itemView.findViewById(R.id.tv_compromisos);
            et_compromiso = (EditText) itemView.findViewById(R.id.et_compromiso);
            et_responsable = (EditText) itemView.findViewById(R.id.et_respons);
            et_estado = (EditText) itemView.findViewById(R.id.et_estado);
        }
    }

    @Override
    public CompromisoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compromiso, parent, false);
        return new CompromisoHolder(v);
    }

    @Override
    public void onBindViewHolder(CompromisoHolder holder, final int position) {

        comps = itemComp.get(position);

        holder.et_compromiso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comps.setCompromiso(editable.toString());
                itemComp.set(position, comps);
            }
        });
        holder.et_responsable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comps.setResponsable(editable.toString());
                itemComp.set(position, comps);
            }
        });
        holder.et_estado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comps.setEstado(editable.toString());
                itemComp.set(position, comps);
            }
        });

        holder.tv_comrpomiso.setText(String.valueOf(position + 1));
        if (id_Identificador == 0){

        } else if (id_Identificador == 2){
            holder.et_estado.setVisibility(View.GONE);
            holder.et_responsable.setHint("");
            holder.et_compromiso.setHint("");
        }

    }

    @Override
    public int getItemCount() {
        return itemComp.size();
    }

}
