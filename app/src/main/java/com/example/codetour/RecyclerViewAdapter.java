package com.example.codetour;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<PosItem> itemLists = new ArrayList<>();
    private SearchView searchView;
    //ViewHolder가 RecyclerView가 갱신될 때 마다 View를 설정해주는 친구인 것 같습니다
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        //검색된 장소 하나를 보여줄 때 아직은 장소이름이랑 주소만 보여주기 때문에 두개 있습니다.
        //추가적인 정보를 보여주고 싶으면 추가하면 됩니다.
        public TextView title;
        public TextView address;
        public Activity mActivity;
        public CustomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) { //검색된 장소 목록죽에서 하나를 클릭했을 때 발생하는 이벤트입니다.
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        PosItem clicked = itemLists.get(pos);

                        /*
                        //선택한 장소의 주소를 스트링 형태로 받아온 뒤, 스페이스바 기준으로 잘라서 맨 앞에 대분류 중분류 선택합니다.
                        String[] splited =clicked.getAddress().split(" ");
                        String add1=splited[0]; //도,시
                        String add2=splited[1]; //시군구
                        String[] result = {clicked.getTitle(),clicked.getAddress()};
                        */

                        //SePosSetting화면으로 넘겨줘야 할듯!!
                        //intent보다는 http://zeany.net/54 이 링크에 나와있는 방법으로 하는게 나을 것 같은데 확인하고 알려줘줭
                        /*
                        //일단 AlterDialog로 선택된 장소의 내용 확인
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("선택된 장소 :"+Integer.toString(pos)).setMessage(add1+" "+add2);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                         */
                        Intent intent = new Intent();
                        //intent.putExtra("result", result);
                        intent.putExtra("title", clicked.getTitle());
                        intent.putExtra("addr", clicked.getAddress());
                        if (v.getContext() instanceof Activity){
                            ((Activity)v.getContext()).setResult(Activity.RESULT_OK, intent);
                            ((Activity)v.getContext()).finish();
                        }


                    }
                }

            });
            title = (TextView) itemView.findViewById(R.id.item_title);
            address = (TextView) itemView.findViewById(R.id.item_address);

        }

    }

    //viewHolder가 생성될 때 발생하는 이벤트
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new CustomViewHolder(view);
    }
    //RecyclerView가 갱신될 때? 발생하는 이벤트
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setText(itemLists.get(position).getTitle());
        holder.address.setText(itemLists.get(position).getAddress());

    }

    //현재 RecyclerView에 존재하는 아이템 개수 반환하는 함수
    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    //RecyclerView의 데이타를 설정할 수 있는 함수
    public void setData(ArrayList<PosItem> itemLists) {
        this.itemLists = itemLists;
    }


    //아까 SearchView 액티비티에서 호출하는 함수
    public void filter(String keyword) {
        if (keyword.length() >= 2) {
            try{
                TmapPOI parser = new TmapPOI(this);
                //getAutoComplete(string) 얘가 검색해주는 함수야
                System.out.println("검색해줘");
                parser.getAutoComplete(keyword);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}


