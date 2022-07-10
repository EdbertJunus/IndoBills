package com.example.indobills.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indobills.R;
import com.example.indobills.TransactionDetailActivity;
import com.example.indobills.model.Bill;
import com.example.indobills.model.BillHelper;
import com.example.indobills.model.Transaction;

import java.util.ArrayList;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Transaction> transactionArrayList;
    private BillHelper billHelper;

    public TransactionListAdapter(Context ctx, ArrayList<Transaction> transactionArrayList){
        this.ctx = ctx;
        this.transactionArrayList = transactionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactionArrayList.get(position);

        String transactionId = transaction.getTransactionId();
        String providerCode, name, number, type;
        Bill bill = null;
        Boolean transactionStatus = transaction.getTransactionStatus();

        if(transactionStatus){
            billHelper = new BillHelper(ctx);
            billHelper.open();
            bill = billHelper.findBillById(transaction.getBillId());
            billHelper.close();

            name = bill.getBillProviderName();
            number =  bill.getBillProviderNumber();
            type = bill.getBillType();
        }else{
            String[] arrayBillInfo = transaction.getBillId().split("#");
            name = arrayBillInfo[0];
            number = arrayBillInfo[1];
            type = arrayBillInfo[2];
        }

        if(type.equals("Wifi")){
            providerCode = type.substring(0,1) + number;

        }else if(type.equals("Phone") ){
            holder.ivTransaction.setImageResource(R.mipmap.phone_border_foreground);
            providerCode = type.substring(0,1) + number;

        }else{
            holder.ivTransaction.setImageResource(R.mipmap.water_drop_foreground);
            providerCode = "A"+ number;

        }

        holder.tvTransaction.setText(providerCode);

        holder.cvTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, TransactionDetailActivity.class);
                intent.putExtra("transactionCode", providerCode);
                intent.putExtra("providerName", name);
                intent.putExtra("providerNumber", number);
                intent.putExtra("providerAmount", transaction.getTransactionAmount());
                intent.putExtra("providerMethod", transaction.getTransactionPaymentMethod());
                intent.putExtra("transactionStatus", transaction.getTransactionStatus());
                intent.putExtra("providerType", type);

                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView cvTransaction;
        protected TextView tvTransaction;
        protected ImageView ivTransaction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvTransaction = itemView.findViewById(R.id.transaction_card);
            tvTransaction = itemView.findViewById(R.id.tv_transaction_card);
            ivTransaction = itemView.findViewById(R.id.iv_transaction_card);
        }
    }

}
