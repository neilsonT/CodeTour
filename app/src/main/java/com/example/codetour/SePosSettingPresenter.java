package com.example.codetour;

public class SePosSettingPresenter implements SePosSettingContract.Presenter {
    SePosSettingContract.View view;

    @Override
    public void setView(SePosSettingContract.View view){ this.view = view; }

    @Override
    public void releaseView(){ this.view = null; }


}
