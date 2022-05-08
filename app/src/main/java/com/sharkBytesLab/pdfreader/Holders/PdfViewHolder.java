package com.sharkBytesLab.pdfreader.Holders;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sharkBytesLab.pdfreader.R;

public class PdfViewHolder extends RecyclerView.ViewHolder
{

    public TextView tvName;
    public CardView container;

    public PdfViewHolder(View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.textPdfName);
        container = itemView.findViewById(R.id.container);



    }
}
