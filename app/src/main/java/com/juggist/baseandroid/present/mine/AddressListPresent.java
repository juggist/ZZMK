package com.juggist.baseandroid.present.mine;

import com.juggist.baseandroid.ui.mine.AddressListContract;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.service.IUserService;
import com.juggist.jcore.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/12/4 3:30 PM
 */
public class AddressListPresent implements AddressListContract.Present {
    private AddressListContract.View view;
    private IUserService userService;

    private List<AddressBean> totalAddressBeans = new ArrayList<>();
    private int selectIndex = -1;

    public AddressListPresent(AddressListContract.View view) {
        this.view = view;
        view.setPresent(this);
        userService = new UserService();

    }

    @Override
    public void getAddressList() {
        showLoading();
        userService.getAddressList(new ResponseCallback<List<AddressBean>>() {
            @Override
            public void onSucceed(List<AddressBean> addressBeans) {
                if (view == null)
                    return;
                view.dismissLoading();
                if (addressBeans.size() == 0) {
                    view.getAddressListEmpty();
                } else {
                    totalAddressBeans.clear();
                    totalAddressBeans.addAll(addressBeans);
                    view.getAddressListSucceed(totalAddressBeans);
                }
            }

            @Override
            public void onError(String message) {
                dismissLoading();
                if (view != null)
                    view.getAddressListFail(message);
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                if (view != null)
                    view.getAddressListFail(message + " ; " + state);
            }
        });
    }

    /**
     * 校验是否可以删除地址
     * @param position
     */
    @Override
    public void checkDeleteAddress(int position){
        selectIndex = position;
        if(view != null)
            view.showDeleteAddressDialog();
    };
    @Override
    public void deleteAddress() {
        showLoading();
        userService.deleteAddress(totalAddressBeans.get(selectIndex).getAuto_id(), new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                totalAddressBeans.remove(selectIndex);
                dismissLoading();
                if (view == null)
                    return;
                if (totalAddressBeans.size() == 0)
                    view.deleteAddressEmptySucceed();
                else
                    view.deleteAddressSucceed(totalAddressBeans);

            }

            @Override
            public void onError(String message) {
                dismissLoading();
                if (view != null)
                    view.deleteAddressFail(message);
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                if (view != null)
                    view.deleteAddressFail(message + " ; " + state);
            }
        });
    }

    /**
     * 校验是否可以设置默认地址
     * @param position
     */
    @Override
    public void checkDefaultAddress(int position) {
        selectIndex = position;
        boolean selectAble = totalAddressBeans.get(selectIndex).getIs_default().equals("0")?false:true;
        if(!selectAble && view != null){
            view.showSetDefaultAddressDialog();
        }
    }

    @Override
    public void setDefaultAddress() {
        showLoading();
        userService.setDefaultAddress(totalAddressBeans.get(selectIndex).getAuto_id(), new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                for(int i = 0; i < totalAddressBeans.size() ;i++){
                    AddressBean item = totalAddressBeans.get(i);
                    if(i == selectIndex){
                        totalAddressBeans.get(i).setIs_default("1");
                    }else{
                        if(item.getIs_default().equals("1")){
                            totalAddressBeans.get(i).setIs_default("0");
                        }
                    }
                }
                dismissLoading();
                if (view != null)
                    view.setDefaultAddressSucceed(totalAddressBeans);
            }

            @Override
            public void onError(String message) {
                dismissLoading();
                if (view != null)
                    view.setDefaultAddressFail(message);
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                if (view != null)
                    view.setDefaultAddressFail(message + " ; " + state);
            }
        });
    }

    @Override
    public AddressBean getAddress(int position) {
        return totalAddressBeans.get(position);
    }

    @Override
    public void start() {
        getAddressList();
    }

    @Override
    public void detach() {
        view = null;
    }

    private void showLoading() {
        if (view != null)
            view.showLoading();
    }

    private void dismissLoading() {
        if (view == null)
            return;
        view.dismissLoading();

    }
}
