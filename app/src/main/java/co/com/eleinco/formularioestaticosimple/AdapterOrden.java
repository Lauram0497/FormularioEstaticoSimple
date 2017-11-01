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
 * Created by Usuario on 09/08/2017.
 */

public class AdapterOrden extends RecyclerView.Adapter<AdapterOrden.OrdenHolder> {

    private List<String> itemOrden;
    private int id_Identificador;
    private String valor;

    public AdapterOrden(List<String> itemOrden, int id_Identificador) {
        this.itemOrden = itemOrden;
        this.id_Identificador = id_Identificador;
    }

    public class OrdenHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public EditText editText;

        public OrdenHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_order);
            editText = (EditText) itemView.findViewById(R.id.et_texto);

        }
    }

    @Override
    public OrdenHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orden, parent, false);
        return new OrdenHolder(v);
    }

    @Override
    public void onBindViewHolder(OrdenHolder holder, final int position) {

        valor = itemOrden.get(position);

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                valor = editable.toString();
                itemOrden.set(position, valor);
            }
        });
        if (id_Identificador == 0){
            holder.textView.setText(String.valueOf(position + 1) + ".");
        } else if (id_Identificador == 1){
            holder.textView.setText(String.valueOf(position + 1) + ")");
        }

    }

    @Override
    public int getItemCount() {
        return itemOrden.size();
    }



}
